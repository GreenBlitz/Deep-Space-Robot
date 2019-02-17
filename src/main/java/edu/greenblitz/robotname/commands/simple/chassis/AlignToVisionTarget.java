package edu.greenblitz.robotname.commands.simple.chassis;

import edu.greenblitz.robotname.VisionPort;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;

public class AlignToVisionTarget extends Command implements PIDSource, PIDOutput {

    private static final double FULL_POWER = 0.5;
    private static final long TIME_ON_TARGET = 200;
    private long m_onTarget = -1;
    private static final double kP = 0.045, kI = 0, kD = 0.0001;

    private PIDController m_controller;

    public AlignToVisionTarget(){
        requires(Chassis.getInstance());
        m_controller = new PIDController(kP, kI, kD, this, this);
    }

    @Override
    public void initialize() {
        System.out.println("initializing AlignToVisionTarget");
        m_controller.setAbsoluteTolerance(3);
        m_controller.setSetpoint(0);
        m_controller.setOutputRange(-FULL_POWER, FULL_POWER);
        m_controller.enable();
    }

    @Override
    protected void execute() {
      if (m_controller.onTarget())
            if (m_onTarget == -1)
                m_onTarget = System.currentTimeMillis();
            else
                m_onTarget = -1;
    }

    @Override
    protected boolean isFinished() {
      return m_controller.onTarget() && System.currentTimeMillis() - m_onTarget > TIME_ON_TARGET;
    }

    @Override
    public void pidWrite(double output) {
        System.out.println("tank drive by param " + output);
        Chassis.getInstance().tankDrive(-output, -output);
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {}

    @Override
    public PIDSourceType getPIDSourceType() {
        return PIDSourceType.kDisplacement;
    }

    @Override
    public void end(){
        m_controller.disable();
        Chassis.getInstance().stop();
    }

    @Override
    public double pidGet() {
        System.out.println("returning pidGet: "+ VisionPort.getInstance().getHatchAngle());
        return VisionPort.getInstance().getHatchAngle();
    }
}