package edu.greenblitz.knockdown.commands.simple.chassis.motion;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class APPCChain extends CommandGroup {

    public APPCChain(APPCCommand... controllers){

        for (APPCCommand apc : controllers){
            addSequential(apc);
        }

    }

}