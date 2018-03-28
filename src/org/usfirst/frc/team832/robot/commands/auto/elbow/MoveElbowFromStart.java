package org.usfirst.frc.team832.robot.commands.auto.elbow;

import com.ctre.phoenix.motorcontrol.ControlMode;
import org.usfirst.frc.team832.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team832.robot.RobotMap;


public class MoveElbowFromStart extends Command {
    private final int pos;
    private final boolean zeroes;
    private boolean finished = false;

    private int startPos;

    public MoveElbowFromStart(int pos) {
        this(pos, false);
    }

    public MoveElbowFromStart(int pos, boolean zeroes) {
        requires(Robot.intakeElbow);
        this.pos = pos;
        this.zeroes = zeroes;
    }

    protected void initialize() {
        System.out.println("start elbow from pos " + RobotMap.intakeElbow.setSelectedSensorPosition(0,0,0));
        if (zeroes) {
            RobotMap.intakeElbow.setSelectedSensorPosition(0, 0, 0);
            startPos = 0;
        } else {
            startPos = RobotMap.intakeElbow.getSelectedSensorPosition(0);
        }
        RobotMap.intakeElbow.set(ControlMode.Position, startPos - pos);
    }

    protected void execute() {}

    protected boolean isFinished() {
        finished = Math.abs(RobotMap.intakeElbow.getSelectedSensorPosition(0)  - RobotMap.intakeElbow.getClosedLoopTarget(0)) <= 50;
        if (finished) System.out.println("finish elbow");
        return finished;
    }

    protected void end() {
        System.out.println("end elbow");
    }

    protected void interrupted() {
        end();
    }
}
