package edu.greenblitz.robotname;

import edu.wpi.first.wpilibj.SerialPort.Port;

public class RobotMap {
    public static class Joysticks {
        public static final int MAIN = 0;
        public static final int SIDE = 1;
    }

    public static class Chassis {
        public static class Motor {
            public static class Left {
                public static final int Front = 1;
                public static final int Middle = 2;
                public static final int Rear = 3;
            }

            public static class Right {
                public static final int Front = 4;
                public static final int Middle = 5;
                public static final int Rear = 6;
            }
        }
        
        public static class Sensor {
            public static class Encoder {
                public static final int TicksPerMeter = 1;
            }
            public static final Port NavX = Port.kMXP;
        }
    }

    public static class Climber {
        public static class Motor {
            public static final int Extender = 6;
            public static final int Wheels = 0;
        }
    }
    
    public static class Elevator {
        public static class Motor {
            public static final int Main = 7;
            public static final int Follower = 0;
        }

        public static class Sensor {
            public static final int Encoder = 0;  
            public static final int TicksPerMeter = 5;
        }

        public static class Solenoid {
            public static final int Forward = 5;
            public static final int Reverse = 4;
        }

        public static class Heights {
            public static final double GROUND = 0;
            public static final double CRUISE = 0;
            public static final double CARGO = 0;
            public static final double ROCKET_LOW = 0;
            public static final double ROCKET_MID = 0;
            public static final double ROCKET_HIGH = 0;
        }

    }

    public static class Roller {
        public static class Motor {
            public static final int Roller = 7;
        }

        public static class Solenoid {
            public static final int Forward = 4;
            public static final int Reverse = 5;
        }

        public static class Sensor {
            public static final int Infrared = 1;
            public static final int LimitSwitch = 9;
        }
    }

    public static class Kicker {
        public static class Solenoid {
            public static final int Forward = 6;
            public static final int Reverse = 7;
        }
    }

    public static class RearPicker {
        public static class Motor {
            public static final int Picker = 7;
        }

        public static class Solenoid {
            public static final int Forward = 6;
            public static final int Reverse = 7;
        }

        public static class Sensor {
            public static final int LowSwitch = 4;
            public static final int HighSwitch = 7;
        }
    }

    public static class Shifter {
        public static class Solenoid {
            public static final int Forward = 0;
            public static final int Reverse = 1;
        }
    }

    public static class FrontPoker {
        public static class Solenoid {
            public static class Kicker {
                public static final int Forward = 1;
                public static final int Reverse = 2;
            }

            public static class Extender {
                public static final int Forward = 6;
                public static final int Reverse = 7;
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