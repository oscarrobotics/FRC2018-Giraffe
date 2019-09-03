package frc.team832.robot.commands.defaults;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.team832.robot.OI;
import frc.team832.robot.Robot;

public class RunIntake extends CommandBase {
    public RunIntake() {
        addRequirements(Robot.intake);
    }

    @Override
    public void execute() {
        double yValue = OI.operatorPad.getRawAxis(1);
        double xValue = 0; // joystick is broken
        Robot.intake.IntakeWithStick(xValue, yValue);
    }
}
