package org.usfirst.frc.team6417.robot.commands.test;

import java.util.Date;

import org.usfirst.frc.team6417.robot.Robot;
import org.usfirst.frc.team6417.robot.commands.GearController;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestGearController extends Command {

	private Date time;
	private GearController gear;

	public TestGearController() {
		time = new Date();
		gear = new GearController();
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		gear.start();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (time.getTime() + 8000 < new Date().getTime()) {
			gear.cancel();
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.gear.stopGear();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.gear.stopGear();
	}
}
