package edu.greenblitz.knockdown;

import edu.wpi.first.wpilibj.SerialPort.Port;

public class RobotMap {
    public static class Joysticks {
        public static final int MAIN = 0;
        public static final int SIDE = 1;
    }

    public static class Chassis {
        public static class Motor {
            public static class Left {
                public static final int LEADER = 4;
                public static final int FOLLOWER1 = 5;
                public static final int FOLLOWER2 = 6;
            }

            public static class Right {
                public static final int LEADER = 1;
                public static final int FOLLOWER1 = 2;
                public static final int FOLLOWER2 = 3;
            }
        }
        
        public static class Sensor {
            public static class Encoder {
                public static final double TICKS_PER_METER_POWER = 2227;// 3442; // mistaken value before 11.3: 2295
                public static final double TICKS_PER_METER_SPEED = 600;//934; // mistaken value before 11.3: 633.5
            }
            public static final Port NAVX = Port.kUSB;
        }

        public static class Data {
            public static final double WHEEL_BASE_RADIUS = 0.595;
        }
    }

    public static class Climber {
        public static class Motor {
            public static final int EXTENDER = 7;
            public static final int WHEELS = 15;
            public static final int BIG = 14;
        }

        public static class Sensor {
            public static final int LIMIT_SWITCH = 8;
        }
    }

    public static class Elevator {
        public static class Motor {
            public static final int LEADER = 11;
            public static final int FOLLOWER = 12;
        }

        public static class Sensor {
            public static final double TICKS_PER_METER = 424400 * (1.48 / 1.07) / 1.11;
            public static final int LIMIT_SWITCH = 9;
        }

        public static class Solenoid {
            public static final int FORWARD = 0;
            public static final int REVERSE = 5;
            public static final int PCM = 21;
        }
    }

    public static class Roller {
        public static class Motor {
            public static final int ROLLER = 13;
        }

        public static class Solenoid {
            public static final int FORWARD = 5;
            public static final int REVERSE = 7;
            public static final int PCM = 22;
        }
    }

    public static class Kicker {
        public static class Solenoid {
            public static final int FORWARD = 2;
            public static final int REVERSE = 4;
            public static final int PCM = 21;
        }
    }

    public static class Shifter {
        public static class Solenoid {
            public static final int FORWARD = 3;
            public static final int REVERSE = 6;
        }

        public static final int PCM = 21;
    }

    public static class Poker {
        public static class Solenoid {
            public static class Holder {
                public static final int FORWARD = 4;
                public static final int REVERSE = 6;
                public static final int PCM = 22;
            }

            public static class Extender {
                public static final int FORWARD = 7;
                public static final int REVERSE = 1;
                public static final int PCM = 21;
            }
        }
    }

    public static class Pneumatics {
        public static final int PCM = 21;

        public static class Sensor {
            public static final int PRESSURE = 3;
        }
    }
}