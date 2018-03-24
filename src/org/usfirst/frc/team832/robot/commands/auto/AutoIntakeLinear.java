package org.usfirst.frc.team832.robot.commands.auto;

import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class AutoIntakeLinear extends Command {


	double pow;
	double time;
	long currentMillis;
	long startMillis;

    public AutoIntakeLinear(double powInput, double time) {
    	requires(Robot.intake);
    	pow = powInput;
    	this.time = time;
    }

    protected void initialize() {
        startMillis = System.currentTimeMillis();
    }

    protected void execute() {
    	Robot.intake.IntakeAuto(pow);
    }

    protected boolean isFinished() {
        currentMillis = System.currentTimeMillis();
        if (currentMillis - startMillis > time) {
            return true;
        } else return false;
    }
    
    protected void end() {
        Robot.intake.stop();
    }

    protected void interrupted() {
    	end();
    }

}
