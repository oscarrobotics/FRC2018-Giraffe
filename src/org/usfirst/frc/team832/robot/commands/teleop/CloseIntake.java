package org.usfirst.frc.team832.robot.commands.teleop;

import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class CloseIntake extends Command {

	public CloseIntake(){
		requires(Robot.pneumatics);
	}
	
	protected void execute(){
		Robot.pneumatics.closeIntake();
	}	
	
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

	protected void end() {
		
	}
	
	protected void inturrepted() {
		end();
	}

}
