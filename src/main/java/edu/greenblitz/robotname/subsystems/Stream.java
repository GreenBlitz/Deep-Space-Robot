package edu.greenblitz.robotname.subsystems;

import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;

public class Stream {
    public static void init() {
        var cam = CameraServer.getInstance().startAutomaticCapture();
        cam.setFPS(24);
        cam.setResolution(160, 120);
        cam.setPixelFormat(VideoMode.PixelFormat.kMJPEG);
    }
}
