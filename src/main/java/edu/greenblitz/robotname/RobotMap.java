package edu.greenblitz.robotname;

import edu.greenblitz.utils.drive.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort.Port;

public class RobotMap {
    public static class Joysticks {
        public static final int Main = 0;
        public static final int Side = 0;
    }

    public static class Chassis {
        public static class Motor {
            public static class Left {
                public static final int Front = 0;
                public static final int Middle = 0;
                public static final int Rear = 0;
            }

            public static class Right {
                public static final int Front = 0;
                public static final int Middle = 0;
                public static final int Rear = 0;
            }
        }
        
        public static class Sensor {
            public static class Encoder {
                public static final RobotDrive.MotorID Left = RobotDrive.MotorID.FRONT_LEFT;
                public static final RobotDrive.MotorID Right = RobotDrive.MotorID.FRONT_RIGHT;
                public static final int TicksPerMeter = 1;
            }
            public static final Port NavX = Port.kMXP;
        }
    }

    public static class Climber {
        public static class Motor {
            public static final int Extender = 0;
            public static final int Wheels = 0;
        }
    }
    
    public static class Elevator {
        public static class Motor {
            public static final int Main = 0;
            public static final int Follower = 0;
        }

        public static class Sensor {
            public static final int Encoder = 0;  
            public static final int TicksPerMeter = 1;
        }

        public static class Solenoid {
            public static final int Forward = 0;
            public static final int Reverse = 0;
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
            public static final int Roller = 0;
        }

        public static class Solenoid {
            public static final int Forward = 0;
            public static final int Reverse = 0;
        }

        public static class Sensor {
            public static final int Infrared = 0;
            public static final int LimitSwitch = 0;
        }
    }

    public static class Kicker {
        public static class Solenoid {
            public static final int Forward = 0;
            public static final int Reverse = 0;
        }
    }

    public static class RearPicker {
        public static class Motor {

        }

        public static class Solenoid {
            public static final int Forward = 0;
            public static final int Reverse = 0;
        }

        public static class Sensor {
            
        }
    }

    public static class Shifter {
        public static class Solenoid {
            public static final int Forward = 0;
            public static final int Reverse = 0;
        }
    }

    public static class FrontPoker {
        public static class Solenoid {
            public static class Kicker {
                public static final int Forward = 0;
                public static final int Reverse = 0;
            }

            public static class Extender {
                public static final int Forward = 0;
                public static final int Reverse = 0;
            }
        }
    }

    public static class Pneumatics {
        public static class Sensor {
            public static final int Pressure = 0;
            public static final int Switch = 0;
        }

        public static class PCM {
            public static final int Compressor = 0;
        }
    }
}