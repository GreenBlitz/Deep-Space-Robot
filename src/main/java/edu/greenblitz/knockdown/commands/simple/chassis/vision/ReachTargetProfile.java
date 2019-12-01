package edu.greenblitz.knockdown.commands.simple.chassis.vision;

import edu.greenblitz.knockdown.commands.complex.chassis.autonomous.Follow2DProf;
import edu.greenblitz.knockdown.data.vision.StandardVisionData;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.command.base.GBCommand;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.profiling.MotionProfile1D;
import org.greenblitz.motion.profiling.MotionProfile2D;
import org.greenblitz.motion.profiling.followers.PidFollower2D;

import java.util.ArrayList;
import java.util.List;

public class ReachTargetProfile extends GBCommand {

    private State endState;
    private Follow2DProf cmd;

    //PidFollower2D follower;
    //MotionProfile2D profile;
    //MotionProfile1D l = new MotionProfile1D(), a = new MotionProfile1D();

    @Override
    protected void atInit() {
        //VisionMaster.getInstance().setCurrentAlgorithm(VisionMaster.Algorithm.TARGETS);
        //StandardVisionData d = VisionMaster.getInstance().getStandardizedData()[0];
        //profile = new MotionProfile2D(l, a);
        //follower = new PidFollower2D();
        double[] difference = VisionMaster.getInstance().getCurrentVisionData();
        double targetX = difference[0];
        double targetY = difference[2];
        double targetAngle = difference[3];
        endState = new State(Chassis.getInstance().getLocation().translate
                (new Point(targetX, targetY).rotate(-Chassis.getInstance().getAngle())),
                Chassis.getInstance().getAngle() + targetAngle,0,0);
        List<State> path = new ArrayList<State>();
        path.add(new State (Chassis.getInstance().getLocation(),Chassis.getInstance().getAngle(),Chassis.getInstance().getVelocity(),Chassis.getInstance().getNavx().getVelocityY()));
        path.add(endState);
        cmd = new Follow2DProf(path, .005, 1.2, 6.75, 3.5, 15,
                0.7, 1, .1, 1, .1);
        cmd.initialize();
    }

    protected void execute() {
        cmd.execute();
    }

    @Override
    protected boolean isFinished() {
        return cmd.isFinished();
    }
}
