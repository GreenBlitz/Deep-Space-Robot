package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.commands.simple.shifter.ToSpeed;
import edu.greenblitz.robotname.subsystems.Shifter;
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
        return OI.getState() == OI.State.HATCH;
    }
}