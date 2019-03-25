package edu.greenblitz.robotname.data;

import org.greenblitz.motion.base.Position;

public class VisionTargetLocations {
    public static class Rocket {
        public static class Left {
            public static final Position LEFT = new Position(0, 0);
            public static final Position FRONT = new Position(0, 0);
            public static final Position RIGHT = new Position(0, 0);
        }

        public static class Right {
            public static final Position LEFT = new Position(0, 0);
            public static final Position FRONT = new Position(0, 0);
            public static final Position RIGHT = new Position(0, 0);
        }
    }

    public static class Cargoship {
        public static class Left {
            public static final Position FRONT = new Position(0, 0);
            public static final Position SECTION1 = new Position(0, 0);
            public static final Position SECTION2 = new Position(0, 0);
            public static final Position SECTION3 = new Position(0, 0);
        }

        public static class Right {
            public static final Position FRONT = new Position(0, 0);
            public static final Position SECTION1 = new Position(0, 0);
            public static final Position SECTION2 = new Position(0, 0);
            public static final Position SECTION3 = new Position(0, 0);
        }
    }

    public static class Feeder {
        public static final Position LEFT = new Position(0, 0);
        public static final Position REFT = new Position(0, 0);
    }
}
