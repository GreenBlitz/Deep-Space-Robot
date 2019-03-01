package edu.greenblitz.utils;

import org.greenblitz.motion.base.Position;
import org.greenblitz.motion.pathing.Path;

import java.util.HashMap;

public class Paths {
    private static HashMap<String, Path<Position>> paths = new HashMap<>();

    public static Path<Position> get(String pathname) {
        if (!paths.containsKey(pathname))
            paths.put(pathname, nativeGetPath(pathname));
        return paths.get(pathname);
    }

    private static Path<Position> nativeGetPath(String pathname) {
        return null;
    }
}