package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.chassis.motion.ResetLocalizer;
import edu.greenblitz.robotname.data.Paths;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.DynamicRequire;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.chain.CommandChain;
import edu.greenblitz.utils.command.dynamic.DynamicCommand;
import org.greenblitz.motion.base.Position;

import java.awt.*;

/**
 * Like two crago hatch but starts at cargohip
 */
public class SemiAuto1Point5Hatch extends CommandChain {

        public SemiAuto1Point5Hatch() {
        addSequential(new VisionPlaceHatchPanel());

        addSequential(new DynamicCommand() {
            @Override
            protected GBCommand pick() {
                Position pos = Paths.get("Cargoship2").get(0);
                pos.setAngle(-Chassis.getInstance().getAngle());
                return new ResetLocalizer(pos);
            }
        });

        addParallel(new APPCCommand(Paths.get("Cargoship2"),
                0.6, 0.2, true, 0.25, 0.4, 0.4, 0.4)
        );

        addSequential(new APPCCommand(Paths.get("Cargoship3"),
                .8, 0.25, false, 0.3, .3, 0.6, .6)
        );

        addSequential(new VisionCollectHatchPanel());

        addSequential(new APPCCommand(Paths.get("CargoRocket4"),
                .8, .25, true, .2, 1, 0.6, .6)
        );

        addSequential(new VisionPlaceHatchPanel());
    }

}
