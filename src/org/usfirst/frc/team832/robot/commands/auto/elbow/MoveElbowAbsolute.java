package org.usfirst.frc.team832.robot.commands.auto.elbow;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.Robot;
import org.usfirst.frc.team832.robot.RobotMap;


public class MoveElbowAbsolute extends Command {
    private final int pos;
    private int absolutePosition;

    public MoveElbowAbsolute(int pos) {
        System.out.println("Create elbow");
        requires(Robot.intakeElbow);
        this.pos = pos;
    }

    protected void initialize() {
        System.out.println("init elbow");
        int absolutePosition = RobotMap.intakeElbow.getSensorCollection().getPulseWidthPosition();
        absolutePosition &= 0xFFF;
        System.out.print("got elbow abs: " + absolutePosition);
        RobotMap.intakeElbow.setSelectedSensorPosition(absolutePosition, 0, 0);
        System.out.print(", set elbow quad: " + absolutePosition);
        RobotMap.intakeElbow.set(ControlMode.Position, pos);
        System.out.println(", set elbow target: " + pos);
    }

    protected void execute() {}

    protected boolean isFinished() {
        System.out.print("current elbow pos: " + RobotMap.intakeElbow.getSelectedSensorPosition(0));
        System.out.print(", talon target: " + RobotMap.intakeElbow.getClosedLoopTarget(0));
        System.out.print(", set target: " + pos);
        System.out.println(", error: " + RobotMap.intakeElbow.getClosedLoopError(0));

        return Math.abs(RobotMap.intakeElbow.getSelectedSensorPosition(0) - RobotMap.intakeElbow.getClosedLoopTarget(0))<= 90;
        //return Robot.intakeElbow.getAtBottom();
    }

    protected void end() {
        System.out.println("end elbow");
    }

    protected void interrupted() {
        end();
    }
}