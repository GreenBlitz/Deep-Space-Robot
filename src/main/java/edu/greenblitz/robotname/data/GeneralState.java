package edu.greenblitz.robotname.data;

import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Roller;

/**
 * A book keeping class about the state of the robot.
 *
 * <p>
 * As of right now it only saves changes, but we may have to use the sensors for validation.
 * If such case arises, implement {@link GeneralState#updateBall()} and {@link GeneralState#updateHatch()}
 * </p>
 */
public class GeneralState {
    private boolean m_hasBall = false;
    private boolean m_hasHatch = false;

    private Elevator elevator;
    private Roller roller;
    private Kicker kicker;

    public GeneralState() {
        elevator = Elevator.getInstance();
        roller = Roller.getInstance();
        kicker = Kicker.getInstance();
    }

    public void ballReceived() {
        m_hasBall = true;
    }

    public void ballShot() {
        m_hasBall = false;
    }

    public boolean hasBall() {
        return m_hasBall;
    }

    /**
     * Ensures that the stats's kept ball status is in sync with reality. Will log a warning and change it if it isn't.
     * As of right now it isn't implemented, but if comes the time and we'll need it...
     *
     * @see GeneralState
     * @see GeneralState#updateHatch()
     */
    private void updateBall() {

    }


    public void hatchRecived() {
        m_hasHatch = true;
    }

    public void hatchShot() {
        m_hasHatch = false;
    }

    public boolean hasHatch() {
        return m_hasHatch;
    }

    /**
     * Ensures that the stats's kept hatch status is in sync with reality. Will log a warning and change it if it isn't.
     * As of right now it isn't implemented, but if comes the time and we'll need it...
     *
     * @see GeneralState
     * @see GeneralState#updateBall()
     */
    private void updateHatch() {

    }


    public void update() {
        updateBall();
        updateHatch();
    }

    public void reset() {
        m_hasBall = false;
        m_hasHatch = false;
    }
}
