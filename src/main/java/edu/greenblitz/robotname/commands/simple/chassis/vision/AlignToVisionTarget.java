package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Chassis;
import edu.greenblitz.utils.command.SubsystemCommand;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AlignToVisionTarget extends SubsystemCommand<Chassis> implements PIDSource, PIDOutput {

    private static final double FULL_POWER = 0;
    private static final long TIME_ON_TARGET = 0;
    private long m_onTarget = -1;
    private static final double kP = 0, kI = 0, kD = 0;

    private PIDController m_controller;

    public AlignToVisionTarget(){
        super(Chassis.getInstance());
        m_controller = new PIDController(kP, kI, kD, this, this);
    }

    @Override
    public void initialize() {
        VisionMaster.getInstance().setCurrentAlgorithm(VisionMaster.Algorithm.TARGETS);
        m_controller.setAbsoluteTolerance(0);
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
        system.tankDrive(-output, output);
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
        system.stop();
    }

    @Override
    public double pidGet() {
        return VisionMaster.getInstance().getStandardizedData().getCenterAngle();
    }
}