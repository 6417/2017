package org.usfirst.frc.team6417.robot.commands;

import org.usfirst.frc.team6417.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearController extends Command {

	private Controller controllerType;

	public static enum Controller {
		XBOX, JOYSTICK
	}

	public GearController() {
		setControllerType(Controller.JOYSTICK);
		if (Robot.joystickOne.getIsXbox()) {
			setControllerType(Controller.XBOX);
		}

		requires(Robot.gear);
	}

	public GearController setControllerType(Controller c) {
		this.controllerType = c;
		return this;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		switch (controllerType) {
		case XBOX:
			if (Robot.turnGearXbox.get()) {
				Robot.gear.turnGear();
			}
			break;

		case JOYSTICK:
			if (Robot.turnGearJoystick.get()) {
				Robot.gear.turnGear();
			}
			break;
		}
		Robot.gear.manageGear();
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("GearController stopped");

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
