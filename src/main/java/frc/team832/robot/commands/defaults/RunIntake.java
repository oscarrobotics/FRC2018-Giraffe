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
        double yValue = OI.driverPad.getRawAxis(3) - OI.driverPad.getRawAxis(2);
        double xValue = 0; // joystick is broken
        Robot.intake.IntakeWithStick(xValue, yValue);
    }
}
