package edu.greenblitz.robotname.commands.chassis;

import edu.greenblitz.robotname.subsystems.Chassis;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnToAngle extends Command implements PIDSource, PIDOutput {

	private static double kP = 0.8, kI = 0, kD = 0;
	private PIDController mController;
	private int mOnTarget = 0;
	private double targetAngle;
	private static final double MINIMUM_OUTPUT = 0.35;
	
  public TurnToAngle(double angle) {
    requires(Chassis.getInstance());
    mController = new PIDController(kP, kI, kD, this, this);
    targetAngle = angle;
	}

  protected void initialize() {
    Chassis.getInstance().resetNavx();
    mController.setAbsoluteTolerance(5/90.0);
    mController.setSetpoint(targetAngle/90.0);
    mController.setOutputRange(-0.8, 0.8);
    mController.enable();
  }

  protected boolean isFinished() {
		if (mController.onTarget())
			mOnTarget++;
		else
			mOnTarget = 0;
		return mOnTarget >= 4;
  }

  protected void end() {
    mController.disable();
    Chassis.getInstance().stop();
  }
    
  @Override
  protected void execute() {
    SmartDashboard.putNumber("Target setPoint", mController.getSetpoint());
    SmartDashboard.putNumber("Current setPoint", pidGet());
    SmartDashboard.putBoolean("is on Target?", mController.onTarget());
    SmartDashboard.putNumber("time on Target", mOnTarget);
  }

	@Override
	public void pidWrite(double output) {
		if (Math.abs(output) < MINIMUM_OUTPUT) 
			output = Math.signum(output) * MINIMUM_OUTPUT;
		SmartDashboard.putNumber("PID Output", output);
		Chassis.getInstance().arcadeDrive(0, output);
	}

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return Chassis.getInstance().getAngle()/90.0;
	}
}