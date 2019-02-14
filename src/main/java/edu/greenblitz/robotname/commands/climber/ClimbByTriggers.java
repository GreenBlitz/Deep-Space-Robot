/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.climber;

import edu.wpi.first.wpilibj.command.Command;
import edu.greenblitz.robotname.OI;
import edu.greenblitz.robotname.subsystems.Climber;
import edu.greenblitz.utils.SmartJoystick;

public class ClimbByTriggers extends Command {
    private SmartJoystick m_joystick;

    public ClimbByTriggers(SmartJoystick joystick) {
        requires(Climber.getInstance());
        m_joystick = joystick;
    }

    public ClimbByTriggers() { this(OI.getMainJoystick()); }

    @Override
    protected void execute() {
        Climber.getInstance().setExtender(
                SmartJoystick.Axis.RIGHT_TRIGGER.getValue(m_joystick) -
                SmartJoystick.Axis.LEFT_TRIGGER.getValue(m_joystick));
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}
