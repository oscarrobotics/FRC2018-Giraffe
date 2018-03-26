package org.usfirst.frc.team832.robot.commands.auto.elbow;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.RobotMap;


public class MoveElbowToBottom extends Command {
    public MoveElbowToBottom() {
        requires(Robot.intakeElbow);
    }


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        RobotMap.intakeElbow.set(ControlMode.PercentOutput, -.1);
    }

    @Override
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {

        return Robot.intakeElbow.getAtBottom();
    }

    @Override
    protected void end() {
        Robot.intakeElbow.setAtBottom();
        RobotMap.intakeElbow.set(ControlMode.Position, 0);
    }


    /**
     * <p>
     * Called when the command ends because somebody called {@link #cancel()} or
     * another command shared the same requirements as this one, and booted it out. For example,
     * it is called when another command which requires one or more of the same
     * subsystems is scheduled to run.
     * </p><p>
     * This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     * </p><p>
     * Generally, it is useful to simply call the {@link #end()} method within this
     * method, as done here.
     * </p>
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
