package org.usfirst.frc.team832.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.Robot;

public class AutoMoveElevatorStage1 extends Command {

    final double target;

    public AutoMoveElevatorStage1(double targetInput) {
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
