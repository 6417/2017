package org.usfirst.frc.team6417.robot.commands.group;

import org.usfirst.frc.team6417.robot.commands.autonomous.DriveStraight;
import org.usfirst.frc.team6417.robot.commands.autonomous.TurnNavX;
import org.usfirst.frc.team6417.robot.commands.autonomous.VisionController;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class RightAutonomous extends CommandGroup {

    public RightAutonomous() {
    	addSequential(new DriveStraight(1.97));
    	addSequential(new TurnNavX(-30));
    	addSequential(new VisionController());
    	addSequential(new DriveStraight(0.4));
    }
}
