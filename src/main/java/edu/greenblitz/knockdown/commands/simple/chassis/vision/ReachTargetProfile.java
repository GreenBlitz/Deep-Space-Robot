package edu.greenblitz.knockdown.commands.simple.chassis.vision;

import edu.greenblitz.knockdown.commands.complex.chassis.autonomous.Follow2DProf;
import edu.greenblitz.knockdown.data.vision.VisionMaster;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.command.base.GBCommand;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.State;
import java.util.ArrayList;
import java.util.List;

public class ReachTargetProfile extends GBCommand {

    private State endState;
    private Follow2DProf prof;

    /**e
     * receive data from vision
     * creates and initializes Follow2DProf command
     */
    @Override
    protected void atInit() {
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
        prof = new Follow2DProf(path, .005, 1.2, 6.75, 3.5, 15,
                0.7, 1, .1, 1, .1);
        prof.initialize();
    }

    /**
     * executes follow profile command
     */
    protected void execute() {
        prof.execute();
    }

    /**
     * @return if follow profile command is finished
     */
    @Override
    protected boolean isFinished() {
        return prof.isFinished();
    }
}
