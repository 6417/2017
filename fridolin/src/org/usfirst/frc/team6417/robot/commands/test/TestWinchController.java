package org.usfirst.frc.team6417.robot.commands.test;

import java.util.Date;

import org.usfirst.frc.team6417.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestWinchController extends Command {

	private Date time;
	private double speed;

	public TestWinchController(double speed) {
		this.speed = speed;
		requires(Robot.winch);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.winch.setWinchSpeed(speed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (time == null) {
			time = new Date();
		} else if (time.getTime() + 3000 < new Date().getTime()) {
			time = null;
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.winch.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.winch.stop();
	}
}
