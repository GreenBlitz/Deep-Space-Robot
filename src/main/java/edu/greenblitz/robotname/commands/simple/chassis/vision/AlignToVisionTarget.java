package edu.greenblitz.robotname.commands.simple.chassis.vision;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.chassis.ChassisBaseCommand;
import edu.greenblitz.robotname.data.GearDependentDouble;
import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.robotname.subsystems.Shifter;
import edu.greenblitz.utils.hid.SmartJoystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AlignToVisionTarget extends ChassisBaseCommand implements PIDSource, PIDOutput {

    private static final GearDependentDouble POWER_LIMIT = new GearDependentDouble(Shifter.Gear.SPEED, 0.15);

    private static final GearDependentDouble turnKp = new GearDependentDouble(Shifter.Gear.SPEED, 0.08/25);

    private PIDController m_controller;

    public AlignToVisionTarget(){
        m_controller = new PIDController(0, 0, 0, this, this);
    }

    @Override
    public void atInit() {
        VisionMaster.getInstance().setCurrentAlgorithm(VisionMaster.Algorithm.TARGETS);
        var current = Shifter.getInstance().getCurrentGear();

        var p = turnKp.getByGear(current);
        m_controller.setP(p);
        m_controller.setAbsoluteTolerance(2);
        m_controller.setSetpoint(0);

        m_controller.enable();
    }


    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    public void pidWrite(double output) {
        system.arcadeDrive(
                POWER_LIMIT.getByCurrentGear() * OI.getMainJoystick().getAxisValue(SmartJoystick.Axis.LEFT_Y),
                -output);
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
        return VisionMaster.getInstance().getStandardizedData()[0].getCenterAngle();
    }

    @Override
    protected void atEnd(){
        system.stop();
    }
}