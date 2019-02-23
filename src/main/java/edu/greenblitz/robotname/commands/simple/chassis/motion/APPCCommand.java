package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.robotname.RobotMap;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.SubsystemCommand;
import org.greenblitz.motion.app.AdaptivePurePursuitController;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

public class APPCCommand extends SubsystemCommand<Chassis> {

    private AdaptivePurePursuitController m_controller;

    public APPCCommand(Path<Position> path, double lookAhead,
                       double tolerance, boolean isBackwards,
                       double minSpeed, double maxSpeedDist, double maxSpeed) {
        super(Chassis.getInstance());
        m_controller = new AdaptivePurePursuitController(path, lookAhead, RobotMap.Chassis.Data.WHEEL_BASE_RADIUS, tolerance, isBackwards,
                minSpeed, maxSpeedDist, maxSpeed);
    }

    @Override
    protected void execute() {
        var moveValues = m_controller.iteration(system.getLocation());
        system.tankDrive(moveValues[0], moveValues[1]);
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
