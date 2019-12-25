package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.gblib.threading.ThreadedCommand;
import edu.greenblitz.knockdown.commands.simple.chassis.ResetNavx;
import edu.greenblitz.knockdown.commands.simple.chassis.SimpleTurnToAngle;
import edu.greenblitz.knockdown.commands.simple.shifter.ToPower;
import edu.greenblitz.knockdown.commands.simple.shifter.ToSpeed;
import edu.greenblitz.knockdown.data.Paths;
import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.greenblitz.utils.command.CommandChain;
import edu.wpi.first.wpilibj.command.WaitCommand;
import org.greenblitz.motion.base.State;
import org.greenblitz.motion.pid.PIDObject;
import org.greenblitz.motion.profiling.ActuatorLocation;

import java.util.ArrayList;
import java.util.List;

public class RocketTwoDisks extends CommandChain {

    public RocketTwoDisks(){
        addSequential(new ThreadedCommand(
                new Follow2DProf(Paths.readGBPath("rocket2part1"),
                        .0001, 3.2, 4.5, 8,
                        15.5, // 15.5, 19.1
                        .5, 1, 1,
                        new PIDObject(1.5/3.2, 0.02/4.5, 170/4.5),
                        0.01*3.2, false), Chassis.getInstance()));
        addSequential(new ToPower());
        addSequential(new SimpleTurnToAngle(-155, 0.5, true,10));
        addSequential(new ToSpeed());
        addSequential(new WaitCommand(0.05));
        addSequential(new ThreadedCommand(
                new LiveProfGenerator(new State(0.5, 0.8, Math.PI),
                        .001, 4, 5.5, 10,
                        15.5, // 15.5, 19.1
                        .7, 1, 1,
                        new PIDObject(5/4.0, 0.03/5.5, 140/5.5),
                        0.01*4, false), Chassis.getInstance()));
    }

}
