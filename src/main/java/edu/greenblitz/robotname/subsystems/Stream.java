package edu.greenblitz.robotname.subsystems;

import edu.wpi.cscore.VideoMode;
import edu.wpi.first.cameraserver.CameraServer;

public class Stream {
    public static void init() {
        var cam = CameraServer.getInstance().startAutomaticCapture();
        cam.setFPS(40);
        cam.setResolution(240, 135);
        cam.setPixelFormat(VideoMode.PixelFormat.kMJPEG);
    }
}
