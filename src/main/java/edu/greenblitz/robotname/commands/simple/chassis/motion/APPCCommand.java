package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.SubsystemCommand;
import org.apache.logging.log4j.Level;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.app.AdaptivePurePursuitController;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

public class APPCCommand extends SubsystemCommand<Chassis> {

    private AdaptivePurePursuitController m_controller;
    private RemoteCSVTarget m_logger;

    public APPCCommand(Path<Position> path, double lookAhead,
                       double tolerance, boolean isBackwards,
                       double minSpeed, double maxSpeedDist, double maxSpeed) {
        super(Chassis.getInstance());
        m_controller = new AdaptivePurePursuitController(path, lookAhead, RobotMap.Chassis.Data.WHEEL_BASE_RADIUS, tolerance, isBackwards,
                minSpeed, maxSpeedDist, maxSpeed);
        m_logger = RemoteCSVTarget.initTarget("Location", "x", "y");
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
        system.getLogger().debug("APPC command ended with error=[" + diff + "]");
    }

    @Override
    protected boolean isFinished() {
        return m_controller.isFinished(system.getLocation());
    }


}
