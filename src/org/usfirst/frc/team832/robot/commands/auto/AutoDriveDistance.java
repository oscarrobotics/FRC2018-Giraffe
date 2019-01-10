package org.usfirst.frc.team832.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.RobotMap;

public class AutoDriveDistance extends Command {

    protected final double power, delay, distance;
    protected final double angle;
    protected double leftD, rightD;

    public AutoDriveDistance(double power, double delay, double distance, double angleIn) {
        this.power = -power;
        this.delay = delay;
        this.distance = (distance);
        this.angle = angleIn;
        requires(Robot.westCoastDrive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        RobotMap.left1.setSensorPosition(0);
        RobotMap.right1.setSensorPosition(0);
        //Timer.delay(delay);

        //TODO: Look into this
        //Robot.gyroPID.setSetpoint(this.angle);
        //Robot.gyroPID.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        // Update motors
        //Robot.westCoastDrive.ArcadeDrive(power, Robot.gyroPID.pidOut, ControlMode.PercentOutput);
        Robot.westCoastDrive.ArcadeDriveSpeed(power, angle);
        //Robot.westCoastDrive.TankDrive(power, power);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//		return (distance <= Math.abs(Robot.westCoastDrive.getMin()));
        return (distance <= RobotMap.left1.getSensorPosition());
        //return false; // for testing purposes only
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.westCoastDrive.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }


}
