package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.exposed.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.exposed.HybridAlign;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.Auto2HatchCargoship;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.AutoFallAndThreeHalfs;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.SemiAuto1Point5Hatch;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.ChangeTargetFocus;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceGameObject;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.exposed.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystickRestricted;
import edu.greenblitz.robotname.commands.complex.hidden.climber.StopClimbing;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SmartExtendAndRollIn;
import edu.greenblitz.robotname.commands.complex.hidden.roller.ToggleRoller;
import edu.greenblitz.robotname.commands.simple.chassis.FallWithNavx;
import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.robotname.commands.simple.shifter.AutoChangeShift;
import edu.greenblitz.robotname.commands.simple.shifter.KeepShift;
import edu.greenblitz.robotname.commands.simple.shifter.ToggleShift;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.greenblitz.utils.command.ResetCommands;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Optional;

public class OI {
    public enum State {
        CARGO,
        HATCH
    }

    private static SmartJoystick mainJoystick;
    private static SmartJoystick sideJoystick;
    private static State oiState = State.HATCH;

    public static SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public static SmartJoystick getSideJoystick() {
        return sideJoystick;
    }

    public static class ToHatchMode extends GBCommand {
        @Override
        public Optional<edu.greenblitz.utils.sm.State> getDeltaState() {
            return Optional.empty();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void atInit() {
            oiState = State.HATCH;
        }
    }

    public static class ToCargoMode extends GBCommand {
        @Override
        public Optional<edu.greenblitz.utils.sm.State> getDeltaState() {
            return Optional.empty();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void atInit() {
            oiState = State.CARGO;
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
        mainJoystick.POV_LEFT.whenPressed(new ChangeTargetFocus(VisionMaster.Focus.LEFT));
        mainJoystick.POV_RIGHT.whenPressed(new ChangeTargetFocus(VisionMaster.Focus.RIGHT));
        mainJoystick.POV_DOWN.whenPressed(new ChangeTargetFocus(VisionMaster.Focus.MIDDLE));

        mainJoystick.POV_UP.whenPressed(new AutoFallAndThreeHalfs());

        mainJoystick.START.whenPressed(new ClimbByJoystick(mainJoystick, mainJoystick, sideJoystick));
        mainJoystick.BACK.whenPressed(new StopClimbing());

        mainJoystick.B.whenPressed(new ReleaseHatch());
        mainJoystick.B.whenReleased(new HoldHatch());

        mainJoystick.A.whenPressed(new TogglePokerExtender());

        mainJoystick.L1.whenPressed(new VisionCollectHatchPanel());
        mainJoystick.L1.whenReleased(new ArcadeDriveByJoystick(mainJoystick));
        mainJoystick.R1.whenPressed(new VisionPlaceGameObject());
        mainJoystick.R1.whenReleased(new ArcadeDriveByJoystick(mainJoystick));

        mainJoystick.L3.whenPressed(new ToggleShift());
        // mainJoystick.L1.whenPressed(new ExtendAndRollIn());
       // mainJoystick.L1.whenReleased(new RetractAndStopRoller(100));

       // mainJoystick.Y.whenPressed(new SafeMoveElevator(Elevator.Level.CARGO_SHIP));

        mainJoystick.START.whenPressed(new ToCargoMode());
        mainJoystick.BACK.whenPressed(new ToHatchMode());

        //mainJoystick.R1.whenPressed(new SafeMoveElevator(Elevator.Level.GROUND));

        mainJoystick.X.whenPressed(new FallWithNavx());

        sideJoystick.R1.whenPressed(new SafeMoveElevator(Elevator.Level.GROUND));
        sideJoystick.A.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_LOW));
        sideJoystick.B.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_MID));
        sideJoystick.Y.whenPressed(new SafeMoveElevator(Elevator.Level.ROCKET_HIGH));
        sideJoystick.X.whenPressed(new SafeMoveElevator(Elevator.Level.CARGO_SHIP));
    }

    private static void initOfficialBindings() {
        mainJoystick.START.whenPressed(new ClimbByJoystick(mainJoystick, mainJoystick, sideJoystick));
        mainJoystick.BACK.whenPressed(new StopClimbing());
        mainJoystick.A.whenPressed(new TogglePokerExtender());

        mainJoystick.B.whenPressed(new ReleaseHatch());
        mainJoystick.B.whenReleased(new HoldHatch());

        mainJoystick.Y.whenPressed(new VisionCollectHatchPanel());
        mainJoystick.Y.whenReleased(new ArcadeDriveByJoystick(mainJoystick));
        mainJoystick.R1.whenPressed(new VisionPlaceGameObject());
        mainJoystick.R1.whenReleased(new ArcadeDriveByJoystick(mainJoystick));

        mainJoystick.L3.whenPressed(new ToggleShift());

        mainJoystick.X.whenPressed(new KickBall());

        mainJoystick.L1.whenPressed(new SmartExtendAndRollIn());
        mainJoystick.L1.whenReleased(new RetractAndStopRoller(300));

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

        POVButton restrictClimbing = new POVButton(sideJoystick.getRawJoystick(), 0);
        restrictClimbing.whenPressed(new ClimbByJoystickRestricted(mainJoystick, mainJoystick, sideJoystick));

        sideJoystick.START.whenPressed(new ToCargoMode());
        sideJoystick.BACK.whenPressed(new ToHatchMode());
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
        oiState = state;
    }

    public static void update() {
        SmartDashboard.putString("Mode", oiState.toString());
    }
}