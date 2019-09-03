/*

package frc.team832.robot.commands.auto;

import frc.team832.GrouchLib.motorcontrol.*;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motion.TrajectoryPoint;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team832.robot.Constants;
import frc.team832.robot.Robot;
import frc.team832.robot.RobotMap;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.io.File;


public class MoveOnPath extends Command {
    private static Notifier trajectoryProcessor;
    private final int TRAJECTORY_SIZE;
    private CANTalon left;
    private CANTalon right;
    private Trajectory trajectoryR, trajectoryL;
    private MotionProfileStatus statusLeft, statusRight;
    private boolean isRunning;
    private int dir;

     // Creates this command using the file prefix to determine
     // the files to load.
     //
     // @param name name of the trajectory
     //
    public MoveOnPath(String name) {
        requires(Robot.westCoastDrive);
        setName("MoveOnPath-" + name);

        Direction direction = Direction.FORWARD;

        left = RobotMap.left1;
        right = RobotMap.right1;

        switch (direction) {
            case BACKWARD:
                dir = -1;
                break;
            case FORWARD:
            default:
                dir = 1;
                break;
        }

//        trajectoryL = Pathfinder.readFromFile(new File("/home/lvuser/trajectories/" + name + "_left_detailed.traj"));
//        trajectoryR = Pathfinder.readFromFile(new File("/home/lvuser/trajectories/" + name + "_right_detailed.traj"));
        trajectoryL = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + name + "_left_detailed.csv"));
        trajectoryR = Pathfinder.readFromCSV(new File("/home/lvuser/paths/" + name + "_right_detailed.csv"));
        if (trajectoryProcessor == null) {
            trajectoryProcessor = new Notifier(() -> {
                // TODO: implement GeniusMotor class
//                left.processMotionProfileBuffer();
//                right.processMotionProfileBuffer();
            });
        }

        statusLeft = new MotionProfileStatus();
        statusRight = new MotionProfileStatus();

        if (trajectoryL != null) {
            TRAJECTORY_SIZE = trajectoryL.length();

            System.out.println(getName() + " constructed: " + TRAJECTORY_SIZE);
        } else {
            TRAJECTORY_SIZE = 0;
            System.out.println(getName() + " could not be constructed!");
            end();
        }
    }

    //Called just before this Command runs for the first time.
    protected void initialize() {
        System.out.println("MoveOnPath: Initializing...");

        // Reset command get
        reset();

        // Change motion control frame period
        // TODO: implement GeniusMotor class
//        left.changeMotionControlFramePeriod(10);
//        right.changeMotionControlFramePeriod(10);

        // Fill TOP (API-level) buffer
        fillTopBuffer();

        // Start processing
        // i.e.: moving API points to RAM
        trajectoryProcessor.startPeriodic(0.005);
        System.out.println(getName() + " Initialized");
    }

    //Called repeatedly when this Command is scheduled to run.
    protected void execute() {
        // TODO: implement GeniusMotor class
//        left.getMotionProfileStatus(statusLeft);
//        right.getMotionProfileStatus(statusRight);

        // Give a slight buffer when we process to make sure we don't bite off more than
        // we can chew or however that metaphor goes.
        if (!isRunning && statusLeft.btmBufferCnt >= 5 && statusRight.btmBufferCnt >= 5) {
            setMotionProfileMode(SetValueMotionProfile.Enable);

            System.out.println("Starting motion profile...");

            isRunning = true;
        }
    }

    @Override
    protected boolean isFinished() {
        // If we're running, only finish if both talons
        // reach their last valid point
        return
                isRunning &&
                        statusLeft.activePointValid &&
                        statusLeft.isLast &&
                        statusRight.activePointValid &&
                        statusRight.isLast;
    }

    @Override
    protected void end() {
        // Stop processing trajectories
        trajectoryProcessor.stop();

        // TODO: implement GeniusMotor class
//        left.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10, 0);
//        right.setStatusFramePeriod(StatusFrameEnhanced.Status_1_General, 10, 0);

        Robot.westCoastDrive.stop();

        System.out.println(getName() + " Finished running");
    }

     // Fill top-level (API-level) buffer with all points
    private void fillTopBuffer() {
        for (int i = 0; i < TRAJECTORY_SIZE; i++) {
            TrajectoryPoint trajPointL = new TrajectoryPoint();
            TrajectoryPoint trajPointR = new TrajectoryPoint();

            double currentPosL = trajectoryL.segments[i].position * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
            double currentPosR = trajectoryR.segments[i].position * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);

            double velocityL = trajectoryL.segments[i].velocity * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);
            double velocityR = trajectoryR.segments[i].velocity * 12.0 * (1 / Constants.kInchesPerWheelTurn) * (1 / Constants.kWheelTurnsPerEncoderTurn);

            boolean isLastPointL = TRAJECTORY_SIZE == i + 1;
            boolean isLastPointR = TRAJECTORY_SIZE == i + 1;
            boolean isZero = i == 0;

            // For each point, fill our structure and pass it to API
            trajPointL.position = -currentPosL * (256.0 * 4.0);
            trajPointR.position = -currentPosR * (256.0 * 4.0);
            trajPointL.velocity = velocityL * (256.0 * 4.0); /// 5.0;
            trajPointR.velocity = velocityR * (256.0 * 4.0); /// 5.0;
            trajPointL.profileSlotSelect0 = RobotMap.DrivePIDID;
            trajPointR.profileSlotSelect0 = RobotMap.DrivePIDID;

            // Sets the duration of each trajectory point to 20ms
            trajPointL.timeDur = 20;
            trajPointR.timeDur = 20;

            // Set these to true on the first point
            trajPointL.zeroPos = isZero;
            trajPointR.zeroPos = isZero;

            // Set these to true on the last point
            trajPointL.isLastPoint = isLastPointL;
            trajPointR.isLastPoint = isLastPointR;

            // Push to API level buffer
            // TODO: implement GeniusMotor class
//            left.pushMotionProfileTrajectory(trajPointL);
//            right.pushMotionProfileTrajectory(trajPointR);
        }
    }

    private void setMotionProfileMode(SetValueMotionProfile value) {
        left.set(ControlMode.MotionProfile, value.value);
        right.set(ControlMode.MotionProfile, value.value);
    }

    private void reset() {
        // Reset flags and motion profile modes
        isRunning = false;
        setMotionProfileMode(SetValueMotionProfile.Disable);
        Robot.westCoastDrive.resetEncoders();

        // Clear the trajectory buffer
        // TODO: implement GeniusMotor class
//        left.clearMotionProfileTrajectories();
//        right.clearMotionProfileTrajectories();

        System.out.println("Cleared trajectories; check: " + statusLeft.btmBufferCnt);
    }

    public enum Direction {
        BACKWARD,
        FORWARD
    }
}
*/
