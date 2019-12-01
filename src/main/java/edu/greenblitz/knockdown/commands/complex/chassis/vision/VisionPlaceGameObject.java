package edu.greenblitz.knockdown.commands.complex.chassis.vision;

import edu.greenblitz.knockdown.OI;
import edu.greenblitz.knockdown.subsystems.Shifter;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class VisionPlaceGameObject extends ConditionalCommand {

    private Command lastShifterCommand;
    private Shifter.Gear lastGear;

    public VisionPlaceGameObject() {
        super(new VisionPlaceHatchPanel(), new VisionPlaceCargo());
    }

    @Override
    protected void initialize() {
        lastGear = Shifter.getInstance().getCurrentGear();
        lastShifterCommand = Shifter.getInstance().getDefaultCommand();
    }

    @Override
    protected void end() {
        Shifter.getInstance().setDefaultCommand(lastShifterCommand);
        Shifter.getInstance().setShift(lastGear);
    }

    @Override
    protected boolean condition() {
        return OI.getState() == OI.RobotState.HATCH;
    }
}