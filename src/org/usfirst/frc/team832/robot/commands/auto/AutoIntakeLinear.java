package org.usfirst.frc.team832.robot.commands.auto;

import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoIntakeLinear extends Command {


	double pow;

    public AutoIntakeLinear(double powInput) {
    	requires(Robot.intake);
    	pow = powInput;
    }

    protected void initialize() {    	
    }

    protected void execute() {
    	Robot.intake.IntakeAuto(pow);
    }

    protected boolean isFinished() {
        return true;
    }
    
    protected void end() {
    }

    protected void interrupted() {
    	end();
    }

}
