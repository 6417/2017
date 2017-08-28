package org.usfirst.frc.team6417.robot.subsystems;

import java.util.Date;

import org.usfirst.frc.team6417.robot.Fridolin;
import org.usfirst.frc.team6417.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Gear extends Subsystem {

	private Fridolin gear;
	private DigitalInput lightSensor, inductor;
	private double speed = 1;
	private Date time;
	private boolean inductorWorks;

	public Gear() {
		gear = new Fridolin(RobotMap.MOTOR.GEAR);
		lightSensor = new DigitalInput(RobotMap.GEAR.lIGHT_SENSOR);
		inductor = new DigitalInput(RobotMap.GEAR.INDUCTOR);
		inductorWorks = true;
	}

	public Fridolin getGear() {
		return gear;
	}

	public void moveGear() {
		gear.set(speed);
	}

	public void stopGear() {
		gear.stopMotor();
	}

	public DigitalInput getLightSensor() {
		return lightSensor;
	}

	public DigitalInput getInductor() {
		return inductor;
	}

	/**
	 * Turns the gear
	 */
	public void turnGear() {
		System.out.println("turnGear");
		Date timeout = new Date();
		while (inductor.get() && inductorWorks) {
			if (timeout.getTime() + 2000 < new Date().getTime()) {
				inductorWorks = false;
			}
			moveGear();
		}
		while (!inductor.get() && inductorWorks) {
			if (timeout.getTime() + 3000 < new Date().getTime()) {
				inductorWorks = false;
			}
			moveGear();
		}
		stopGear();
	}

	public void manageGear() {
		if (getLightSensor().get()) {
			if (time == null) {
				turnGear();
				time = new Date();
			} else if (time.getTime() + 1000 < new Date().getTime()) {
				time = null;
			}
		}
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
