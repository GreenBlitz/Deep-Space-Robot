package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.complex.exposed.cargo.KickerEnsureBallOut;
import edu.greenblitz.robotname.commands.complex.hidden.climber.ClimbByJoystick;
import edu.greenblitz.robotname.commands.complex.hidden.elevator.PrepareToExchangeGameObject;
import edu.greenblitz.robotname.commands.complex.hidden.kicker.KickBall;
import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerExtended;
import edu.greenblitz.robotname.commands.complex.hidden.poker.EnsurePokerRetracted;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeExtendAndRollIn;
import edu.greenblitz.robotname.commands.complex.hidden.roller.SafeRetractAndRollIn;
import edu.greenblitz.robotname.commands.complex.hidden.roller.ToggleRoller;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.robotname.subsystems.Elevator;
import edu.greenblitz.utils.hid.CustomControlBoard;
import edu.greenblitz.utils.hid.SmartJoystick;

public class OI {
    public enum State {
        CARGO,
        HATCH
    }

    private static SmartJoystick mainJoystick;
    private static CustomControlBoard sideJoystick;
    private static State oiState;

    public static SmartJoystick getMainJoystick() {
        return mainJoystick;
    }

    public static CustomControlBoard getSideJoystick() {
        return sideJoystick;
    }

    public static void initJoysticks() {
        if (mainJoystick == null) mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        if (sideJoystick == null) sideJoystick = new CustomControlBoard(RobotMap.Joysticks.SIDE);
    }

    public static void initBindings() {
        mainJoystick.A.whenPressed(new KickBall());
        mainJoystick.B.whenPressed(new TogglePokerExtender());
        mainJoystick.X.whenPressed(new ToggleRoller());

        sideJoystick.SMALL_RED.whenPressed(new PrepareToExchangeGameObject(State.CARGO, Elevator.Level.GROUND));
        sideJoystick.BOTTOM_SMALL_GREEN.whenPressed(new PrepareToExchangeGameObject(getOIState(), Elevator.Level.CARGO_SHIP));
        sideJoystick.SMALL_YELLOW.whenPressed(new PrepareToExchangeGameObject(getOIState(), Elevator.Level.ROCKET_LOW));
        sideJoystick.SMALL_BLUE.whenPressed(new PrepareToExchangeGameObject(getOIState(), Elevator.Level.ROCKET_MID));
        sideJoystick.TOP_SMALL_GREEN.whenPressed(new PrepareToExchangeGameObject(getOIState(), Elevator.Level.ROCKET_HIGH));

        //TODO: decide on good buttons
        sideJoystick.LEFT_WHITE.whenActive(new ClimbByJoystick());
        sideJoystick.RIGHT_WHITE.whenPressed(new SafeExtendAndRollIn());
        sideJoystick.RIGHT_WHITE.whenReleased(new SafeRetractAndRollIn());
        sideJoystick.RIGHT_BLACK.whenPressed(new EnsurePokerExtended());
        sideJoystick.RIGHT_BLACK.whenReleased(new EnsurePokerRetracted());
        sideJoystick.LEFT_BLACK.whenPressed(new HoldHatch());
        sideJoystick.LEFT_BLACK.whenReleased(new ReleaseHatch());
        sideJoystick.BIG_YELLOW.whenPressed(new KickerEnsureBallOut());
    }

    public static State getOIState() {
        return oiState;
    }

    public static boolean isStateCargo() {
        return getOIState() == State.CARGO;
    }

    public static boolean isStateHatch() {
        return getOIState() == State.HATCH;
    }

    public static void update() {
        oiState = sideJoystick.STATE_SWITCH.get() ? State.CARGO : State.HATCH;
    }
}