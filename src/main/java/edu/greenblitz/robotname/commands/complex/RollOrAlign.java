package edu.greenblitz.robotname.commands.complex;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.OIModeConditionCommand;
import edu.greenblitz.robotname.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.roller.SmartExtendAndRollIn;
import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

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
