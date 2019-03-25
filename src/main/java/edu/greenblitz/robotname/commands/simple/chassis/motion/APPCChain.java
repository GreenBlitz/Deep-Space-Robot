package edu.greenblitz.robotname.commands.simple.chassis.motion;

import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.wpi.first.wpilibj.command.CommandGroup;
import org.greenblitz.motion.app.AdaptivePurePursuitController;
import org.greenblitz.motion.base.Position;

public class APPCChain extends CommandGroup {

    public APPCChain(APPCCommand... controllers){

        for (APPCCommand apc : controllers){
            addSequential(apc);
        }

    }

}