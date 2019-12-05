package frc.team832.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team832.robot.Robot;

public class AutoMoveElevatorStage2 extends CommandBase {

    private final double target;
    private boolean _doesWait;

    public AutoMoveElevatorStage2(double targetInput) {
        addRequirements(Robot.elevatorStage2);
        target = targetInput;
        _doesWait = true;
    }

    public AutoMoveElevatorStage2(double targetInput, boolean doesWait) {
        addRequirements(Robot.elevatorStage2);
        target = targetInput;
        _doesWait = doesWait;
    }

    public void initialize() {
        if (Robot.elevatorStage2.getAtBottom()) Robot.elevatorStage2.setAtBottom();
        System.out.println("Begin stage 2");
        Robot.elevatorStage2.setAutoPos(target);
    }

    public void execute() {
    }

    public boolean isFinished() {
        boolean finished = false;
        if (Robot.elevatorStage2.isFinished() || !_doesWait) {
            System.out.println("Finished stage 2");
            finished = true;
        }
        return finished;
    }

    public void end() {
        System.out.println("End stage 2");
    }

    public void interrupted() {
    }
}