package edu.greenblitz.robotname.commands.simple.elevator;

import org.greenblitz.motion.tolerance.ITolerance;

public class BiggerThan implements ITolerance {
    @Override
    public boolean onTarget(double goal, double currentValue) {
        return goal >= currentValue;
    }
}
