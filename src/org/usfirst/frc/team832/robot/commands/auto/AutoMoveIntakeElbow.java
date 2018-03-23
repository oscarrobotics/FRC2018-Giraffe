package org.usfirst.frc.team832.robot.commands.auto;

import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoMoveIntakeElbow extends Command {

	int target;
	
    public AutoMoveIntakeElbow(int targetInput) {
    	requires(Robot.intakeElbow);
    	target = targetInput;
    }

    protected void initialize() {
    	Robot.intakeElbow.start();
    }
    
    protected void execute() {
    	Robot.intakeElbow.setAutoPos(target);
    }

    protected boolean isFinished() {
       return Robot.intakeElbow.getIsFinished();
    }

    protected void end() {
    	Robot.intakeElbow.stop();
    }

    protected void interrupted() {
    	end();
    }

}
