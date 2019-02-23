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
                public static final int TOP = 4;
                public static final int BOTTOM = 2;
                public static final int BACK = 5;
            }

            public static class Right {
                public static final int TOP = 1;
                public static final int BOTTOM = 6;
                public static final int BACK = 3;
            }
        }
        
        public static class Sensor {
            public static class Encoder {
                public static final int TICKS_PER_METER = 1;
            }
            public static final Port NAVX = Port.kMXP;
        }

        public static class Data {
            public static final double WHEEL_BASE_RADIUS = 0;
        }
    }

    public static class Climber {
        public static class Motor {
            public static final int EXTENDER = 0;
            public static final int WHEELS = 0;
            public static final int BIG_0 = 0;
            public static final int BIG_1 = 0;
        }

        public static class Sensor {
            public static final int LIMIT_SWITCH = 0;
        }
    }

    public static class Elevator {
        public static class Motor {
            public static final int MAIN = 0;
            public static final int FOLLOWER = 0;
        }

        public static class Sensor {
            public static final int TICKS_PER_METER = 0;
        }

        public static class Solenoid {
            public static final int FORWARD = 0;
            public static final int REVERSE = 0;
        }

        public static class Heights {
            public static class Cargo {
                public static final double GROUND = 0;
                public static final double CRUISE = 0;
                public static final double SHIP = 0;
                public static final double ROCKET_LOW = 0;
                public static final double ROCKET_MID = 0;
                public static final double ROCKET_HIGH = 0;
                public static final double COLLECT = 0;
            }

            public static class Hatch {
                public static final double GROUND = 0;
                public static final double CRUISE = 0;
                public static final double SHIP = 0;
                public static final double ROCKET_LOW = 0;
                public static final double ROCKET_MID = 0;
                public static final double ROCKET_HIGH = 0;
                public static final double COLLECT = 0;
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
            public static final int INFRARED = 0;
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
            public static final int PICKER = 0;
        }

        public static class Solenoid {
            public static final int FORWARD = 0;
            public static final int REVERSE = 0;
        }

        public static class Sensor {
            public static final int LOW_SWITCH = 0;
            public static final int HIGH_SWITCH = 0;
        }
    }

    public static class Shifter {
        public static class Solenoid {
            public static final int FORWARD = 2;
            public static final int REVERSE = 3;
        }

        public static final int PCM = 0;
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

    public static class Pneumatics {
        public static final int PCM = 0;

        public static class Sensor {
            public static final int PRESSURE = 3;
            public static final int SWITCH = 9;
        }
    }
}