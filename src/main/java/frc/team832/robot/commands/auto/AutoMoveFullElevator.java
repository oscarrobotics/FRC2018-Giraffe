package frc.team832.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team832.robot.Constants;
import frc.team832.robot.Robot;
import frc.team832.robot.subsystems.ElevatorStage1;
import frc.team832.robot.subsystems.ElevatorStage2;


public class AutoMoveFullElevator extends CommandBase {

    private static Constants.ElevatorPosition elevatorTarget;
    private static ElevatorStage1 stage1;
    private static ElevatorStage2 stage2;

    public AutoMoveFullElevator(Constants.ElevatorPosition elevatorPos, ElevatorStage1 stage1, ElevatorStage2 stage2) {
        this.stage1 = stage1;
        this.stage2 = stage2;
        addRequirements(stage1, stage2);
        this.elevatorTarget = elevatorPos;
    }

    public void initialize() {
        Robot.elevatorStage2.start();
        Robot.elevatorStage1.start();
    }

    public void execute() {
        Robot.elevatorStage2.setPos(elevatorTarget);
        Robot.elevatorStage1.setPos(elevatorTarget);
    }

    public boolean isFinished() {
        return Robot.elevatorStage2.isFinished() && Robot.elevatorStage1.isFinished();
    }

    protected void end() {
    }

    protected void interrupted() {
        end();
    }
}
