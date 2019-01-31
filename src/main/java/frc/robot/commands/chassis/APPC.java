// package frc.robot.commands.chassis;

// import edu.wpi.first.wpilibj.command.Command;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.motion.app.AbstractPositionPursuitController;
// import frc.motion.pathing.Path;
// import frc.motion.base.Point;
// import frc.robot.subsystems.Chassis;

// import java.util.Arrays;

// public class APPC extends Command {

//     private Chassis m_chassis;
//     private Path m_path;
//     private AbstractPositionPursuitController m_controller;

//     public APPC(AbstractPositionPursuitController controller) {
//         requires(Chassis.getInstance());
//         m_controller = controller;
//         m_path = controller.getM_path();
//         m_chassis = Chassis.getInstance();
//     }

//     @Override
//     protected void initialize() {
//         m_chassis.setCoast();
//         m_path.sendToCSV("m_path");
//         super.initialize();
//     }

//     @Override
//     protected void execute() {
//         double[] moveValues = m_controller.iteration(m_chassis.getLocation());
//         System.out.println("Move vals - " + Arrays.toString(moveValues));
//         m_chassis.tankDrive(moveValues[0], moveValues[1]);
//     }

//     @Override
//     protected void end(){
//         SmartDashboard.putNumber("APPC final x", m_chassis.getLocation().getX());
//         SmartDashboard.putNumber("APPC final y", m_chassis.getLocation().getY());
//     }

//     protected void savePath(String fileName){
//         m_path.saveAsCSV(fileName);
//     }

//     @Override
//     protected boolean isFinished() {
//         return m_controller.isFinished(m_chassis.getLocation());
//     }
// }