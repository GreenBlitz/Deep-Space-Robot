package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;

public class CheckMaxRot extends Command {

    private double power;
    private double previousAngle;
    private double previousVel;
    private double previousTime;
    private long tStart;
    private RemoteCSVTarget target;
    int count;

    public CheckMaxRot(double power) {
        requires(Chassis.getInstance());
        this.power = power;
    }

    @Override
    public void initialize() {
        previousTime = System.currentTimeMillis() / 1000.0;
        previousAngle = Chassis.getInstance().getLocation().getAngle();
        previousVel = 0;
        count = 0;
        tStart = System.currentTimeMillis();
        target = RemoteCSVTarget.initTarget("RotationalData", "time", "vel", "acc");
    }

    @Override
    protected void execute() {
        count++;

        while (System.currentTimeMillis() - tStart < 5000) {
            Chassis.getInstance().tankDrive(-power, power);

            double time = System.currentTimeMillis() / 1000.0;
            double angle = Math.toRadians(Chassis.getInstance().getNavx().getAngle());
            double V = (angle - previousAngle) / (time - previousTime);
            target.report(time - tStart, V, (V - previousVel) / (time - previousTime));
            previousAngle = angle;
            previousTime = time;
            previousVel = V;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void end(){
        System.out.println(System.currentTimeMillis() - tStart);
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() - tStart > 5000;
    }
}
