package edu.greenblitz.robotname.subsystems;

import edu.greenblitz.robotname.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Pi {

    private static InetAddress pi;

    public static void init() {
        try {
            pi = InetAddress.getByName("10.45.90.8");
        } catch (UnknownHostException e) {
            LogManager.getLogger().error("could not resolve PI's ip");
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
        return isReachable(1000);
    }

    public static void update() {
        SmartDashboard.putBoolean("Pi::isReachable", isReachable());
    }
}