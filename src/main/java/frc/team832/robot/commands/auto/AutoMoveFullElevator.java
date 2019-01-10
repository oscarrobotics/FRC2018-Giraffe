package frc.team832.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Robot;


public class AutoMoveFullElevator extends Command {

    final double target;

    public AutoMoveFullElevator(double targetInput) {
        requires(Robot.elevatorStage1);
        requires(Robot.elevatorStage2);
        target = targetInput;
    }

    protected void initialize() {
        Robot.elevatorStage2.start();
        Robot.elevatorStage1.start();
    }

    protected void execute() {
        Robot.elevatorStage2.setPos(target);
        Robot.elevatorStage1.setPos(target);
    }

    protected boolean isFinished() {
        return Robot.elevatorStage2.isFinished() && Robot.elevatorStage1.isFinished();
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
