package edu.greenblitz.robotname.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Pi implements Runnable {

    private static InetAddress pi;

    static {
        try {
            pi = InetAddress.getByName("10.45.90.8");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            SmartDashboard.putBoolean("Pi::isReachable", isReachable());
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
}