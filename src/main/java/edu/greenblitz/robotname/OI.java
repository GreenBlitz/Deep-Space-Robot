package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.shifter.ToPower;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.utils.hid.CustomControlBoard;
import edu.greenblitz.utils.hid.SmartJoystick;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.BasicAngleInterpolator;
import org.greenblitz.motion.pathing.Path;

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

        mainJoystick.A.whenPressed(new APPCCommand(
                new Path<>(
                        new Position(0, 0, 0),
                        new Position(0, 0.1, 0),
                        new Position(0, 0.2, 0),
                        new Position(0, 0.3, 0),
                        new Position(0, 0.4, 0),
                        new Position(0, 0.5, 0),
                        new Position(0, 0.6, 0),
                        new Position(0, 0.7, 0),
                        new Position(0, 0.8, 0),
                        new Position(0, 0.9, 0),
                        new Position(0, 1, 0),
                        new Position(0.1, 1, 0),
                        new Position(0.2, 1, 0),
                        new Position(0.3, 1, 0),
                        new Position(0.4, 1, 0),
                        new Position(0.5, 1, 0),
                        new Position(0.6, 1, 0),
                        new Position(0.7, 1, 0),
                        new Position(0.8, 1, 0),
                        new Position(0.9, 1, 0),
                        new Position(1, 1, 0),
                        new Position(1, 1.1, 0),
                        new Position(1, 1.2, 0),
                        new Position(1, 1.3, 0),
                        new Position(1, 1.4, 0),
                        new Position(1, 1.5, 0),
                        new Position(1, 1.6, 0),
                        new Position(1, 1.7, 0),
                        new Position(1, 1.8, 0),
                        new Position(1, 1.9, 0),
                        new Position(1, 2, 0)),
                0.8, 0.2, false, 0, 0.5, 1));
        mainJoystick.X.whenPressed(new ToSpeed());
        mainJoystick.Y.whenPressed(new ToPower());
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
