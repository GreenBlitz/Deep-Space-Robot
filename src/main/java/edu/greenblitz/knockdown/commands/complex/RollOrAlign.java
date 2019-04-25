package edu.greenblitz.knockdown.commands.complex;

import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.commands.OIModeConditionCommand;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.knockdown.commands.complex.roller.SmartExtendAndRollIn;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.utils.command.CommandChain;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.wpi.first.wpilibj.command.Command;

public class RollOrAlign {

    public static Command rollBack = new RetractAndStopRoller();


    public static class Main extends GBCommand {

        public static Command collectPanel = new VisionCollectHatchPanel();
        public static Command getCargo = new CollectWithRollerAndDrive();

        @Override
        protected void atInit(){
            if (OI.getOiState() == OI.State.HATCH)
                collectPanel.start();
            else
                getCargo.start();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }
    }

    public static class Cleanup extends GBCommand {

        public static Command arcadeDrive = new ArcadeDriveByJoystick(OI.getMainJoystick());

        @Override
        protected void atInit(){
            if (OI.getOiState() == OI.State.CARGO)
                rollBack.start();
            else
                arcadeDrive.start();
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
