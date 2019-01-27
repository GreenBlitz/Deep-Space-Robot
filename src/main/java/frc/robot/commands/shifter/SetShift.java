package frc.robot.commands.shifter;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Shifter;

/**
 * This command sets the ShifterState to the state given and set in the constructor.
 * This command uses the shifter subsystem.
 * The command will stop as soon as the shift is set.
 *
 * @see Command
 * @see org.usfirst.frc.team4590.robot.subsystems.Shifter.ShifterState
 * @see Shifter
 */

public class SetShift extends Command {

    private static Shifter.ShifterState m_shifterState;

    public SetShift(Shifter.ShifterState state) {
        requires(Shifter.getInstance());
        m_shifterState = state;
    }

    @Override
    protected void execute() {
        Shifter.getInstance().setShift(m_shifterState);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
