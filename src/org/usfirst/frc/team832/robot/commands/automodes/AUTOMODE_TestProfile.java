package org.usfirst.frc.team832.robot.commands.automodes;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.usfirst.frc.team832.robot.commands.auto.AutoDriveProfile;


public class AUTOMODE_TestProfile extends CommandGroup {

    public AUTOMODE_TestProfile(String[] traj) {
        addSequential(new AutoDriveProfile(traj[0], traj[1]));
    }
}
