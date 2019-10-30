package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CheckMaxRot extends Command {

    private double power;
    private double previousAngle;
    private double previousVel;
    private double previousTime;
    private double maxV = 0;
    private long tStart;
    int count;

    public CheckMaxRot(double power){
        this.power = power;
    }

    @Override
    public void initialize(){
        previousTime = System.currentTimeMillis() / 1000.0;
        previousAngle = Chassis.getInstance().getLocation().getAngle();
        previousVel = 0;
        count = 0;
        tStart = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        count++;
        Chassis.getInstance().tankDrive(-power, power);

        if (count % 5 == 0) {
            double time = System.currentTimeMillis() / 1000.0;
            double angle = Math.toRadians(Chassis.getInstance().getNavx().getAngle());
            double V = Math.abs(angle - previousAngle) / (time - previousTime);
            maxV = Math.max(maxV, V);
            SmartDashboard.putNumber("VEL", V);
            SmartDashboard.putNumber("ACC", (V - previousVel) / (time - previousTime));
            previousTime = time;
            previousAngle = angle;
            previousVel = V;
        }
    }

    @Override
    protected boolean isFinished() {
       return System.currentTimeMillis() - tStart > 5000;
    }
}
