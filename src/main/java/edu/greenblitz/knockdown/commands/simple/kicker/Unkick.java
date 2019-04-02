/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.greenblitz.knockdown.commands.simple.kicker;

import static edu.greenblitz.knockdown.subsystems.Kicker.KICKER_STATE_TIMEOUT;

public class Unkick extends KickerBaseCommand {

    public Unkick(long ms) {
        super(ms);
    }

    public Unkick() {
        this(KICKER_STATE_TIMEOUT);
    }

    @Override
    protected void atInit() {
        system.unkick();
    }
}
