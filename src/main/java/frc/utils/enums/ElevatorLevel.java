package frc.utils.enums;

import frc.robot.RobotMap.Elevator.Heights;

public enum ElevatorLevel {
    GROUND(Heights.Ground),
    LEVEL1(Heights.Level1),
    LEVEL2(Heights.Level2),
    LEVEL3(Heights.Level3);

    private double m_height;

    private ElevatorLevel(double height) {
        m_height = height;
    }

    public double getHeight() {
        return m_height;
    }
}