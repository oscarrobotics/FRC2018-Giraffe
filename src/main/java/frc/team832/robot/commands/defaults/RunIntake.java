package frc.team832.robot.commands.defaults;

import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Robot;
import frc.team832.robot.subsystems.Intake;

public class RunIntake extends Command {

    final Intake intake = Robot.intake;

    public RunIntake() {
        requires(Robot.intake);
    }

    protected void initialized() {
    }

    protected void execute() {
        //TODO: find axis port
        double yValue = Robot.oi.operatorPad.getRawAxis(1);
//        double xValue = Robot.oi.operatorPad.getRawAxis(0);
        double xValue = 0; // joystick is broken
        intake.IntakeWithStick(xValue, yValue);
    }

    @Override
    protected boolean isFinished() {
        // TODO Auto-generated method stub
        return false;
    }


}
