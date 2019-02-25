package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.simple.pneumatics.CompressorOff;
import edu.greenblitz.robotname.commands.simple.pneumatics.CompressorOn;
import edu.greenblitz.robotname.commands.simple.poker.HoldHatch;
import edu.greenblitz.robotname.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
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

    public static void init() {
        mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        sideJoystick = new CustomControlBoard(RobotMap.Joysticks.SIDE);

        mainJoystick.A.whenPressed(new HoldHatch());
        mainJoystick.B.whenPressed(new ReleaseHatch());
        mainJoystick.X.whenPressed(new CompressorOff(0));
        mainJoystick.Y.whenPressed(new CompressorOn(0));
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

    }
}
