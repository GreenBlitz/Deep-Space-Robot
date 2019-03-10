package edu.greenblitz.robotname.data;

import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.PeriodicRunner;
import edu.greenblitz.utils.encoder.IEncoder;
import edu.wpi.first.wpilibj.DriverStation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.greenblitz.debug.RemoteGuydeBugger;
import org.greenblitz.motion.app.Localizer;
import org.greenblitz.motion.base.Position;

public class LocalizerRunner extends PeriodicRunner {

    private Localizer m_localizer;

    private IEncoder m_leftEncoder;
    private IEncoder m_rightEncoder;

    private boolean m_useGyro;

    private boolean m_resetOnDisable = false;

    private Logger logger;

    public LocalizerRunner(long period, double wheelBase, IEncoder leftEncoder, IEncoder rightEncoder) {
        super(period);
        logger = LogManager.getLogger(getClass());

        m_localizer = Localizer.getInstance();
        m_localizer.configure(wheelBase, 0, 0);
        m_leftEncoder = leftEncoder;
        leftEncoder.reset();
        m_rightEncoder = rightEncoder;
        rightEncoder.reset();
    }

    public void enableGyro() {
        m_useGyro = true;
        logger.debug("gyro enabled!");
    }

    public void disableGyro() {
        m_useGyro = false;
        logger.debug("gyro disabled!");
    }

    public LocalizerRunner(double wheelBase, IEncoder leftEncoder, IEncoder rightEncoder) {
        this(20, wheelBase, leftEncoder, rightEncoder);
    }

    public void resetOnDisable() {
        setResetOnDisable(true);
    }

    public void dontResetOnDisable() {
        setResetOnDisable(false);
    }

    public void setResetOnDisable(boolean value) {
        m_resetOnDisable = value;
    }

    public boolean shouldResetOnDisable() {
        return m_resetOnDisable;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    protected void whenActive() {
        var lTicks = m_leftEncoder.getNormalizedTicks();
        var rTicks = m_rightEncoder.getNormalizedTicks();

        if (m_useGyro) {
            m_localizer.update(lTicks, rTicks, -Math.toRadians(Chassis.getInstance().getAngle()));
        } else {
            m_localizer.update(lTicks, rTicks);
        }

        var pos = m_localizer.getLocation();
        RemoteGuydeBugger.report(pos.getX(), pos.getY(), pos.getAngle());
    }

    @Override
    protected void whenInActive() {
        if (DriverStation.getInstance().isDisabled() && shouldResetOnDisable()) reset();
    }

    public Position getLocation() {
        return m_localizer.getLocation();
    }

    public void reset() {
        m_localizer.reset(m_leftEncoder.getNormalizedTicks(), m_rightEncoder.getNormalizedTicks());
        logger.debug("reset!");
    }

    @Override
    public void start() {
        super.start();
        logger.debug("start!");
    }

    @Override
    public void stop() {
        super.stop();
        logger.debug("stop!");
    }

    public void forceSetLocation(Position location, double currentLeftDistance, double currentRightDistance) {
        m_localizer.reset(currentLeftDistance, currentRightDistance, location);
    }
}