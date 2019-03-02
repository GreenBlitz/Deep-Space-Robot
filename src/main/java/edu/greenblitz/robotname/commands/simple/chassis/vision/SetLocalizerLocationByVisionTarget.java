//package edu.greenblitz.robotname.commands.simple.chassis.vision;
//
//import edu.greenblitz.robotname.data.vision.VisionMaster;
//import edu.greenblitz.robotname.subsystems.Chassis;
//import edu.greenblitz.utils.command.GBCommand;
//import org.greenblitz.motion.base.Position;
//
//public class SetLocalizerLocationByVisionTarget extends GBCommand {
//
//    private double m_x, m_y;
//
//    public SetLocalizerLocationByVisionTarget(double x, double y) {
//        m_x = x;
//        m_y = y;
//    }
//
//    public SetLocalizerLocationByVisionTarget(Position location) {
//        this(location.getX(), location.getY());
//    }
//
//    @Override
//    protected void initialize() {
//        var targetLocation = VisionMaster.getInstance().getStandardizedTargetsData();
//        Chassis.getInstance().getLocation().set(m_x - targetLocation.x, m_y - targetLocation.y);
//    }
//
//    @Override
//    protected boolean isFinished() {
//        return true;
//    }
//}