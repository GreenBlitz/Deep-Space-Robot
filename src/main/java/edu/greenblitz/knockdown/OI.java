package edu.greenblitz.knockdown;

import edu.greenblitz.gblib.threading.ThreadedCommand;
import edu.greenblitz.knockdown.commands.complex.RollOrAlign;
import edu.greenblitz.knockdown.commands.complex.chassis.autonomous.*;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.ChangeTargetFocus;
import edu.greenblitz.knockdown.commands.complex.chassis.vision.VisionPlaceGameObject;
import edu.greenblitz.knockdown.commands.complex.climber.ClimbByJoystick;
import edu.greenblitz.knockdown.commands.complex.climber.StopClimbing;
import edu.greenblitz.knockdown.commands.complex.elevator.SafeMoveElevator;
import edu.greenblitz.knockdown.commands.complex.kicker.KickBall;
import edu.greenblitz.knockdown.commands.complex.roller.SmartExtendAndRollIn;
import edu.greenblitz.knockdown.commands.complex.roller.ToggleRoller;
import edu.greenblitz.knockdown.commands.simple.chassis.SimpleTurnToAngle;
import edu.greenblitz.knockdown.commands.simple.chassis.driver.ArcadeDriveByJoystick;
import edu.greenblitz.knockdown.commands.simple.poker.HoldHatch;
import edu.greenblitz.knockdown.commands.simple.poker.ReleaseHatch;
import edu.greenblitz.knockdown.commands.simple.poker.TogglePokerExtender;
import edu.greenblitz.knockdown.commands.simple.roller.RetractAndStopRoller;
import edu.greenblitz.knockdown.commands.simple.shifter.AutoChangeShift;
import edu.greenblitz.knockdown.commands.simple.shifter.ToggleShift;
import edu.greenblitz.knockdown.data.Paths;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.knockdown.subsystems.Elevator;
import edu.greenblitz.utils.command.ResetCommands;
import edu.greenblitz.utils.command.base.GBCommand;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.profiling.ActuatorLocation;

import java.util.ArrayList;
import java.util.List;

public class OI {
    public enum RobotState {
        CARGO,
        HATCH
    }

    private static SmartJoystick mainJoystick;
    private static SmartJoystick sideJoystick;

    public static RobotState getOiState() {
        return oiState;
    }

    private static RobotState oiState = RobotState.HATCH;

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
            setOIState(RobotState.HATCH);
        }
    }

    public static class ToCargoMode extends GBCommand {

        @Override
        protected boolean isFinished() {
            return true;
        }

        @Override
        protected void atInit() {
            setOIState(RobotState.CARGO);
        }
    }

    public static void initJoysticks() {
        if (mainJoystick == null) mainJoystick = new SmartJoystick(new Joystick(RobotMap.Joysticks.MAIN), 0.07);
        if (sideJoystick == null) sideJoystick = new SmartJoystick(RobotMap.Joysticks.SIDE);
    }

    public static void initBindings() {
//        initOfficialBindings(); // For real game shit
        initTestBindings(); // For testing code
    }

    private static void initTestBindings() {
        mainJoystick.A.whenPressed(new CheckMaxRot(.7));
        mainJoystick.Y.whenPressed(new CheckMaxLin(.7));
        //mainJoystick.B.whenPressed(new ArcadeDriveByJoystick(mainJoystick));
        ArrayList<ActuatorLocation> angle = new ArrayList<>();
        angle.add(new ActuatorLocation(0,0));
        angle.add(new ActuatorLocation(2*Math.PI,0));
        mainJoystick.B.whenPressed(new ThreadedCommand(
                new LiveProfGenerator(new State(0, 0, Math.PI),
                        .001, 4, 5.5, 10,
                        20, // 15.5, 19.1
                        .7, 1, 1,
                        new PIDObject(5/4.0, 0.03/5.5, 170/5.5),
                        0.01*4, false), Chassis.getInstance()));
        ArrayList<State> pth = new ArrayList<>();

        pth.add(new State(0, 0, 0, 0, 0));
        pth.add(new State(0, 3, 0, 0, 0));
//        pth.add(new State(-1.5, 4,-Math.PI/2, 0, 0));

//        pth.add(new State(.8, 2, 0, 0, 0));
        // Max .4 rot = 2.1, 10
        // Max .4 lin = 0.7, 4.6

        // Max .7 rot = 4, 15
        // Max .7 lin = 1.25, 10

        // Max 1 rot = 5.5, 15
        // Max 1 lin = 1.75, 11.25

        // Max 1 rot carpet = 5.25, 16
        // Max 1 lin carpet = 1.75, 7.5

        // Max .5 speed rot carpet = 8, 15.5
        // Max .5 speed lin carpet = 3.2, 4.5

        // Max .7 speed rot carpet = 10, 14
        // Max .7 speed lin carpet = 4, 5.5

        mainJoystick.X.whenPressed(new RocketTwoDisks());
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

    public static RobotState getState() {
        return oiState;
    }

    public static boolean isStateCargo() {
        return getState() == RobotState.CARGO;
    }

    public static boolean isStateHatch() {
        return getState() == RobotState.HATCH;
    }

    public static void setOIState(RobotState state) {
        VisionMaster.getInstance().reportOIMode(state);
        oiState = state;
    }

    public static void update() {
        SmartDashboard.putString("Mode", oiState.toString());
    }
}