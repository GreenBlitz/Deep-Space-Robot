package edu.greenblitz.knockdown.commands.complex.chassis.autonomous;

import edu.greenblitz.knockdown.subsystems.Chassis;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CheckMaxLin extends Command {

    private double power;
    private double previousLoc;
    private double previousVel;
    private double previousTime;
    private double maxV = 0;
    private long tStart;
    int count;

    public CheckMaxLin(double power){
        requires(Chassis.getInstance());
        this.power = power;
    }

    @Override
    public void initialize(){
        previousTime = System.currentTimeMillis() / 1000.0;
        previousLoc = Chassis.getInstance().getDistance();
        previousVel = 0;
        count = 0;
        tStart = System.currentTimeMillis();
    }

    @Override
    protected void execute() {
        count++;
        Chassis.getInstance().tankDrive(power, power);

        if (count % 5 == 0) {
            double time = System.currentTimeMillis() / 1000.0;
            double dist = Chassis.getInstance().getDistance();
            double V = Math.abs(dist - previousLoc) / (time - previousTime);
            SmartDashboard.putNumber("VEL LIN", V);
            SmartDashboard.putNumber("ACC LIN", (V - previousVel) / (time - previousTime));
            SmartDashboard.putNumber("Acc Navx Y",Chassis.getInstance().getNavx().getWorldLinearAccelY());
            SmartDashboard.putNumber("Acc Navx X",Chassis.getInstance().getNavx().getWorldLinearAccelX());
            SmartDashboard.putNumber("Acc Navx Z",Chassis.getInstance().getNavx().getWorldLinearAccelZ());

            previousTime = time;
            previousLoc = dist;
            previousVel = V;
        }
    }

    @Override
    protected boolean isFinished() {
       return System.currentTimeMillis() - tStart > 3000;
    }
}
