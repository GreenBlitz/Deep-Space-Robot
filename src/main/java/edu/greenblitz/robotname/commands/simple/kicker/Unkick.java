/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.subsystems.Kicker;
import edu.greenblitz.utils.command.TimedSubsystemCommand;
import edu.greenblitz.utils.sm.KickerState;
import edu.greenblitz.utils.sm.State;

public class Unkick extends TimedSubsystemCommand<Kicker> {

    private static final long KICKER_CLOSE_TIMEOUT = 1000;

    public Unkick(long ms) {
        super(ms, Kicker.getInstance());
    }

    @Override
    public State getDeltaState() {
        return new State(null, null, null, KickerState.UNKICK);
    }

    public Unkick() {
        this(KICKER_CLOSE_TIMEOUT);
    }

    @Override
    protected void initialize() {
        system.unkick();
    }
}
