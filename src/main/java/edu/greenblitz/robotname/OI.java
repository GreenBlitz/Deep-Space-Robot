package edu.greenblitz.robotname;

import edu.greenblitz.robotname.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.robotname.commands.simple.chassis.motion.APPCCommand;
import edu.greenblitz.robotname.commands.simple.pneumatics.CompressorOff;
import edu.greenblitz.robotname.commands.simple.pneumatics.CompressorOn;
import edu.greenblitz.utils.hid.CustomControlBoard;
import edu.greenblitz.utils.hid.SmartJoystick;
import org.greenblitz.motion.base.Position;
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

    public static void init () {
        mainJoystick = new SmartJoystick(RobotMap.Joysticks.MAIN);
        sideJoystick = new CustomControlBoard(RobotMap.Joysticks.SIDE);

        mainJoystick.A.whenPressed(new APPCCommand(
                new Path<>(APPCCommand.getPath("Vis Cargoship1.pf1.csv")),
                new Position(-3.0734, 1.5, 0),

                0.5, 0.2, false, 0.3, 0.5, 1));
        mainJoystick.B.whenPressed(new ArcadeDriveByJoystick(mainJoystick));

        mainJoystick.X.whenPressed(new CompressorOn(0));
        mainJoystick.Y.whenPressed(new CompressorOff(0));
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
