
package org.usfirst.frc.team832.robot.commands.defaults;

import org.usfirst.frc.team832.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class RunElevatorStage2 extends Command {
	
	public RunElevatorStage2(){
		requires(Robot.elevatorStage2);
	}
	
	protected void initialize() {
		Robot.elevatorStage2.start();
	}
	
	protected void execute() {
		double sliderVal = Robot.oi.operatorPad.getRawAxis(3);
		if(Robot.oi.operatorPad.getRawButton(5)) {
			Robot.elevatorStage2.setPos(1.0);
		}
		else if(Robot.oi.operatorPad.getRawButton(6)) {
			Robot.elevatorStage2.setPos(1.0);
		}
		else if(Robot.oi.operatorPad.getRawButton(7)) {
			Robot.elevatorStage2.setPos(0.5);
		}
		else {
			Robot.elevatorStage2.setPos(sliderVal);
		}
	}
	
		
	protected boolean isFinished() {
		return false;
	}

	protected  void end() {
		Robot.elevatorStage2.stop();
	}
	
	protected void interrupted() {
		end();
	}
}
