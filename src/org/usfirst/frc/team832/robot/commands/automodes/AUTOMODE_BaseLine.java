package org.usfirst.frc.team832.robot.commands.automodes;

import org.usfirst.frc.team832.robot.commands.auto.AutoDriveDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AUTOMODE_BaseLine extends CommandGroup {

    public AUTOMODE_BaseLine() {
        addSequential(new AutoDriveDistance(0.5, 0.0, 9000, 0));

    }
}
