package edu.greenblitz.knockdown.commands.complex;

import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.commands.OIModeConditionCommand;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.knockdown.commands.complex.roller.SmartExtendAndRollIn;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.command.base.GBCommand;

public class RollOrAlign {
    public static class Main extends GBCommand {

        @Override
        protected void atInit(){
            if (OI.getOiState() == OI.State.HATCH)
                new VisionCollectHatchPanel().start();
            else
                new CollectWithRollerAndDrive().start();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }
    }

    public static class Cleanup extends GBCommand {

        @Override
        protected void atInit(){
            if (OI.getOiState() == OI.State.CARGO)
                new RetractAndStopRoller(300).start();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }
    }

    public static class CollectWithRollerAndDrive extends CommandChain {
        public CollectWithRollerAndDrive() {
            addParallel(new ArcadeDriveByJoystick(OI.getMainJoystick()), new SmartExtendAndRollIn());
        }
    }
}
