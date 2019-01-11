package frc.team832.robot.commands.automodes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team832.robot.commands.auto.AutoDriveDistance;

/**
 *
 */
public class AUTOMODE_BaseLine extends CommandGroup {

    public AUTOMODE_BaseLine() {
        addSequential(new AutoDriveDistance(0.5, 0.0, 7000, 0));

    }
}
