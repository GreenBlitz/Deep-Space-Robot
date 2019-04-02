package edu.greenblitz.knockdown.commands.simple.chassis;

public class ResetNavx extends ChassisBaseCommand {

    @Override
    protected void atInit(){
        system.resetNavx();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
