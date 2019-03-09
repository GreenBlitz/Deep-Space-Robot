package edu.greenblitz.robotname.commands.complex;

import edu.greenblitz.utils.command.chain.CommandChain;
import edu.wpi.first.wpilibj.command.Scheduler;

public class HoldAllCommands extends CommandChain {
    @Override
    protected void initChain() {
        /*addParallel(
                new StopChassis(),
                new StopClimbing(),
                new BrakeElevator(),
                new Unkick(),
                new CompressorOff(0),
                new RetractPoker(),
                new RetractRoller(),
                new DynamicRequire(Shifter.getInstance())
        );*/
        Scheduler.getInstance().removeAll();
    }
}
