package edu.greenblitz.utils.command.dynamic;

import edu.wpi.first.wpilibj.command.Command;

public abstract class MultiChoiceCommand extends Command {
    private Command chosen;

    @Override
    public synchronized void start() {
        chosen = pick();
        chosen.start();
    }

    @Override
    protected void end() {
        chosen = null;
    }

    @Override
    protected boolean isFinished() {
        return chosen.isCompleted();
    }

    protected abstract Command pick();
}
