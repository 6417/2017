package org.usfirst.frc.team6417.robot.commands.autonomous;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import org.usfirst.frc.team6417.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionController extends Command {
	private double speed = 0.3;
	private double stripeTargetArea;
	private boolean dontStart;
	private double areaOffset = 1.5;
	private double distanceOffset = 5;
	private double distanceLeft, distanceRight;
	private Date time;
	private boolean firstTimeDriveForward;
	private double sizeSteps;
	private double partialTargetArea;

	public VisionController() {
		requires(Robot.drive);
		requires(Robot.vision);
		requires(Robot.axis);
		stripeTargetArea = 150;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		if (Robot.vision.amountOfStripes() == 0 || Robot.vision.amountOfStripes() == 1
				|| Robot.vision.amountOfStripes() == 10) {
			dontStart = true;
			isFinished();
		} else {
			dontStart = false;
		}
		partialTargetArea = 0;
		firstTimeDriveForward = true;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (Robot.vision.amountOfStripes() == 2) {
			
			if (Robot.vision.leftStripeArea() - Robot.vision.rightStripeArea() > areaOffset) {
				while(Robot.vision.leftStripeArea() - Robot.vision.rightStripeArea() > areaOffset){
					System.out.println("Driving Right");
					Robot.drive.right(speed);
					
				}
			}
			else if (Robot.vision.leftStripeArea() - Robot.vision.rightStripeArea() < areaOffset) {
				while(Robot.vision.leftStripeArea() - Robot.vision.rightStripeArea() < areaOffset){
					System.out.println("Driving Left");
					Robot.drive.left(speed);
					
				}
				
			}
		
			Robot.drive.stop();

//			if (Robot.vision.leftStripeArea() - Robot.vision.rightStripeArea() < areaOffset
//					&& Robot.vision.leftStripeArea() - Robot.vision.rightStripeArea() > areaOffset) {
				if (Math.abs(Robot.vision.leftDistanceToMiddle() - Robot.vision.rightDistanceToMiddle()) > distanceOffset) {
					while(Math.abs(Robot.vision.leftDistanceToMiddle() - Robot.vision.rightDistanceToMiddle()) > distanceOffset) {
						if (Robot.vision.leftDistanceToMiddle() < Robot.vision.rightDistanceToMiddle()) {
						System.out.println("Turning Left");
						Robot.drive.turnLeft(speed);
						
					} else if (Robot.vision.leftDistanceToMiddle() > Robot.vision.rightDistanceToMiddle()) {
						System.out.println("Turning Right");
						Robot.drive.turnRight(speed);
						
					}
					}
					
//				}
				}
				Robot.drive.stop();
				
				if (Robot.vision.leftStripeArea() < stripeTargetArea
				&& Robot.vision.rightStripeArea() < stripeTargetArea) {
					if(firstTimeDriveForward){
						sizeSteps = (stripeTargetArea - ((Robot.vision.leftStripeArea() + Robot.vision.rightStripeArea())/2));
						firstTimeDriveForward = false;
					}
					if(partialTargetArea < stripeTargetArea){
					partialTargetArea = partialTargetArea + sizeSteps;}
					while(Robot.vision.leftStripeArea() < stripeTargetArea
				&& Robot.vision.rightStripeArea() < stripeTargetArea && Robot.vision.amountOfStripes() == 2){
						System.out.println("driving forward"); 
						Robot.drive.forward(speed);
					}
					
					
//					time = new Date();
//					while(time.getTime() + 10000 < new Date().getTime()) {
//						System.out.println("driving forward"); 
//						Robot.drive.forward(speed);
//						
//					}
//					time = null;
					
			} else if (Robot.vision.leftStripeArea() > stripeTargetArea
				&& Robot.vision.rightStripeArea() > stripeTargetArea) {
				while(Robot.vision.leftStripeArea() > stripeTargetArea
						&& Robot.vision.rightStripeArea() > stripeTargetArea){
					System.out.println("Driving Backward");
					Robot.drive.backward(speed);
					dontStart = true;
					isFinished();
				}
			} 
		}

		else if (Robot.vision.amountOfStripes() == 1)

		{
			Robot.drive.backward(speed);
		}

//		else if (Robot.vision.amountOfStripes() == 0) {
//			dontStart = true;
//			isFinished();
//		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		 if (dontStart) {
			 System.out.println("Stopped");
		 return true;
		 }
		// distanceLeft = Robot.vision.leftDistanceToMiddle();
		// distanceRight = Robot.vision.rightDistanceToMiddle();
		// if (Math.abs(distanceLeft - distanceRight) < distanceOffset) {
		// Robot.drive.stop();
		// System.out.println("done " + distanceLeft + " " + distanceRight);
		// return true;
		// }

		if (Robot.vision.leftStripeArea() - stripeTargetArea > areaOffset
				&& Robot.vision.rightStripeArea() - stripeTargetArea > areaOffset
				&& Math.abs(Robot.vision.leftDistanceToMiddle() - Robot.vision.rightDistanceToMiddle()) < distanceOffset) {
			Robot.drive.stop();
			System.out.println("FUCK YEAH");
			return true;
		}
		// if (Robot.vision.leftStripeArea() == Robot.vision.rightStripeArea())
		// {
		// Robot.drive.stop();
		// System.out.println("Done");
		// return true;
		// }
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.drive.stop();
//		try {
//			Robot.axis.setExposure(50);
//			Robot.axis.setBrightness(50);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.drive.stop();
	}
}
