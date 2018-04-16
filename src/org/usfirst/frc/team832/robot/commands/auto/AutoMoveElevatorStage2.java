package org.usfirst.frc.team832.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.Robot;

public class AutoMoveElevatorStage2 extends Command {
	private final double target;
	
    public AutoMoveElevatorStage2(double targetInput) {
    	requires(Robot.elevatorStage2);
    	target = targetInput;
    }

    protected void initialize() {
        if (Robot.elevatorStage2.getAtBottom()) Robot.elevatorStage2.setAtBottom();
    	System.out.println("Begin stage 2");
        Robot.elevatorStage2.setAutoPos(target);
    }
    
    protected void execute() {}

    protected boolean isFinished() {
        boolean finished = false;
        if (Robot.elevatorStage2.isFinished()) {
            System.out.println("Finished stage 2");
            finished = true;
        }
        return finished;
    }

    protected void end() { System.out.println("End stage 2");}

    protected void interrupted() {}
}