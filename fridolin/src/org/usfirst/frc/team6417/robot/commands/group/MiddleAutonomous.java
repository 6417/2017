package org.usfirst.frc.team6417.robot.commands.group;

import org.usfirst.frc.team6417.robot.commands.autonomous.DriveStraight;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class MiddleAutonomous extends CommandGroup {

    public MiddleAutonomous() {
    	addSequential(new DriveStraight(1.95));
   
    }
}
