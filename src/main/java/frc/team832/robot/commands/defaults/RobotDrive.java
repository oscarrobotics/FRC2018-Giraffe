package frc.team832.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team832.GrouchLib.util.OscarMath;
import frc.team832.robot.OI;
import frc.team832.robot.RobotMap;

/**
 *
 */
public class RobotDrive extends CommandBase {

//    final WestCoastDrive westCoastDrive = Robot.westCoastDrive;

    public RobotDrive() {
//        requires(Robot.westCoastDrive);
    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {
        double leftStick = .5 * OscarMath.signumPow(OI.driverPad.getRawAxis(1), 2);
        double rightStick = .2 * OscarMath.signumPow(OI.driverPad.getRawAxis(2), 2);
        //westCoastDrive.ArcadeDrive(leftStick, rightStick, ControlMode.PercentOutput);
        RobotMap.diffDrive.curvatureDrive(-leftStick, rightStick, true);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
