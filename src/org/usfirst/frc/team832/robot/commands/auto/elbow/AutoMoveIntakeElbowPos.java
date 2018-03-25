package org.usfirst.frc.team832.robot.commands.auto.elbow;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.RobotMap;

import java.net.SocketPermission;

public class AutoMoveIntakeElbowPos extends Command {

	int target;
	
    public AutoMoveIntakeElbowPos(int targetInput) {
    	requires(Robot.intakeElbow);
    	target = targetInput;
    }

    protected void initialize() { }
    
    protected void execute() {
        Robot.intakeElbow.setAutoPos(target);
    }

    protected boolean isFinished() {
       return Robot.intakeElbow.getIsFinished();
    }

    protected void end() {
        Robot.intakeElbow.setAutoPos(target);
        System.out.println("end elbow");
       // Robot.intakeElbow.stop();
    }

    protected void interrupted() {
    	end();
    }

}
