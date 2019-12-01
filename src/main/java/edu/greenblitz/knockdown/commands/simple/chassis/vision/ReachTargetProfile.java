package edu.greenblitz.knockdown.commands.simple.chassis.vision;

import edu.greenblitz.knockdown.data.vision.StandardVisionData;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.utils.command.base.GBCommand;
import org.greenblitz.motion.profiling.MotionProfile1D;
import org.greenblitz.motion.profiling.MotionProfile2D;
import org.greenblitz.motion.profiling.followers.PidFollower2D;

public class ReachTargetProfile extends GBCommand {

    PidFollower2D follower;
    MotionProfile2D profile;
    MotionProfile1D l = new MotionProfile1D(), a = new MotionProfile1D();

    @Override
    protected void atInit() {
        VisionMaster.getInstance().setCurrentAlgorithm(VisionMaster.Algorithm.TARGETS);
        StandardVisionData d = VisionMaster.getInstance().getStandardizedData()[0];
        profile = new MotionProfile2D(l, a);
        follower = new PidFollower2D();

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
