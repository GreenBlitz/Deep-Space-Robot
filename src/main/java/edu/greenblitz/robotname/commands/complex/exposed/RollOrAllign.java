package edu.greenblitz.robotname.commands.complex.exposed;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SmartExtendAndRollIn;
import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class RollOrAllign {
    public static class Main extends ConditionalCommand {
        public Main() {
            super(new VisionCollectHatchPanel(), new CollectWithRollerAndDrive());
        }

        @Override
        protected boolean condition() {
            return OI.isStateHatch();
        }
    }

    public static class Cleanup extends ConditionalCommand {
        public Cleanup() {
            super(new ArcadeDriveByJoystick(OI.getMainJoystick()), new RetractAndStopRoller(300));
        }

        @Override
        protected boolean condition() {
            return OI.isStateHatch();
        }
    }

    public static class CollectWithRollerAndDrive extends CommandChain {
        public CollectWithRollerAndDrive() {
            addParallel(new ArcadeDriveByJoystick(OI.getMainJoystick()), new SmartExtendAndRollIn());
        }
    }
}
