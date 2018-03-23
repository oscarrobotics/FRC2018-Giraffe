package org.usfirst.frc.team832.robot.commands.teleop;

import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftHigh extends Command {

	public ShiftHigh(){
		requires(Robot.pneumatics);
	}
	
	protected void execute(){
		Robot.pneumatics.shiftToHigh();
	}	
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void end() {
		
	}
	
	protected void interrupted() {
		end();
	}
}
