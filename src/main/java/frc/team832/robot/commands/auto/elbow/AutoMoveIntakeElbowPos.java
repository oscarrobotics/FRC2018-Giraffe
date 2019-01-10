package frc.team832.robot.commands.auto.elbow;

import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Robot;

public class AutoMoveIntakeElbowPos extends Command {

    final int target;
    boolean wasAtBottom;

    public AutoMoveIntakeElbowPos(int targetInput) {
        requires(Robot.intakeElbow);
        target = targetInput;
    }

    protected void initialize() {
        Robot.intakeElbow.setAutoPos(target);
    }

    protected void execute() {
    }

    protected boolean isFinished() {

        return Robot.intakeElbow.getIsFinished();
    }

    protected void end() {
        Robot.intakeElbow.setAutoPos(target);
        System.out.println("end elbow");
        // Robot.intakeElbow.stop();
    }

    protected void interrupted() {
        end();
    }

}
