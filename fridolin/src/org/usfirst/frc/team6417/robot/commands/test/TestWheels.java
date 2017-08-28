package org.usfirst.frc.team6417.robot.commands.test;

import java.util.Date;

import org.usfirst.frc.team6417.robot.Robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Test program to see if all wheels and encoders are turning and working
 */
public class TestWheels extends Command {

	private int frontRightInitialCount, rearRightInitialCount, frontLeftInitialCount, rearLeftInitialCount;
	private Date timeout;
	private double speed;
	private double deviation = 0.1;

	public TestWheels() {
		requires(Robot.drive);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Resets robot to zero
		Robot.drive.resetRobot();

		// Takes in initial encoder counts for the case that reset didn't work
		// properly
		frontRightInitialCount = Robot.drive.getFrontRightEncoder();
		rearRightInitialCount = Robot.drive.getRearRightEncoder();
		frontLeftInitialCount = Robot.drive.getFrontLeftEncoder();
		rearLeftInitialCount = Robot.drive.getRearLeftEncoder();
		Robot.drive.mecanumDrive_Autonomous(0, speed, 0);
		timeout = new Date();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.mecanumDrive_Autonomous(0, speed, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (timeout.getTime() + 3000 < new Date().getTime()) {
			Robot.drive.stop();
			String[] encoderLegend = new String[4];
			encoderLegend[0] = "Front Right";
			encoderLegend[1] = "Rear Right";
			encoderLegend[2] = "Front Left";
			encoderLegend[3] = "Rear Left";

			int[] values = new int[4];
			values[0] = Robot.drive.getFrontRightEncoder() - frontRightInitialCount;
			values[1] = Robot.drive.getRearRightEncoder() - rearRightInitialCount;
			values[2] = Robot.drive.getFrontLeftEncoder() - frontLeftInitialCount;
			values[3] = Robot.drive.getRearLeftEncoder() - rearLeftInitialCount;

			double value;

			for (int i = 1; i < values.length; i++) {
				if ((value = Math.abs((values[i] / values[0]) - 1)) > deviation) {
					DriverStation.reportError("Encoder " + encoderLegend[i] + " has the set deviation (" + value + " > "
							+ deviation + ") to encoder " + encoderLegend[0], false);
				}
			}

			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
