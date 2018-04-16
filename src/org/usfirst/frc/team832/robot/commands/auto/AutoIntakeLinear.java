package org.usfirst.frc.team832.robot.commands.auto;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.RobotMap;

public class AutoIntakeLinear extends Command {


	private final double pow;
	private final double time;
	private long currentMillis;
	private long startMillis;

    public AutoIntakeLinear(double powInput, double time) {
        requires(Robot.intake);
        //TODO: THIS IS POSITIVE NEEDED ON THE PRACTICE BOT
        //TODO: THIS SHOULD BE NEGATIVE ON THE ACTUAL BOT
        this.pow = powInput;
    	this.time = time;
    }

    protected void initialize() {
        System.out.println("Begin intake");
        startMillis = System.currentTimeMillis();
        RobotMap.leftIntake.set(ControlMode.PercentOutput, pow);
        RobotMap.rightIntake.set(ControlMode.PercentOutput, pow);
    }

    protected void execute() {

    }

    protected boolean isFinished() {
        boolean finished = false;
        currentMillis = System.currentTimeMillis();
        if (currentMillis - startMillis > time) {
            System.out.println("Finish intake");
            finished = true;
        }
        return finished;
    }
    
    protected void end() {
        System.out.println();
        Robot.intake.stop();
    }

    protected void interrupted() {
    	end();
    }
}
