package org.usfirst.frc.team6417.robot.commands.autonomous;

import org.usfirst.frc.team6417.robot.Robot;
import org.usfirst.frc.team6417.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnEncoder extends Command {

	private int frontRightInitialCount, rearRightInitialCount, frontLeftInitialCount, rearLeftInitialCount,
			frontRightTargetCount, rearRightTargetCount, frontLeftTargetCount, rearLeftTargetCount, encoderSteps;
	private double targetDegree, distance;
	private double speed = 0.75;
	private double turnRadius = RobotMap.ROBOT.DIAGONAL_DISTANCE_BETWEEN_WHEELS * RobotMap.MATH.PI;

	public TurnEncoder(double degree) {
		requires(Robot.drive);
		targetDegree = degree;
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

		// Reverses speed and degree robot is supposed to turn to the left so
		// into a negative angle
		if (targetDegree < 0) {
			speed = -speed;
			targetDegree = -targetDegree;
			frontRightInitialCount = -frontRightInitialCount;
			rearRightInitialCount = -rearRightInitialCount;
			frontLeftInitialCount = -frontLeftInitialCount;
			rearLeftInitialCount = -rearLeftInitialCount;
		}

		distance = turnRadius / (360 / targetDegree);

		// Checks if robot wants to drive forever
		if (distance == Double.MAX_VALUE || distance == -Double.MAX_VALUE) {
			encoderSteps = Integer.MAX_VALUE;
		}
		// Calculates encoder steps that have to be counted so robot can reach
		// desired distance
		else {
			encoderSteps = (int) ((((distance) / (RobotMap.ROBOT.WHEEL_CIRCUMFERENCE))
					* RobotMap.ENCODER.PULSE_PER_ROTATION));
		}

		// Calculates taretEnocderCount with initial offset calculated into it
		frontRightTargetCount = frontRightInitialCount + encoderSteps;
		rearRightTargetCount = rearRightInitialCount + encoderSteps;
		frontLeftTargetCount = frontLeftInitialCount + encoderSteps;
		rearLeftTargetCount = rearLeftInitialCount + encoderSteps;

		// Starts robot to let him drive into desired direction at desired speed
		Robot.drive.mecanumDrive_Autonomous(0, 0, speed);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.drive.mecanumDrive_Autonomous(0, 0, speed);
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
