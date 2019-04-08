package edu.greenblitz.knockdown.commands.simple.climber;

import edu.greenblitz.knockdown.subsystems.Climber;
import edu.greenblitz.utils.command.base.JoystickCommand;
import edu.greenblitz.utils.hid.SmartJoystick;

public class ClimberPExtenderControl extends JoystickCommand<Climber.Extender> {

    private static final double RETRACTED_LOW_SPEED_START = 0.03,
            RETRACTED_SLOWDOWN_START = 0.08,
            RETRACTED_SLOW_VEL = 0.05;

    private static final double EXTENDED_LOW_SPEED_START = 0.49,
            EXTENDED_SLOWDOWN_START = 0.43,
            EXTENDED_SLOW_VEL = 0.2;

    private static final double MAX_VEL = 1;

    private double kpExtending;
    private double kpRetracting;

    public ClimberPExtenderControl(SmartJoystick joystick) {
        super(Climber.getInstance().getExtender(), joystick);
        kpExtending = (MAX_VEL - EXTENDED_SLOW_VEL) / (EXTENDED_LOW_SPEED_START - EXTENDED_SLOWDOWN_START);
        kpRetracting = (MAX_VEL - RETRACTED_SLOW_VEL) / (RETRACTED_SLOWDOWN_START - RETRACTED_LOW_SPEED_START);
    }

    @Override
    protected void execute() {
        if (Math.abs(getStickValue()) < 0.05) {
            moveExtender(0);
            return;
        }
        if (extending()) {
            if (getExtenderValue() > EXTENDED_LOW_SPEED_START) {
                moveExtender(getStickValue() * EXTENDED_SLOW_VEL);
            } else if (getExtenderValue() > EXTENDED_SLOWDOWN_START) {
                moveExtender(getStickValue() * (kpExtending * (EXTENDED_LOW_SPEED_START - getExtenderValue()) + EXTENDED_SLOW_VEL));
            } else {
                moveExtender(getStickValue() * MAX_VEL);
            }
        } else {
            if (getExtenderValue() < RETRACTED_LOW_SPEED_START) {
                moveExtender(getStickValue() * RETRACTED_SLOW_VEL);
            } else if (getExtenderValue() < RETRACTED_SLOWDOWN_START) {
                moveExtender(getStickValue() * (kpRetracting * (getExtenderValue() - RETRACTED_LOW_SPEED_START) + RETRACTED_SLOW_VEL));
            } else {
                moveExtender(getStickValue() * MAX_VEL);
            }
        }
    }

    @Override
    protected void atEnd() {
        system.extend(0);
    }

    private void moveExtender(double p) {
        Climber.getInstance().getExtender().extend(p);
    }

    private double getExtenderValue() {
        return -Climber.getInstance().getExtender().getHeight();
    }

    private boolean extending() {
        return getStickValue() < 0;
    }

    private double getStickValue() {
        return -SmartJoystick.Axis.LEFT_Y.getValue(joystick);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
