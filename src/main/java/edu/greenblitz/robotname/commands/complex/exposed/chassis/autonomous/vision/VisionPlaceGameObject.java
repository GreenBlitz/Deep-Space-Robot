package edu.greenblitz.robotname.commands.complex.exposed.chassis.autonomous.vision;

import edu.greenblitz.robotname.OI;
import edu.wpi.first.wpilibj.command.ConditionalCommand;

public class VisionPlaceGameObject extends ConditionalCommand {

    public VisionPlaceGameObject() {
        super(new VisionPlaceHatchPanel(), new VisionPlaceCargo());
    }

    @Override
    protected boolean condition() {
        return OI.getGameObject() == OI.GameObject.HATCH;
    }
}