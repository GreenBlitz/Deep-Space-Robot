/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.robotname.commands.simple.kicker;

import edu.greenblitz.robotname.data.sm.KickerState;

public class Unkick extends KickerBaseCommand {

    private static final long KICKER_CLOSE_TIMEOUT = 1000;

    public Unkick(long ms) {
        super(ms);
    }

    @Override
    protected KickerState getNextState() {
        return KickerState.UNKICK;
    }

    public Unkick() {
        this(KICKER_CLOSE_TIMEOUT);
    }

    @Override
    protected void initialize() {
        system.unkick();
    }
}
