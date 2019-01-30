package frc.utils;

import frc.motion.base.Point;

/**
 * Field Coordinents in meters.
 * <p> 
 * When alliance specific object are referenced, 
 * only the North-Western quarter of the field is specified, 
 * with the North-West most point of the field is considered as (0, 0). 
 * <p>
 * To recieve the South-Western quarter, simply subtract the field's width from all coordinents. 
 * <p>
 * There is no need for these coordinents for the East side of the field, 
 * since it is mirrored and flipped vertically. Thus rendering it exactly the same.
 */
public class Field {
    public static final double Length = 16.4592;
    public static final double Width = 8.2296;

    public static class Habitat {
        public static final double Line = 2.427;
        
        public static class Level2 {
            public static final Point Coords = new Point(0, 0);
            public static final double Width = 0;
            public static final double Length = 0;
        }

        public static class Level3 {
            public static final Point Coords = new Point(0, 0);
            public static final double Width = 0;
            public static final double Length = 0;
        }
    }

    public static class Cargoship {
        public static final Point Front = new Point(0, 0);
        public static final Point Section1 = new Point(0, 0);
        public static final Point Section2 = new Point(0, 0);
        public static final Point Section3 = new Point(0, 0);
    }

    public static class Rocket {
        public static final Point South = new Point(0, 0);
        public static final Point West = new Point(0, 0);
        public static final Point East = new Point(0, 0);
    }

    public static class Depot {
        public static final Point Coords = new Point(0, 0);
        public static final double Width = 0;
        public static final double Length = 0;
    }

    public static final Point Feeder = new Point(0, 0);
}