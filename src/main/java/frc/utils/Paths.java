package frc.utils;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import frc.motion.base.Path;
import frc.motion.base.Point;

public class Paths {
    public static class Left {
        public static final Path DOUBLE_HATCH_CARGOSHIP1 = new Path(getPath("Double Hatch Cargoship1.pf1.csv"));
        public static final Path DOUBLE_HATCH_CARGOSHIP2 = new Path(getPath("Double Hatch Cargoship2.pf1.csv"));
        public static final Path DOUBLE_HATCH_CARGOSHIP3 = new Path(getPath("Double Hatch Cargoship3.pf1.csv"));
        public static final Path DOUBLE_HATCH_CARGOSHIP4 = new Path(getPath("Double Hatch Cargoship4.pf1.csv"));
        public static final Path DOUBLE_HATCH_CARGOSHIP5 = new Path(getPath("Double Hatch Cargoship5.pf1.csv"));
    }

    public static class Middle {

    }

    public static class Right {

    }

    private static ArrayList<Point> getPath(String filename) {
        CSVParser read;
            try {
                File f= new File(Paths.class.getResource("output\\" + filename).toURI());
                read = CSVFormat.EXCEL.parse(new FileReader(f)); 
                ArrayList<Point> path = new ArrayList<>();
                for (var record : read) 
                    path.add(new Point(Double.parseDouble(record.get("x")), Double.parseDouble(record.get("y"))));
                return path;          
            } catch (Exception e) {}
        return new ArrayList<>();
    } 
}