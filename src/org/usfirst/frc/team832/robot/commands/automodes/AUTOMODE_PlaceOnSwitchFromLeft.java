package org.usfirst.frc.team832.robot.commands.automodes;

import org.usfirst.frc.team832.robot.commands.auto.AutoDriveDistance;
import org.usfirst.frc.team832.robot.commands.auto.AutoIntakeLinear;
import org.usfirst.frc.team832.robot.commands.auto.AutoMoveElevatorStage2;
import org.usfirst.frc.team832.robot.commands.auto.AutoMoveIntakeElbow;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AUTOMODE_PlaceOnSwitchFromLeft extends CommandGroup {
	public String gameData = "";
	{
	gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
	  if(gameData.charAt(0) == 'R')
	  {
		//Put left auto code here
		  addSequential(new AutoDriveDistance(0.5, 0.0, 2000, 0));
		  addSequential(new AutoDriveDistance(0.5, 0.0, 0, 90));
		  addSequential(new AutoMoveElevatorStage2(250));
		  addSequential(new AutoDriveDistance(0.5, 0.0, 1850, 0));
		  addSequential(new AutoDriveDistance(0.5, 0.0, 0, 90));
		  addSequential(new AutoMoveIntakeElbow(0));
		  addSequential(new AutoDriveDistance(0.5, 0.0, 100, 0));
		  addSequential(new AutoIntakeLinear(.75));
	  } 
	  else {
		//Put right auto code here
		  addSequential(new AutoDriveDistance(0.5, 0.0, 2000, 0));
		  addSequential(new AutoDriveDistance(0.5, 0.0, 0, 90));
		  addSequential(new AutoMoveElevatorStage2(250));
		  addSequential(new AutoMoveIntakeElbow(0));
		  addSequential(new AutoDriveDistance(0.5, 0.0, 500, 0));
		  addSequential(new AutoIntakeLinear(.75));	  
	  }
    }
	}
}
