package org.usfirst.frc.team6417.robot.subsystems;

import org.usfirst.frc.team6417.robot.Fridolin;
import org.usfirst.frc.team6417.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem responsible for controlling the winch
 */
public class Winch extends Subsystem {

	private Fridolin winch;

	public Winch() {
		winch = new Fridolin(RobotMap.MOTOR.WINCH);
	}

	public Fridolin getWinch() {
		return winch;
	}

	public void setWinchSpeed(double speed) {
		winch.set(speed);
	}

	public void stop() {
		winch.stopMotor();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
