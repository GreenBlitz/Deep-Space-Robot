package edu.greenblitz.robotname.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Pi {

    private static InetAddress pi;
    private static NetworkTableEntry exists;

    public static void init() {
        try {
            pi = InetAddress.getByName("10.45.90.8");
        } catch (UnknownHostException e) {
            LogManager.getLogger().error("could not resolve PI's ip");
        }

        exists = NetworkTableInstance.getDefault().getTable("vision").getEntry("exists");

        if (!exists.getBoolean(false)) {
            LogManager.getLogger().warn("Pi does not appear on networktables");
            SmartDashboard.putBoolean("Pi::exists", false);
        } else {
            SmartDashboard.putBoolean("Pi::exists", true);
        }
    }

    public static boolean isReachable(int timeout) {
        try {
            return pi.isReachable(timeout);
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isReachable() {
        return isReachable(10);
    }

    public static void update() {
        SmartDashboard.putBoolean("Pi::isReachable", isReachable());
        SmartDashboard.putBoolean("Pi::exists", exists.getBoolean(false));
    }
}
