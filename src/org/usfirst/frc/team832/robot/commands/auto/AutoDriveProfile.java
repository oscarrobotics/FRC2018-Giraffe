package org.usfirst.frc.team832.robot.commands.auto;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import org.usfirst.frc.team832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.awt.*;
import java.io.File;

public class AutoDriveProfile extends Command {
    private static final int min_points = 60; // minimum points to stream to talon before starting. TODO: Can this be lower?
    private Trajectory trajectory_left;
    private Trajectory trajectory_right;

    public AutoDriveProfile(String leftFile, String rightFile) {
        this(Pathfinder.readFromCSV(new File(leftFile)), Pathfinder.readFromCSV(new File(rightFile)));
    }

    public AutoDriveProfile(Trajectory traj_l, Trajectory traj_r) {
        requires(Robot.westCoastDrive);
        this.trajectory_left = traj_l;
        this.trajectory_right = traj_r;
    }

   @Override
   protected void initialize() {
        Robot.westCoastDrive.resetEncoders();
        System.out.println("Filling talons...");
        Robot.westCoastDrive.startFillingLeft(Robot.pathfinderFormatToTalon(trajectory_left), trajectory_left.length());
        Robot.westCoastDrive.startFillingRight(Robot.pathfinderFormatToTalon(trajectory_right), trajectory_right.length());
        while (Robot.westCoastDrive.getLeftMpStatus().btmBufferCnt < min_points || Robot.westCoastDrive.getRightMpStatus().btmBufferCnt < min_points) {
            Robot.westCoastDrive.periodic();
        }
        System.out.println("Talons filled (enough)!");
   }

    @Override
    protected void execute() {
        Robot.westCoastDrive.leftMpControl(SetValueMotionProfile.Enable);
        Robot.westCoastDrive.rightMpControl(SetValueMotionProfile.Enable);
    }

    @Override
    protected void end() {
        System.out.println("Done MP driving!");
        Robot.westCoastDrive.leftMpControl(SetValueMotionProfile.Disable);
        Robot.westCoastDrive.rightMpControl(SetValueMotionProfile.Disable);
        Robot.westCoastDrive.stop();
    }

    @Override
    protected boolean isFinished() {
        boolean leftComplete = Robot.westCoastDrive.getLeftMpStatus().activePointValid && Robot.westCoastDrive.getLeftMpStatus().isLast;
        boolean rightComplete = Robot.westCoastDrive.getRightMpStatus().activePointValid && Robot.westCoastDrive.getRightMpStatus().isLast;
        boolean trajectoryComplete = leftComplete && rightComplete;
        if (trajectoryComplete) {
            System.out.println("Finished Trajectory");
        }
        return trajectoryComplete;
    }
}
