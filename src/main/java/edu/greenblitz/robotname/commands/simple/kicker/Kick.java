/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.robotname.subsystems.Poker;
import edu.greenblitz.robotname.subsystems.Roller;
import edu.greenblitz.robotname.subsystems.TimedSubsystem;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Kick extends TimedSubsystemCommand<Kicker> {
    private static final long KICKER_KICK_TIMEOUT = 1000;

    public Kick() {
        this(KICKER_KICK_TIMEOUT);
    }

    @Override
    protected TimedSubsystem[] requirements() {
        return new TimedSubsystem[]{Poker.getInstance(), Roller.getInstance()};
    }

    public Kick(long ms) {
        super(Kicker.getInstance(), ms);
    }

    @Override
    protected void timedInitialize() {
        system.kick();
    }
}
