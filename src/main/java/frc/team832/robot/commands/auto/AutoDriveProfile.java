/*
package frc.team832.robot.commands.auto;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Robot;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;

public class AutoDriveProfile extends Command {
    private static final int min_points = 85; // minimum points to stream to talon before starting. TODO: Can this be lower?
    private final Trajectory trajectory_left;
    private final Trajectory trajectory_right;

    public AutoDriveProfile(String name) {
        requires(Robot.westCoastDrive);
        String leftfile = "/home/lvuser/paths/" + name + "_left_detailed.csv";
        String rightfile = "/home/lvuser/paths/" + name + "_right_detailed.csv";
        // this.trajectory_left = Pathfinder.readFromCSV(new File(leftfile));
        // this.trajectory_right = Pathfinder.readFromCSV(new File(rightfile));
    }

    @Override
    protected void initialize() {
        System.out.println("Resetting encoders");
        Robot.westCoastDrive.resetEncoders();
        System.out.println("Filling talons...");
        // TODO: Implement GeniusMotor class
//        Robot.westCoastDrive.startFillingLeft(Robot.pathfinderFormatToTalon(trajectory_left), trajectory_left.length());
//        Robot.westCoastDrive.startFillingRight(Robot.pathfinderFormatToTalon(trajectory_right), trajectory_right.length());
        while (Robot.westCoastDrive.getLeftMpStatus().btmBufferCnt < min_points || Robot.westCoastDrive.getRightMpStatus().btmBufferCnt < min_points) {
            Robot.westCoastDrive.periodic();
        }
        System.out.println("Talons filled (enough)!");
    }

    @Override
    protected void execute() {
        System.out.println("Running profile");
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

        return Robot.westCoastDrive.getLeftMpStatus().btmBufferCnt == 0 && Robot.westCoastDrive.getRightMpStatus().btmBufferCnt == 0;
//        boolean leftComplete = Robot.westCoastDrive.getLeftMpStatus().activePointValid && Robot.westCoastDrive.getLeftMpStatus().isLast;
//        boolean rightComplete = Robot.westCoastDrive.getRightMpStatus().activePointValid && Robot.westCoastDrive.getRightMpStatus().isLast;
//        //System.out.println(String.format("LeftStatus: %s, RightStatus: %s", Robot.westCoastDrive.getLeftMpStatus(), Robot.westCoastDrive.getRightMpStatus()));
//        boolean trajectoryComplete = leftComplete && rightComplete;
//        if (trajectoryComplete) {
//            System.out.println("Finished Trajectory");
//        }
//        return trajectoryComplete;
    }
}
*/
