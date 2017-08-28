package org.usfirst.frc.team6417.robot.commands.autonomous;

import org.usfirst.frc.team6417.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Class responsible for turning a certain angle by reading NavX inputs
 */
public class TurnNavX extends Command {
	
	private double targetDegree;
	private double speed = 0.75;

    public TurnNavX(double degree) {
        requires(Robot.drive);
        requires(Robot.navX);
        targetDegree = degree;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.navX.reset();
    	//Reverses speed and degree robot is supposed to turn to the left so into a negative angle
    	if(targetDegree < 0){
			speed = -speed;
			targetDegree = -targetDegree;
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Tells robot to turn into desired direction with desired speed
    	Robot.drive.mecanumDrive_Autonomous(0, 0, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//Checks if robot has already reached target degree
    	if(Math.abs(Robot.navX.get().getYaw()) >= targetDegree){
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
