package edu.greenblitz.knockdown.commands.simple.chassis.motion;

import edu.greenblitz.knockdown.RobotMap;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.command.base.SubsystemCommand;
import org.greenblitz.debug.RemoteCSVTarget;
import org.greenblitz.motion.app.AdaptivePurePursuitController;
import org.greenblitz.motion.app.Localizer;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

public class APPCCommand extends SubsystemCommand<Chassis> {

    private AdaptivePurePursuitController m_controller;
    private RemoteCSVTarget m_logger;
    private Position startPos;
    private double accelTime;
    private double gyroTarget = 4590;
    private double gyroTolerance = -1;
    private double startSearchingForVisionSqaured = 0;

    public APPCCommand(AdaptivePurePursuitController c, Position startPos, double accelTime){
        super(Chassis.getInstance());
        m_controller = c;
        m_logger = RemoteCSVTarget.initTarget("Location", "x", "y");
        this.startPos = startPos;
        this.accelTime = accelTime;
    }

    public APPCCommand(Path<Position> path, Position startPos, double lookAhead,
                       double tolerance, boolean isBackwards,
                       double minSpeed, double maxSpeedDist, double maxSpeed, double accelTime) {
        super(Chassis.getInstance());
        m_controller = new AdaptivePurePursuitController(path, lookAhead, RobotMap.Chassis.Data.WHEEL_BASE_RADIUS, tolerance, isBackwards,
                minSpeed, maxSpeedDist, maxSpeed);
        m_logger = RemoteCSVTarget.initTarget("Location", "x", "y");
        this.accelTime = accelTime;
        this.startPos = startPos;
    }

    public APPCCommand(Path<Position> path, Position startPos, double lookAhead,
                       double tolerance, boolean isBackwards,
                       double minSpeed, double maxSpeedDist, double maxSpeed, double accelTime,
                       double gyroLocalizerTarget, double gyroTolerance) {
        this(path, startPos, lookAhead, tolerance, isBackwards, minSpeed, maxSpeedDist,
                maxSpeed, accelTime);
        this.gyroTarget = Position.normalizeAngle(Math.toRadians(gyroLocalizerTarget));
        this.gyroTolerance = Position.normalizeAngle(Math.toRadians(gyroTolerance));
    }

    public APPCCommand(Path<Position> path, Position startPos, double lookAhead,
                       double tolerance, boolean isBackwards,
                       double minSpeed, double maxSpeedDist, double maxSpeed, double accelTime,
                       double visionDist) {
        this(path, startPos, lookAhead, tolerance, isBackwards, minSpeed, maxSpeedDist,
                maxSpeed, accelTime);
        startSearchingForVisionSqaured = visionDist*visionDist;
    }

    @Override
    protected void atInit(){
        system.setRampRate(this.accelTime);
        if (this.startPos != null)
            Localizer.getInstance().reset(Chassis.getInstance().getLeftDistance(),
                    Chassis.getInstance().getRightDistance(), this.startPos);
        else
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
//        RemoteGuydeBugger.report(-loc.getX(), -loc.getY(), loc.getAngle());
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
        if (m_controller.isFinished(system.getLocation())) {
            logger.debug("APPC finished because tolerance is reached.");
            return true;
        }
        if (startSearchingForVisionSqaured > 0){
            double dist = Point.distSqared(m_controller.getPath().getLast(),
                    Localizer.getInstance().getLocation());
            if (dist < startSearchingForVisionSqaured
                && VisionMaster.getInstance().isDataValid()) {
                logger.debug("APPC finished because vision target seen at distance sqrt({}) m.", dist);
                return true;
            }
        }
        if (gyroTolerance > 0 && gyroTarget != 4590){
            if (Math.abs(Position.normalizeAngle(Chassis.getInstance().getLocation().getAngle() - gyroTarget))
                    < gyroTolerance){
                logger.debug("APPC finished because target angle was reached.");
                return true;
            }
        }
        return false;
    }
}