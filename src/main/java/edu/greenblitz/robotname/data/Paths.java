package edu.greenblitz.robotname.data;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.greenblitz.motion.base.Point;
import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Paths {
    private static Logger logger = LogManager.getLogger(Paths.class);

    private static HashMap<String, Path<Position>> paths = new HashMap<>();

    public static Path<Position> get(String pathname) {
        if (!paths.containsKey(pathname))
            paths.put(pathname, nativeGetPath("/home/lvuser/deploy/output/" + pathname + ".pf1.csv"));
        return paths.get(pathname);
    }

    public static void init(String... pathname) {
        Arrays.stream(pathname).forEach(Paths::get);
    }

    private static Path<Position> nativeGetPath(String filename) {
        try(CSVParser read = CSVFormat.DEFAULT.parse(new FileReader(new File(filename)))) {
            ArrayList<Position> path = new ArrayList<>();
            List<CSVRecord> records = read.getRecords();
            for (int i = 1; i < records.size(); i++)
                path.add(new Position(new Point(Double.parseDouble(records.get(i).get(1)), Double.parseDouble(records.get(i).get(2))).weaverToLocalizerCoords()));
            return new Path<>(path);
        } catch (Exception e) {
            logger.error(e);
        }

        return new Path<>();
    }
}