package edu.greenblitz.knockdown;

import edu.greenblitz.knockdown.commands.complex.RollOrAlign;
import edu.greenblitz.knockdown.commands.complex.chassis.autonomous.AutoFallAndThreeHalfs;
import edu.greenblitz.knockdown.commands.complex.chassis.autonomous.AutoThreeHalfFarRocket;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.ChangeTargetFocus;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionCollectHatchPanel;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionPlaceGameObject;
import edu.greenblitz.knockdown.commands.complex.climber.ClimbByJoystick;
import edu.greenblitz.knockdown.commands.complex.climber.StopClimbing;
import edu.greenblitz.knockdown.commands.complex.elevator.SafeMoveElevator;
import edu.greenblitz.knockdown.commands.complex.kicker.KickBall;
import edu.greenblitz.knockdown.commands.complex.roller.SmartExtendAndRollIn;
import edu.greenblitz.knockdown.commands.complex.roller.ToggleRoller;
import edu.greenblitz.knockdown.commands.simple.chassis.FallWithNavx;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.knockdown.commands.simple.poker.HoldHatch;
import edu.greenblitz.knockdown.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.knockdown.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.knockdown.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.knockdown.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.knockdown.commands.simple.shifter.AutoChangeShift;
import edu.greenblitz.knockdown.commands.simple.shifter.ToggleShift;
import edu.greenblitz.knockdown.data.Paths;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.ResetCommands;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.base.Position;

public class OI {
    public enum State {
        CARGO,
        HATCH
    }

    private static SmartJoystick mainJoystick;
    private static SmartJoystick sideJoystick;

    public static State getOiState() {
        return oiState;
    }

    private static State oiState = State.HATCH;

    public static SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public static SmartJoystick getSideJoystick() {
        return sideJoystick;
    }

    public static class ToHatchMode extends GBCommand {

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void atInit() {
            setOIState(State.HATCH);
        }
    }

    public static class ToCargoMode extends GBCommand {

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void atInit() {
            setOIState(State.CARGO);
        }
    }

    public static void initJoysticks() {
        if (mainJoystick == null) mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        if (sideJoystick == null) sideJoystick = new SmartJoystick(RobotMap.Joysticks.SIDE);
    }

    public static void initBindings() {
//        initOfficialBindings(); // For real game shit
        initTestBindings(); // For testing code
    }

    private static void initTestBindings() {
//        mainJoystick.R1.whenPressed(new VisionPlaceGameObject());
//        mainJoystick.R1.whenReleased(new ArcadeDriveByJoystick(mainJoystick));
        mainJoystick.R1.whenPressed(new KickBall());

        mainJoystick.L1.whileHeld(new RollOrAlign.Main());
        mainJoystick.L1.whenReleased(new RollOrAlign.Cleanup());

        mainJoystick.START.whenPressed(new SafeMoveElevator(Elevator.Level.GROUND));
        mainJoystick.A.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_LOW));
        mainJoystick.B.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_MID));
        mainJoystick.Y.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_HIGH));
        mainJoystick.X.whenPressed(new SafeMoveElevator(Elevator.Level.CARGO_SHIP));

        mainJoystick.POV_UP.whenPressed(new ToggleShift());
    }

    private static void initOfficialBindings() {
        mainJoystick.START.whenPressed(new ClimbByJoystick(mainJoystick, mainJoystick, sideJoystick));
        mainJoystick.BACK.whenPressed(new StopClimbing());
        mainJoystick.A.whenPressed(new TogglePokerExtender());

        mainJoystick.B.whenPressed(new ReleaseHatch());
        mainJoystick.B.whenReleased(new HoldHatch());

        mainJoystick.Y.whenPressed(new SmartExtendAndRollIn());
        mainJoystick.Y.whenReleased(new RetractAndStopRoller(300));

        mainJoystick.R1.whenPressed(new VisionPlaceGameObject());
        mainJoystick.R1.whenReleased(new ArcadeDriveByJoystick(mainJoystick));

        mainJoystick.L3.whenPressed(new ToggleShift());

        mainJoystick.X.whenPressed(new KickBall());

        mainJoystick.L1.whileHeld(new RollOrAlign.Main());
        mainJoystick.L1.whenReleased(new RollOrAlign.Cleanup());

        mainJoystick.POV_UP.whenPressed(new AutoChangeShift());
        mainJoystick.POV_LEFT.whenPressed(new ChangeTargetFocus(VisionMaster.Focus.LEFT));
        mainJoystick.POV_RIGHT.whenPressed(new ChangeTargetFocus(VisionMaster.Focus.RIGHT));
        mainJoystick.POV_DOWN.whenPressed(new ChangeTargetFocus(VisionMaster.Focus.MIDDLE));

        sideJoystick.L3.whenPressed(new ResetCommands());
        sideJoystick.R1.whenPressed(new SafeMoveElevator(Elevator.Level.GROUND));
        sideJoystick.A.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_LOW));
        sideJoystick.B.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_MID));
        sideJoystick.Y.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_HIGH));
        sideJoystick.X.whenPressed(new SafeMoveElevator(Elevator.Level.CARGO_SHIP));
        sideJoystick.L1.whenReleased(new ToggleRoller());

//        POVButton restrictClimbing = new POVButton(sideJoystick.getRawJoystick(), 0);
//        restrictClimbing.whenPressed(new ClimbByJoystickRestricted(mainJoystick, mainJoystick, sideJoystick));

        sideJoystick.START.whenPressed(new ToCargoMode());
        sideJoystick.BACK.whenPressed(new ToHatchMode());

//        sideJoystick.POV_RIGHT.whenPressed(new AutoFallAndThreeHalfs(false, false));
//        sideJoystick.POV_LEFT.whenPressed(new AutoFallAndThreeHalfs(true, false));
//        sideJoystick.POV_UP.whenPressed(new AutoThreeHalfFarRocket(false, false));
//        sideJoystick.POV_DOWN.whenPressed(new AutoThreeHalfFarRocket(true, false));
    }

    public static State getState() {
        return oiState;
    }

    public static boolean isStateCargo() {
        return getState() == State.CARGO;
    }

    public static boolean isStateHatch() {
        return getState() == State.HATCH;
    }

    public static void setOIState(State state) {
        VisionMaster.getInstance().reportOIMode(state);
        oiState = state;
    }

    public static void update() {
        SmartDashboard.putString("Mode", oiState.toString());
    }
}