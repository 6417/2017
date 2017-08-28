package org.usfirst.frc.team6417.robot.commands.autonomous;

import org.usfirst.frc.team6417.robot.Robot;
import org.usfirst.frc.team6417.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class responsible for letting the robot drive exactly straight for a desired
 * distance
 */
public class DriveStraight extends Command {

	private int frontRightInitialCount, rearRightInitialCount, frontLeftInitialCount, rearLeftInitialCount,
			frontRightTargetCount, rearRightTargetCount, frontLeftTargetCount, rearLeftTargetCount, encoderSteps;
	private double distance;
	private double speed = 0.5;
	private double offset = (RobotMap.ENCODER.PULSE_PER_ROTATION / 2);

	public DriveStraight(double meters) {
		requires(Robot.drive);
		Robot.drive.resetRobot();
		distance = meters;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Resets robot to zero
		Robot.drive.resetRobot();

		// Takes in initial encoder counts for the case that reset didin't work
		// properly
		frontRightInitialCount = Robot.drive.getFrontRightEncoder();
		rearRightInitialCount = Robot.drive.getRearRightEncoder();
		frontLeftInitialCount = Robot.drive.getFrontLeftEncoder();
		rearLeftInitialCount = Robot.drive.getRearLeftEncoder();

		// Reverses speed (driving direction of Robot) if robot should drive a
		// negative distance so backwards also reverses targetCount so nevative
		// distance can be reached
		if (distance < 0) {
			distance = -distance;
			speed = -speed;
			frontRightInitialCount = -frontRightInitialCount;
			rearRightInitialCount = -rearRightInitialCount;
			frontLeftInitialCount = -frontLeftInitialCount;
			rearLeftInitialCount = -rearLeftInitialCount;
		}

		// Checks if robot wants to drive forever
		if (distance == Double.MAX_VALUE || distance == -Double.MAX_VALUE) {
			encoderSteps = Integer.MAX_VALUE;
		}
		// Calculates encoder steps that have to be counted so robot can reach
		// desired distance
		else {
			encoderSteps = (int) ((((distance * 100) / (RobotMap.ROBOT.WHEEL_CIRCUMFERENCE))
					* RobotMap.ENCODER.PULSE_PER_ROTATION));
		}

		// Calculates taretEnocderCount with initial offset calculated into it
		frontRightTargetCount = frontRightInitialCount + encoderSteps;
		rearRightTargetCount = rearRightInitialCount + encoderSteps;
		frontLeftTargetCount = frontLeftInitialCount + encoderSteps;
		rearLeftTargetCount = rearLeftInitialCount + encoderSteps;

		// Starts robot to let him drive into desired direction at desired speed
		Robot.drive.mecanumDrive_Autonomous(0, speed, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		// Entire execute is there to correct possible offsets of sides

		// Checks if right side has driven further than left side by a defined
		// offset
		if (Math.abs(Robot.drive.getFrontRightEncoder() - frontRightInitialCount + Robot.drive.getRearRightEncoder()
				- rearRightInitialCount) >= Math
						.abs(Robot.drive.getFrontLeftEncoder() - frontLeftInitialCount
								+ Robot.drive.getRearLeftEncoder() - rearLeftInitialCount + offset)) {
			// For the case that one side has turned farther offset is corrected
			// here
			Robot.drive.mecanumDrive_Autonomous(0, speed, speed);
		}
		// Checks if left side has driven further than right side by a defined
		// offset
		else if (Math.abs(Robot.drive.getFrontLeftEncoder() - frontLeftInitialCount + Robot.drive.getRearLeftEncoder()
				- rearLeftInitialCount) >= Math
						.abs(Robot.drive.getFrontRightEncoder() - frontRightInitialCount
								+ Robot.drive.getRearRightEncoder() - rearRightInitialCount + offset)) {
			// For the case that one side has turned farther offset is corrected
			// here
			Robot.drive.mecanumDrive_Autonomous(0, speed, -speed);
		}
		// If robot is driving straight it tells him to continue to do so
		else {
			Robot.drive.mecanumDrive_Autonomous(0, speed, 0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		// Checks if encoders have already reached their desired target count
		if (Math.abs(Robot.drive.getFrontRightEncoder() + Robot.drive.getRearRightEncoder()
				+ Robot.drive.getFrontLeftEncoder() + Robot.drive.getRearLeftEncoder()) >= Math.abs(
						frontRightTargetCount + rearRightTargetCount + frontLeftTargetCount + rearLeftTargetCount)) {
			Robot.drive.stop();
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drive.stop();
	}
}
