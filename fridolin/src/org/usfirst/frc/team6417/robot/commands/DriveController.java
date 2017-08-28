package org.usfirst.frc.team6417.robot.commands;

import org.usfirst.frc.team6417.robot.Robot;
import org.usfirst.frc.team6417.robot.RobotMap;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveController extends Command {

	private Controller controllerType;
	private DriveMode driveMode;

	public static enum Controller {
		XBOX, JOYSTICK
	}

	public static enum DriveMode {
		DEFAULT, CONTROLLER, TANK
	}

	/**
	 * Enables you to drive the robot with either controller, arcade or tank
	 * controls.
	 * 
	 * @param controllerType
	 *            enter Joystick or XBox to choose your type of contoller that
	 *            you're using
	 * @param driveMode
	 *            enter controller, arcade oder tank depending on the way how
	 *            you want to stear your robot
	 */
	public DriveController(DriveMode driveMode) {

		// detecting connected controller
		setControllerType(Controller.JOYSTICK);
		if (Robot.joystickOne.getIsXbox()) {
			setControllerType(Controller.XBOX);
		}

		// check if driveMode is possible with connected joysticks
		setDriveMode(driveMode);

		System.out.format("DriveController initialized with Controller \"%s\" and mode \"%s\".\n", controllerType,
				driveMode);

		requires(Robot.drive);
	}

	public DriveController setControllerType(Controller c) {
		this.controllerType = c;
		return this;
	}

	public DriveController setDriveMode(DriveMode d) {
		// TODO check for more incompatible controllers
		switch (d) {
		// Tank mode is only supported with 2 joysticks
		case TANK:
			if (controllerType == Controller.JOYSTICK && isJoystickConnected(RobotMap.CONTROLLER.LEFT)
					&& Robot.joystickTwo.getIsXbox()) {
				DriverStation.reportWarning(
						"Tank drive not supported with Joystick and XBOX controller. Falling back to default drive mode.",
						false);
				this.driveMode = DriveMode.DEFAULT;
				break;
			}
		default:
			this.driveMode = d;
		}
		return this;
	}

	private boolean isJoystickConnected(int stick) {
		return (DriverStation.getInstance().getJoystickType(stick) == -1) ? false : true;
	}

	/**
	 * calculates the output of the controller with a deadzone. The output is
	 * linearized to start from 0 when deadzone is reached.
	 * 
	 * 1 deadzone output = ------------ * magnitude - ------------ 1 - deadzone
	 * 1 - deadzone
	 * 
	 * @param magnitude
	 * @param deadzone
	 * @return
	 */
	protected double deadzone(double magnitude, double deadzone) {
		return (Math.abs(magnitude) < deadzone) ? 0 : magnitude / (1 - deadzone) - deadzone / (1 - deadzone);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		// TODO Joystick has a function joystickOne.isXbox() for Xbox controller
		// detection! could be used?
		switch (controllerType) {
		case XBOX:

			switch (driveMode) {
			case DEFAULT:
			case CONTROLLER:
				// CHECKME
				Robot.drive.mecanumDrive_Controller(
						deadzone(
								Robot.joystickOne.getRawAxis(RobotMap.XBOX.AXIS.LEFT_X),
								RobotMap.XBOX.DEADZONES.XBOX_LEFT_X
								),
						deadzone(
								Robot.joystickOne.getRawAxis(RobotMap.XBOX.AXIS.LEFT_Y),
								RobotMap.XBOX.DEADZONES.XBOX_LEFT_Y
								),
						deadzone(
								Robot.joystickOne.getRawAxis(RobotMap.XBOX.AXIS.RIGHT_X),
								RobotMap.XBOX.DEADZONES.XBOX_RIGHT_X
								)
						);
				break;
			case TANK:
				// CHECKME
				Robot.drive.mecanumDrive_Tank(
						deadzone(
								Robot.joystickOne.getRawAxis(RobotMap.XBOX.AXIS.RIGHT_X),
								RobotMap.XBOX.DEADZONES.XBOX_RIGHT_X
								),
						deadzone(
								Robot.joystickOne.getRawAxis(RobotMap.XBOX.AXIS.RIGHT_Y),
								RobotMap.XBOX.DEADZONES.XBOX_RIGHT_Y
								),
						deadzone(
								Robot.joystickOne.getRawAxis(RobotMap.XBOX.AXIS.LEFT_X),
								RobotMap.XBOX.DEADZONES.XBOX_LEFT_X
								),
						deadzone(
							Robot.joystickOne.getRawAxis(RobotMap.XBOX.AXIS.LEFT_Y),
							RobotMap.XBOX.DEADZONES.XBOX_LEFT_Y
								)
						);
				break;
			}
			break;

		case JOYSTICK:
			switch (this.driveMode) {
			case DEFAULT:
				Robot.drive.mecanumDrive_Default(
						deadzone(
								Robot.joystickOne.getX(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK1_X
								), 
						deadzone(
								Robot.joystickOne.getY(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK1_Y
								),
						deadzone(
								Robot.joystickOne.getTwist(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK1_TWIST
								)
						);
				break;
			case CONTROLLER:
				// CHECKME
				Robot.drive.mecanumDrive_Controller(
						deadzone(
								Robot.joystickTwo.getX(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK2_X
								),
						deadzone(
								Robot.joystickTwo.getY(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK2_Y
								),
						deadzone(
								Robot.joystickOne.getX(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK1_X
								)
						);
				break;
			case TANK:
				// CHECKME
				Robot.drive.mecanumDrive_Tank(
						deadzone(
								Robot.joystickOne.getX(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK1_X
								),
						deadzone(
								Robot.joystickOne.getY(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK1_Y
								),
						deadzone(
								Robot.joystickTwo.getX(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK2_X
								),
						deadzone(
								Robot.joystickTwo.getY(),
								RobotMap.JOYSTICK.DEADZONES.JOYSTICK2_Y
								)
						);
				break;
			}
			break;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.resetRobot();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drive.resetRobot();
	}
}
