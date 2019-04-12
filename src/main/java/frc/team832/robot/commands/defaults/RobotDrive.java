package frc.team832.robot.commands.defaults;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.OI;
import frc.team832.robot.Robot;
import frc.team832.robot.RobotMap;

/**
 *
 */
public class RobotDrive extends Command {

//    final WestCoastDrive westCoastDrive = Robot.westCoastDrive;

    public RobotDrive() {
        requires(Robot.westCoastDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("RobotDrive INIT");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        double distPow, rotPow;
        double leftStick = .7 * OI.driverPad.getRawAxis(1);
        double rightStick = .7 * OI.driverPad.getRawAxis(4);

        if (OI.driverPad.getAButton()) {
            distPow = OI.driverPad.getBButton() ? Robot.vision.getDistAdjust() : leftStick;
            rotPow = Robot.vision.getTurnAdjust();
        } else {
            distPow = leftStick;
            rotPow = rightStick;
        }

        Robot.westCoastDrive.ArcadeDrive(rotPow, distPow, ControlMode.PercentOutput);
//        RobotMap.left1.set(ControlMode.PercentOutput, -leftStick);
//        RobotMap.right1.set(ControlMode.PercentOutput, -leftStick);
//        RobotMap.diffDrive.curvatureDrive(-leftStick, rightStick, true);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.westCoastDrive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
