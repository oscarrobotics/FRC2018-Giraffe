package frc.team832.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team832.robot.OI;
import frc.team832.robot.Robot;

public class RunElevatorStage1 extends CommandBase {

    public RunElevatorStage1() {
        addRequirements(Robot.elevatorStage1);
    }

    @Override
    public void initialize() {
        Robot.elevatorStage1.stop();
        Robot.elevatorStage1.start();
    }

    @Override
    public void execute() {
        double sliderVal = OI.operatorPad.getRawAxis(2);
        if (OI.operatorPad.getGreenButton().get())
            Robot.elevatorStage1.setPos(1.0);
        else if (OI.operatorPad.getYellowButton().get())
            Robot.elevatorStage1.setPos(0.5);
        else if (OI.operatorPad.getRedButton().get())
            Robot.elevatorStage1.setPos(0.2);
        else
            Robot.elevatorStage1.setPos(sliderVal);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.elevatorStage1.stop();
    }

    protected void interrupted() {
        end();
    }
}
