package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.exposed.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.exposed.HoldHatchAndMoveToFloor;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionCollectHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision.VisionPlaceHatchPanel;
import edu.greenblitz.robotname.commands.complex.exposed.elevator.SafeMoveElevator;
import edu.greenblitz.robotname.commands.complex.exposed.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystickRestricted;
import edu.greenblitz.robotname.commands.complex.hidden.climber.StopClimbing;
import edu.greenblitz.robotname.commands.complex.hidden.roller.ToggleRoller;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.robotname.commands.simple.roller.ExtendAndRollIn;
import edu.greenblitz.robotname.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.robotname.commands.simple.shifter.AutoChangeShift;
import edu.greenblitz.robotname.commands.simple.shifter.KeepShift;
import edu.greenblitz.robotname.commands.simple.shifter.ToggleShift;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.command.GBCommand;
import edu.greenblitz.utils.command.ResetCommands;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.greenblitz.utils.sm.State;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Optional;

public class OI {
    public enum GameObject {
        CARGO,
        HATCH
    }

    private static SmartJoystick mainJoystick;
    private static SmartJoystick sideJoystick;
    private static GameObject oiGameObject = GameObject.HATCH;

    public static SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public static SmartJoystick getSideJoystick() {
        return sideJoystick;
    }

    private static class ToHatchMode extends GBCommand {
        @Override
        public Optional<State> getDeltaState() {
            return Optional.empty();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void atInit() {
            oiGameObject = GameObject.HATCH;
        }
    }

    private static class ToCargoMode extends GBCommand {
        @Override
        public Optional<State> getDeltaState() {
            return Optional.empty();
        }

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void atInit() {
            oiGameObject = GameObject.CARGO;
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
        mainJoystick.A.whenPressed(new TogglePokerExtender());

        mainJoystick.B.whenPressed(new ReleaseHatch());
        mainJoystick.B.whenReleased(new HoldHatchAndMoveToFloor());

        mainJoystick.R1.whenPressed(new VisionCollectHatchPanel());
        mainJoystick.L1.whenPressed(new VisionPlaceHatchPanel());
    }

    private static void initOfficialBindings() {
        mainJoystick.START.whenPressed(new ClimbByJoystick(mainJoystick, mainJoystick, sideJoystick));
        mainJoystick.BACK.whenPressed(new StopClimbing());
        mainJoystick.A.whenPressed(new TogglePokerExtender());

        mainJoystick.B.whenPressed(new ReleaseHatch());
        mainJoystick.B.whenReleased(new HoldHatchAndMoveToFloor());

        mainJoystick.L3.whenPressed(new ToggleShift());

        mainJoystick.X.whenPressed(new KickBall());

        mainJoystick.L1.whenPressed(new ExtendAndRollIn());
        mainJoystick.L1.whenReleased(new RetractAndStopRoller(300));

        POVButton autoShiftOn = new POVButton(mainJoystick.getRawJoystick(), 0);
        autoShiftOn.whenPressed(new AutoChangeShift());
        POVButton autoShiftOff = new POVButton(mainJoystick.getRawJoystick(), 180);
        autoShiftOff.whenPressed(new KeepShift());

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

    public static GameObject getOIState() {
        return oiGameObject;
    }

    public static boolean isStateCargo() {
        return getOIState() == GameObject.CARGO;
    }

    public static boolean isStateHatch() {
        return getOIState() == GameObject.HATCH;
    }

    public static void update() {
        SmartDashboard.putString("Mode", oiGameObject.toString());
    }
}