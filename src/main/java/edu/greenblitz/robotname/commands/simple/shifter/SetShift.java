package edu.greenblitz.robotname.commands.simple.shifter;

import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command sets the ShifterState to the state given and set in the constructor.
 * This command uses the shifter subsystem.
 * The command will stop as soon as the shift is set.
 *
 * @see Command
 * @see Shifter
 */

public class SetShift extends SubsystemCommand<Shifter> {

    private Shifter.ShifterState m_shifterState;

    public SetShift(Shifter.ShifterState state) {
        super(Shifter.getInstance());
        m_shifterState = state;
    }

    @Override
    protected void execute() {
        system.setShift(m_shifterState);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
