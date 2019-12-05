package frc.team832.robot.commands.defaults;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team832.robot.Robot;

public class RunElevatorStage2 extends CommandBase {

    public RunElevatorStage2() {
        addRequirements(Robot.elevatorStage2);
    }

    public void initialize() {
        Robot.elevatorStage2.start();
    }

    public void execute() {
        double sliderVal = Robot.oi.operatorPad.getRawAxis(3);
        if (Robot.oi.operatorPad.getRawButton(5)) {
            Robot.elevatorStage2.setPositionFromSlider(1.0);
        } else if (Robot.oi.operatorPad.getRawButton(6)) {
            Robot.elevatorStage2.setPositionFromSlider(1.0);
        } else if (Robot.oi.operatorPad.getRawButton(7)) {
            Robot.elevatorStage2.setPositionFromSlider(-1.0);
        } else {
            Robot.elevatorStage2.setPositionFromSlider(sliderVal);
        }
    }


    public boolean isFinished() {
        return false;
    }

    public void end() {
        Robot.elevatorStage2.stop();
    }

    public void interrupted() {
        end();
    }
}
