package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.SubsystemCommand;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.Level;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.app.AdaptivePurePursuitController;
import org.greenblitz.motion.app.Localizer;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class APPCCommand extends SubsystemCommand<Chassis> {

    private AdaptivePurePursuitController m_controller;
    private RemoteCSVTarget m_logger;
    private Position startPos;

    public APPCCommand(Path<Position> path, Position startPos, double lookAhead,
                       double tolerance, boolean isBackwards,
                       double minSpeed, double maxSpeedDist, double maxSpeed) {
        super(Chassis.getInstance());
        m_controller = new AdaptivePurePursuitController(path, lookAhead, RobotMap.Chassis.Data.WHEEL_BASE_RADIUS, tolerance, isBackwards,
                minSpeed, maxSpeedDist, maxSpeed);
        m_logger = RemoteCSVTarget.initTarget("Location", "x", "y");
        this.startPos = startPos;
    }

    @Override
    protected void initialize(){
        if (startPos != null)
            Localizer.getInstance().reset(Chassis.getInstance().getLeftDistance(),
                    Chassis.getInstance().getRightDistance(), startPos);
    }

    @Override
    protected void execute() {
        Position loc = system.getLocation();
        var moveValues = m_controller.iteration(loc);
        system.tankDrive(moveValues[0], moveValues[1]);
        system.getLogger().debug(system.getLocation());
        m_logger.report(loc.getX(), loc.getY());
    }

    @Override
    protected void end() {
        var expected = m_controller.getPath().getLast();
        var actual = system.getLocation();
        var diff = new Position(expected.getX() - actual.getX(), expected.getY() - actual.getY(), expected.getAngle() - actual.getAngle());
        system.getLogger().debug("APPC command ended with error=[{}]", diff);
    }

    @Override
    protected boolean isFinished() {
        return m_controller.isFinished(system.getLocation());
    }

    public static List<Position> getPath(String filename) {
        CSVParser read;
        try {
            read = CSVFormat.DEFAULT.parse(new FileReader(new File("/home/lvuser/deploy/paths/" + filename)));
            ArrayList<Position> path = new ArrayList<>();
            List<CSVRecord> records = read.getRecords();
            for (int i = 1; i < records.size(); i++) {
                path.add(new Position(new Point(Double.parseDouble(records.get(i).get(1)), Double.parseDouble(records.get(i).get(2))).weaverToLocalizerCoords()));
            }
            System.out.println(filename + ": " + path);
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Failed to read file");
        return new ArrayList<>();
    }

}
