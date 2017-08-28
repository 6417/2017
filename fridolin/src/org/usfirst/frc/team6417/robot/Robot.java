
package org.usfirst.frc.team6417.robot;

import java.io.IOException;

import org.usfirst.frc.team6417.robot.commands.DriveController;
import org.usfirst.frc.team6417.robot.commands.DriveController.DriveMode;
import org.usfirst.frc.team6417.robot.commands.GearController;
import org.usfirst.frc.team6417.robot.commands.WinchController;
import org.usfirst.frc.team6417.robot.commands.autonomous.VisionController;
import org.usfirst.frc.team6417.robot.commands.group.LeftAutonomous;
import org.usfirst.frc.team6417.robot.commands.group.MiddleAutonomous;
import org.usfirst.frc.team6417.robot.commands.group.RightAutonomous;
import org.usfirst.frc.team6417.robot.commands.group.TestMode;
import org.usfirst.frc.team6417.robot.subsystems.AxisCameraSettings;
import org.usfirst.frc.team6417.robot.subsystems.Drive;
import org.usfirst.frc.team6417.robot.subsystems.Gear;
import org.usfirst.frc.team6417.robot.subsystems.NavX;
import org.usfirst.frc.team6417.robot.subsystems.Vision;
import org.usfirst.frc.team6417.robot.subsystems.Winch;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {

	// Controllers
	public static Joystick joystickOne;
	public static Joystick joystickTwo;
	public static JoystickButton turnGearXbox, turnGearJoystick, stopGearXbox, stopGearJoystick;
	public static OI oi;

	// Subsystems
	public static Drive drive;
	public static Winch winch;
	public static NavX navX;
	public static Vision vision;
	public static Gear gear;
	public static AxisCameraSettings axis;

	// Commands
	public static DriveController driveController;
	public static WinchController winchController;
	public static GearController gearController;
	// Autonomous
	public static VisionController visionController;

	DriveMode driveMode;
	SendableChooser<DriveMode> driveModeChooser = new SendableChooser<DriveMode>();

	Command autonomousMode;
	SendableChooser<Command> autonomousModeChooser = new SendableChooser<Command>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		joystickOne = new Joystick(RobotMap.CONTROLLER.RIGHT);
		joystickTwo = new Joystick(RobotMap.CONTROLLER.LEFT);

		turnGearXbox = new JoystickButton(joystickOne, RobotMap.XBOX.BUTTONS.RIGHT_BUTTON);
		turnGearJoystick = new JoystickButton(joystickOne, RobotMap.JOYSTICK.BUTTONS.TRIGGER);

		navX = new NavX();
		drive = new Drive();
		axis = new AxisCameraSettings();
		vision = new Vision();
		NetworkTable.getTable("Vision").putBoolean("process", true);
		winch = new Winch();
		gear = new Gear();
		oi = new OI();

		autonomousModeChooser.addDefault("Middle", new MiddleAutonomous());
		autonomousModeChooser.addObject("Left", new LeftAutonomous());
		autonomousModeChooser.addObject("Right", new RightAutonomous());
		autonomousModeChooser.addObject("Test", new TestMode());
		SmartDashboard.putData("Autonomous Mode", autonomousModeChooser);

		driveModeChooser.addDefault("Default", DriveMode.DEFAULT);
		driveModeChooser.addObject("Tank", DriveMode.TANK);
		driveModeChooser.addObject("Controller", DriveMode.CONTROLLER);
		SmartDashboard.putData("Drive Mode", driveModeChooser);

		drive.resetRobot();
	}

	/**
	 * 
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		drive.resetRobot();
		NetworkTable.getTable("Vision").putBoolean("process", false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {

		try {
			axis.setExposure(0);
			axis.setBrightness(0);
			System.out.println("Made camera dark");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't make camera dark");
		}

		autonomousMode = (Command) autonomousModeChooser.getSelected();
		if (autonomousMode != null) {
			autonomousMode.start();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousMode != null) {
			autonomousMode.cancel();
		}

		try {
			axis.setExposure(50);
			axis.setBrightness(50);
			System.out.println("Made camera bright");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Couldn't make camera bright");
		}

		NetworkTable.getTable("Vision").putBoolean("process", false);

		driveMode = (DriveMode) driveModeChooser.getSelected();
		if (driveMode == null) {
			driveMode = DriveMode.DEFAULT;
		}
		driveController = new DriveController(driveMode);
		driveController.start();
		gearController = new GearController();
		gearController.start();
		winchController = new WinchController();
		winchController.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void robotPeriodic() {
		SmartDashboard.putNumber("Front Right", drive.getFrontRightEncoder());
		SmartDashboard.putNumber("Rear Right", drive.getRearRightEncoder());
		SmartDashboard.putNumber("Front Left", drive.getFrontLeftEncoder());
		SmartDashboard.putNumber("Rear Left", drive.getRearLeftEncoder());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
