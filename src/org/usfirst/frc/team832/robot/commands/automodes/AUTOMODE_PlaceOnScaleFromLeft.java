package org.usfirst.frc.team832.robot.commands.automodes;

import org.usfirst.frc.team832.robot.commands.auto.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AUTOMODE_PlaceOnScaleFromLeft extends CommandGroup {
	public String gameData = "";
	{
	gameData = DriverStation.getInstance().getGameSpecificMessage();
    if(gameData.length() > 0)
    {
	  //if(gameData.charAt(1) == 'L')
	  //{
		//Put left auto code here
		  addSequential(new AutoDriveDistance(0.5, 0.0, 12000, 0));
		  //addSequential(new AutoMoveElevatorStage2(25000));
		  //addSequential(new AutoMoveIntakeElbow(0));
		  //addSequential(new AutoMoveElevatorStage1(500));
		  addSequential(new AutoDriveDistance(0.25, 0.0, 500, 45));
		  //addSequential(new AutoIntakeLinear(.75));
//	  } 
//	  else {
//		//Put right auto code here
//		  addSequential(new AutoDriveDistance(0.5, 0.0, 2000, 0));
//		  addSequential(new AutoDriveDistance(0.5, 0.0, 0, 90));
//		  addSequential(new AutoMoveElevatorStage2(1000));
//		  addSequential(new AutoDriveDistance(0.5, 0.0, 1850, 0));
//		  addSequential(new AutoMoveElevatorStage1(500));
//		  addSequential(new AutoDriveDistance(0.5, 0.0, 0, 90));
//		  addSequential(new AutoMoveIntakeElbow(0));
//		  addSequential(new AutoDriveDistance(0.5, 0.0, 300, 0));
//		  addSequential(new AutoIntakeLinear(.75));
//	  }
    }
	}
}
