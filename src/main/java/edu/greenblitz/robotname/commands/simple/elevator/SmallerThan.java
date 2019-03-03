package edu.greenblitz.robotname.commands.simple.elevator;

import org.greenblitz.motion.tolerance.ITolerance;

public class SmallerThan implements ITolerance {
    @Override
    public boolean onTarget(double v, double v1) {
        return v1 <= v;
    }
}
