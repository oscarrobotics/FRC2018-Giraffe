package org.usfirst.frc.team832.robot.commands.auto;

import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoMoveElevatorStage2 extends Command {


	double target;
	
    public AutoMoveElevatorStage2(double targetInput) {
    	requires(Robot.elevatorStage2);
    	target = targetInput;
    }

    protected void initialize() {
    	
    }
    
    protected void execute() {
    	Robot.elevatorStage2.setAutoPos(target);
    }

    protected boolean isFinished() {
        return Robot.elevatorStage2.isFinished();
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();
    }
}
