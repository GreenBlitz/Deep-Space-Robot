package edu.greenblitz.knockdown.commands.complex.chassis.vision;

import edu.greenblitz.knockdown.data.vision.VisionMaster;
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
