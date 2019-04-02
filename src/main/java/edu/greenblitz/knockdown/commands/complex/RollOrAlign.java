package edu.greenblitz.knockdown.commands.complex;

import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.commands.OIModeConditionCommand;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.knockdown.commands.complex.roller.SmartExtendAndRollIn;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.utils.command.CommandChain;

public class RollOrAlign {
    public static class Main extends OIModeConditionCommand {
        public Main() {
            super(new VisionCollectHatchPanel(), new CollectWithRollerAndDrive());
        }
    }

    public static class Cleanup extends OIModeConditionCommand {
        public Cleanup() {
            super(new ArcadeDriveByJoystick(OI.getMainJoystick()), new RetractAndStopRoller(300));
        }
    }

    public static class CollectWithRollerAndDrive extends CommandChain {
        public CollectWithRollerAndDrive() {
            addParallel(new ArcadeDriveByJoystick(OI.getMainJoystick()), new SmartExtendAndRollIn());
        }
    }
}
