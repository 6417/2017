package org.usfirst.frc.team6417.robot.commands.group;

import org.usfirst.frc.team6417.robot.commands.autonomous.DriveStraight;
import org.usfirst.frc.team6417.robot.commands.test.TestGearController;
import org.usfirst.frc.team6417.robot.commands.test.TestWheels;
import org.usfirst.frc.team6417.robot.commands.test.TestWinchController;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TestMode extends CommandGroup {

    public TestMode() {
    	addSequential(new TestWheels());
    	addSequential(new TestGearController());
    	addSequential(new TestWinchController(0.3));
    	addSequential(new TestWinchController(-0.3));
    }
}
