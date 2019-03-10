package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.greenblitz.utils.sm.State;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.debug.RemoteGuydeBugger;
import org.greenblitz.motion.app.AdaptivePurePursuitController;
import org.greenblitz.motion.app.Localizer;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class APPCCommand extends SubsystemCommand<Chassis> {

    private AdaptivePurePursuitController m_controller;
    private RemoteCSVTarget m_logger;
    private double accelTime;

    public APPCCommand(AdaptivePurePursuitController c, double accelTime) {
        super(Chassis.getInstance());
        m_controller = c;
        m_logger = RemoteCSVTarget.initTarget("Location", "x", "y");
        this.accelTime = accelTime;
    }

    public APPCCommand(Path<Position> path, double lookAhead,
                       double tolerance, boolean isBackwards,
                       double minSpeed, double maxSpeedDist, double maxSpeed, double accelTime) {
        super(Chassis.getInstance());
        m_controller = new AdaptivePurePursuitController(path, lookAhead, RobotMap.Chassis.Data.WHEEL_BASE_RADIUS, tolerance, isBackwards,
                minSpeed, maxSpeedDist, maxSpeed);
        m_logger = RemoteCSVTarget.initTarget("Location", "x", "y");
        this.accelTime = accelTime;
    }

    @Override
    protected void initialize() {
        system.setRampRate(this.accelTime);
        Localizer.getInstance().resetEncoders(Chassis.getInstance().getLeftDistance(),
                Chassis.getInstance().getRightDistance());
        system.getLogger().debug("initial: {}, end: {}, current: {}",
                m_controller.getPath().get(0), m_controller.getPath().getLast(),
                Localizer.getInstance().getLocation());

    }

    @Override
    protected void execute() {
        Position loc = system.getLocation();
        var moveValues = m_controller.iteration(loc);
        system.tankDrive(moveValues[0], moveValues[1]);
        m_logger.report(loc.getX(), loc.getY());
    }

    @Override
    public Optional<State> getDeltaState() {
        return Optional.empty();
    }

    @Override
    protected void atEnd() {
        var expected = m_controller.getPath().getLast();
        var actual = system.getLocation();
        var diff = new Position(expected.getX() - actual.getX(), expected.getY() - actual.getY(), expected.getAngle() - actual.getAngle());
        system.getLogger().debug("APPC command ended with error=[{}]", diff);
        system.setRampRate(0);
    }

    @Override
    protected boolean isFinished() {
        return m_controller.isFinished(system.getLocation());
    }
}