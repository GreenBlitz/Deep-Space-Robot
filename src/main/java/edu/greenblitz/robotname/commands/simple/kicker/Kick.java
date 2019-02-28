/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;

public class Kick extends TimedSubsystemCommand<Kicker> {
    private static final long KICKER_KICK_TIMEOUT = 1000;

    public Kick() {
        this(KICKER_KICK_TIMEOUT);
    }

    public Kick(long ms) {
        super(ms, Kicker.getInstance());
    }

    @Override
    protected void initialize() {
        system.kick();
    }
}
