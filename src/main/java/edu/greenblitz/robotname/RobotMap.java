package edu.greenblitz.robotname;

import edu.greenblitz.robotname.utils.drive.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class RobotMap {
    public static class Joysticks {
        public static final int MAIN = 0;
        public static final int SIDE = 0;
    }

    public static class Chassis {
        public static class Motor {
            public static class Left {
                public static final int FRONT = 0;
                public static final int REAR = 0;
            }

            public static class Right {
                public static final int FRONT = 0;
                public static final int REAR = 0;
            }
        }
        
        public static class Sensor {
            public static class Encoder {
                public static final RobotDrive.MotorID LEFT = RobotDrive.MotorID.FRONT_LEFT;
                public static final RobotDrive.MotorID RIGHT = RobotDrive.MotorID.FRONT_RIGHT;
                public static final int TICKS_PER_METER = 1;
            }
            public static final Port NavX = Port.kMXP;
        }
    }

    public static class Climber {
        public static class Motor {
            public static final int EXTENDER = 0;
            public static final int WHEELS = 0;
        }
    }
    
    public static class Elevator {
        public static class Motor {
            public static final int ELEVATOR = 0;
        }

        public static class Sensor {
            public static final int TICKS_PER_METER = 1;
        }

        public static class Solenoid {
            public static final int FORWARD = 0;
            public static final int REVERSE = 0;
        }

        public static class Heights {
            public static final double GROUND = 0;
            public static final double LEVEL1 = 0;
            public static final double LEVEL2 = 0;
            public static final double LEVEL3 = 0;
        }

        public enum ElevatorLevel {
            GROUND(Heights.GROUND),
            LEVEL1(Heights.LEVEL1),
            LEVEL2(Heights.LEVEL2),
            LEVEL3(Heights.LEVEL3);

            private double m_height;

            ElevatorLevel(double height) {
                m_height = height;
            }

            public double getHeight() {
                return m_height;
            }
        }
    }

    public static class Roller {
        public static class Motor {
            public static final int ROLLER = 0;
        }

        public static class Solenoid {
            public static final int FORWARD = 0;
            public static final int REVERSE = 0;
        }

        public static class Sensor {
            public static final int IR = 0;
            public static final int LIMIT_SWITCH = 0;
        }
    }

    public static class Kicker {
        public static class Solenoid {
            public static final int FORWARD = 0;
            public static final int REVERSE = 0;
        }
    }

    public static class RearPicker {
        public static class Motor {

        }

        public static class Solenoid {
            public static final int FORWARD = 0;
            public static final int Backward = 0;
        }

        public static class Sensor {
            
        }
    }

    public static class Shifter {
        public static class Solenoid {
            public static final int FORWARD = 0;
            public static final int REVERSE = 0;
        }
    }

    public static class FrontPoker {
        public static class Solenoid {
            public static class Kicker {
                public static final int FORWARD = 0;
                public static final int REVERSE = 0;
            }

            public static class Extender {
                public static final int FORWARD = 0;
                public static final int REVERSE = 0;
            }
        }
    }
}