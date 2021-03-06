package frc.team832.robot.commands.defaults;

import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Robot;

public class RunIntakeElbow extends Command {

    public RunIntakeElbow() {
        requires(Robot.intakeElbow);
    }

    protected void initialize() {
        Robot.intakeElbow.start();
    }

    protected void execute() {
        double potVal = Robot.oi.operatorPad.getRawAxis(4);
        Robot.intakeElbow.setPos(potVal);
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
        Robot.intakeElbow.stop();
    }

    protected void interrupted() {
        end();
    }
}
