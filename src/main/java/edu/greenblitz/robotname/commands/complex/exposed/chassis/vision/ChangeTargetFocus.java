package edu.greenblitz.robotname.commands.complex.exposed.chassis.vision;

import edu.greenblitz.robotname.data.vision.VisionMaster;
import edu.greenblitz.utils.command.base.GBCommand;

public class ChangeTargetFocus extends GBCommand {

    private VisionMaster.Focus m_focus;

    public ChangeTargetFocus(VisionMaster.Focus focus) {
        m_focus = focus;
    }

    @Override
    protected void atInit() {
        VisionMaster.getInstance().setCurrentFocus(m_focus);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
