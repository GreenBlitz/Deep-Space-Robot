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

        }

        public static class Sensor {

        }
    }
    
    public static class Elevator {
        public static class Motor {

        }

        public static class Sensor {
            
        }
    }

    public static class Intake {
        public static class Motor {

        }

        public static class Sensor {
            
        }
    }

    public static class Kicker {
        public static class Motor {

        }

        public static class Sensor {
            
        }
    }

    public static class Plate {
        public static class Motor {

        }

        public static class Sensor {
            
        }
    }

    public static class Shifter {
        public static class Solenoids {
            public static final int Forward = 0;
            public static final int Reverse = 0;
        }

		public static Object getInstance() {
			return null;
		}
    }

    public static class Spike {
        public static class Motor {

        }

        public static class Sensor {
            
        }
    }
}