package edu.greenblitz.utils.sendables;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class SendableDoubleSolenoid extends DoubleSolenoid {
    public SendableDoubleSolenoid(int forwardChannel, int reverseChannel) {
        super(forwardChannel, reverseChannel);
    }

    public SendableDoubleSolenoid(int moduleNumber, int forwardChannel, int reverseChannel) {
        super(moduleNumber, forwardChannel, reverseChannel);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        super.initSendable(builder);
        builder.addBooleanProperty("state", this::getState, this::setState);
    }

    private void setState(boolean newState) {
        set(newState ? Value.kForward : Value.kReverse);
    }

    private boolean getState() {
        return get() == Value.kForward;
    }
}
