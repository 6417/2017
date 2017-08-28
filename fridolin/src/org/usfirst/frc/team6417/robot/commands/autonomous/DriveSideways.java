package org.usfirst.frc.team6417.robot.commands.autonomous;

import org.usfirst.frc.team6417.robot.Robot;
import org.usfirst.frc.team6417.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Keine Ahnung ob und wie gut funktionert, (geschrieben nach
 * Klassenuebernachtung um 3 Uhr frueh begonnen)
 */
public class DriveSideways extends Command {

	int frontRightInitialCount, rearRightInitialCount, frontLeftInitialCount, rearLeftInitialCount,
			frontRightTargetCount, rearRightTargetCount, frontLeftTargetCount, rearLeftTargetCount, encoderSteps;
	double distance;
	double speed = 0.5;
	double offset = (RobotMap.ENCODER.PULSE_PER_ROTATION / 2);

	public DriveSideways(double meters) {
		requires(Robot.drive);
		Robot.drive.resetRobot();
		distance = meters;
	}

	// Called just before this Command runs the first time
	protected void initialize() {

		frontRightInitialCount = Math.abs(Robot.drive.getFrontRightEncoder());
		rearRightInitialCount = Math.abs(Robot.drive.getRearRightEncoder());
		frontLeftInitialCount = Math.abs(Robot.drive.getFrontLeftEncoder());
		rearLeftInitialCount = Math.abs(Robot.drive.getRearLeftEncoder());
		if (distance < 0) {
			distance = -distance;
			speed = -speed;
			frontRightInitialCount = -frontRightInitialCount;
			rearRightInitialCount = -rearRightInitialCount;
			frontLeftInitialCount = -frontLeftInitialCount;
			rearLeftInitialCount = -rearLeftInitialCount;
		}
		if (distance == Integer.MAX_VALUE || distance == -Integer.MAX_VALUE) {
			encoderSteps = Integer.MAX_VALUE;
		} else {
			encoderSteps = (int) (((((distance * 100) / (RobotMap.ROBOT.WHEEL_CIRCUMFERENCE))
					* RobotMap.ENCODER.PULSE_PER_ROTATION)) * (1 / 0.86));
		}
		frontRightTargetCount = Math.abs(Robot.drive.getFrontRightEncoder()) + encoderSteps;
		rearRightTargetCount = Math.abs(Robot.drive.getRearRightEncoder()) + encoderSteps;
		frontLeftTargetCount = Math.abs(Robot.drive.getFrontLeftEncoder()) + encoderSteps;
		rearLeftTargetCount = Math.abs(Robot.drive.getRearLeftEncoder()) + encoderSteps;
		Robot.drive.mecanumDrive_Autonomous(speed, 0, 0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		Robot.drive.mecanumDrive_Autonomous(speed, 0, 0);

		// if ((Math.abs(Robot.drive.getFrontRightEncoder()) -
		// frontRightInitialCount
		// + Math.abs(Robot.drive.getFrontLeftEncoder())
		// - frontLeftInitialCount) >=
		// (Math.abs(Robot.drive.getRearRightEncoder()) - rearRightInitialCount
		// + Math.abs(Robot.drive.getRearLeftEncoder()) - rearLeftInitialCount
		// + offset)) {
		// Robot.drive.mecanumDrive_Autonomous(speed, 0, speed);
		// } else if ((Math.abs(Robot.drive.getRearRightEncoder()) -
		// rearRightInitialCount
		// + Math.abs(Robot.drive.getRearLeftEncoder())
		// - rearLeftInitialCount) >=
		// (Math.abs(Robot.drive.getFrontRightEncoder()) -
		// frontRightInitialCount
		// + Math.abs(Robot.drive.getFrontLeftEncoder()) - frontLeftInitialCount
		// + offset)) {
		// Robot.drive.mecanumDrive_Autonomous(speed, 0, -speed);
		// } else {
		// Robot.drive.mecanumDrive_Autonomous(speed, 0, 0);
		// }
		// }

		// if ((Math.abs(Robot.drive.getFrontRightEncoder()) -
		// frontRightInitialCount
		// + Math.abs(Robot.drive.getRearLeftEncoder())
		// - rearLeftInitialCount) >=
		// (Math.abs(Robot.drive.getRearRightEncoder()) - rearRightInitialCount
		// + Math.abs(Robot.drive.getFrontLeftEncoder()) - frontLeftInitialCount
		// + offset)) {
		// Robot.drive.mecanumDrive_Autonomous(speed, -speed, 0);
		// } else if ((Math.abs(Robot.drive.getRearRightEncoder()) -
		// rearRightInitialCount
		// + Math.abs(Robot.drive.getFrontLeftEncoder())
		// - frontLeftInitialCount) >=
		// (Math.abs(Robot.drive.getFrontRightEncoder()) -
		// frontRightInitialCount
		// + Math.abs(Robot.drive.getRearLeftEncoder()) - rearLeftInitialCount
		// + offset)) {
		// Robot.drive.mecanumDrive_Autonomous(speed, speed,0);
		// } else {
		// Robot.drive.mecanumDrive_Autonomous(speed, 0, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if (Math.abs(Robot.drive.getFrontRightEncoder()) + Math.abs(Robot.drive.getRearRightEncoder())
				+ Math.abs(Robot.drive.getFrontLeftEncoder()) + Math.abs(Robot.drive.getRearLeftEncoder()) >= Math.abs(
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
