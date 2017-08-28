package org.usfirst.frc.team6417.robot.subsystems;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 *
 */
public class Vision extends Subsystem {

	private NetworkTable table;
	private CameraServer server;

	public Vision() {
		table = NetworkTable.getTable("Vision");
		server = CameraServer.getInstance();
		server.addAxisCamera("10.64.17.6");
	}

	public void printTable() {
		System.out.println("Left Stripe: " + leftStripeArea() + ", " + leftDistanceToMiddle());
		System.out.println("Right Stripe: " + rightStripeArea() + ", " + rightDistanceToMiddle());
		System.out.println("Amount of Stripes:" + amountOfStripes());
		System.out.println("");
	}

	public NetworkTable getTable() {
		return table;
	}

	public double rightStripeArea() {
		// return 0;
		return table.getNumber("right_stripe_area", 0);
	}

	public double leftStripeArea() {
		// return 0;
		return table.getNumber("left_stripe_area", 0);
	}

	public double rightDistanceToMiddle() {
		// return 0;
		return table.getNumber("right_distance_to_middle", 0);
	}

	public double leftDistanceToMiddle() {
		// return 0;
		return table.getNumber("left_distance_to_middle", 0);
	}

	public double amountOfStripes() {
		return table.getNumber("amount_of_stripes", 10);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
