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
                        0.01*3.2, new PIDObject(0),
                        false), Chassis.getInstance()));
        addSequential(new WaitCommand(0.08));
        addSequential(new ThreadedCommand(
                new Follow2DProf(Paths.readGBPath("rocket2part1p2"),
                        .0001, 3.2, 4.5, 8,
                        15.5, // 15.5, 19.1
                        .5, 1, 1,
                        new PIDObject(1/3.2, 0, 200/4.5),
                        0.01*3.2, new PIDObject(0),
                        true), Chassis.getInstance()));
        addSequential(new ThreadedCommand(
                new LiveProfGenerator(new State(1.4, 1, Math.toRadians(179)),
                        .001, 4.0, 5.5, 10,
                        20, // 15.5, 19.1
                        .7, 1, 1,
                        new PIDObject(1.5/4.0, 0.04/5.5, 190/5.5),
                        0.01*4.0, new PIDObject(1.2/10.0, 0.0004, 200/20.0),false), Chassis.getInstance()));


    }

}
