package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.greenblitz.debug.RemoteCSVTarget;

public class CheckMaxLin extends Command {

    private double power;
    private double previousLoc;
    private double previousVel;
    private double previousTime;
    private double maxV = 0;
    private RemoteCSVTarget target;
    private long tStart;
    int count;

    public CheckMaxLin(double power) {
        requires(Chassis.getInstance());
        this.power = power;
    }

    @Override
    public void initialize() {
        previousTime = System.currentTimeMillis() / 1000.0;
        previousLoc = Chassis.getInstance().getDistance();
        previousVel = 0;
        count = 0;
        tStart = System.currentTimeMillis();
        target = RemoteCSVTarget.initTarget("LinearData", "time", "vel", "acc");
    }

    private final double G = 9.806;

    @Override
    protected void execute() {
        count++;

        while (System.currentTimeMillis() - tStart < 5000) {
            Chassis.getInstance().tankDrive(power, power);

            double time = System.currentTimeMillis() / 1000.0;
            double dist = Chassis.getInstance().getVelocity();
            double V = Chassis.getInstance().getVelocity();
            target.report(time - tStart, V, (V - previousVel) / (time - previousTime));
            previousTime = time;
            previousLoc = dist;
            previousVel = V;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected boolean isFinished() {
        return System.currentTimeMillis() - tStart > 3000;
    }
}
