package org.usfirst.frc.team832.robot.commands.defaults;

import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

public class RunIntake extends Command {
	
	Intake intake = Robot.intake;
	
	public RunIntake() {
		requires(Robot.intake);
	}
	
	protected void initialized() {
	}
	
	protected void execute() {
		//TODO: find axis port
		double yValue = Robot.oi.operatorPad.getRawAxis(1);
		double xValue = Robot.oi.operatorPad.getRawAxis(0);
		intake.IntakeWithStick(xValue, yValue);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
