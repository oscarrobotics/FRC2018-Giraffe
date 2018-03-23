package org.usfirst.frc.team832.robot.commands.teleop;

import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ExpandIntake extends Command {
	public ExpandIntake(){
		requires(Robot.pneumatics);
	}
	
	protected void execute(){
		Robot.pneumatics.expandIntake();
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
