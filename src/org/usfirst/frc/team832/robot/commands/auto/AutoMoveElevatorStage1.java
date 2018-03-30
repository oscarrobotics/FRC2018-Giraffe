package org.usfirst.frc.team832.robot.commands.auto;

import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.subsystems.ElevatorStage1;

public class AutoMoveElevatorStage1 extends Command {

	final int target;
	
    public AutoMoveElevatorStage1(int targetInput) {
    	requires(Robot.elevatorStage1);
    	target = targetInput;
    }

    protected void initialize() {
    	Robot.elevatorStage1.start();
    }
    
    protected void execute() {
    	Robot.elevatorStage1.setPos(target);
    }

    protected boolean isFinished() {
        
    	return Robot.elevatorStage1.isFinished();
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();
    }
}
