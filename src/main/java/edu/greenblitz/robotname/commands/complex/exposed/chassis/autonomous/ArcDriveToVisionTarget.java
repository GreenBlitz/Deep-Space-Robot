package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous;

import edu.greenblitz.robotname.commands.simple.chassis.TurnToAngle;
import edu.greenblitz.robotname.commands.simple.chassis.vision.AlignToVisionTarget;
import edu.greenblitz.robotname.commands.simple.chassis.vision.ArcDriveToTarget;
import edu.greenblitz.robotname.commands.simple.chassis.vision.ArcPidDriveToVisionTarget;
import edu.greenblitz.robotname.data.vision.StandardVisionData;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.chain.CommandChain;

public class ArcDriveToVisionTarget extends CommandChain {
    @Override
    protected void initChain() {
        StandardVisionData target = VisionMaster.getInstance().getStandardizedData();
        double targetAngle = Chassis.getInstance().getAngle() + 2*target.getCenterAngle() + target.getRelativeAngle();
//        addSequential(new TurnToAngle(targetAngle));
//        addSequential(new ArcDriveToTarget(target));
        addSequential(new ArcPidDriveToVisionTarget(target));
    }
}
