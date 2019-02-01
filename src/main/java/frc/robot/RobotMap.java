package frc.robot;

import edu.wpi.first.wpilibj.SerialPort.Port;
import frc.utils.ctre.CANRobotDrive.TalonID;

public class RobotMap {
    public static class Joysticks {
        public static final int Main = 0;
        public static final int Side = 0;
    }

    public static class Chassis {
        public static class Motor {
            public static class Left {
                public static final int Front = 0;
                public static final int Rear = 0;
            }

            public static class Right {
                public static final int Front = 0;
                public static final int Rear = 0;
            }
        }
        
        public static class Sensor {
            public static class Encoder {
                public static final TalonID Left = TalonID.REAR_LEFT;
                public static final TalonID Right = TalonID.REAR_RIGHT;
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
            public static final int Elevator = 0;
        }

        public static class Sensor {
            public static final int TicksPerMeter = 1;
        }

        public static class Solenoid {
            public static final int Forward = 0;
            public static final int Reverse = 0;
        }

        public static class Heights {
            public static final double Ground = 0;
            public static final double Level1 = 0;
            public static final double Level2 = 0;
            public static final double Level3 = 0;
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
            public static final int Backward = 0;
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
}