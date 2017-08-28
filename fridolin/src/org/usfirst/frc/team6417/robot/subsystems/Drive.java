package org.usfirst.frc.team6417.robot.subsystems;

import org.usfirst.frc.team6417.robot.Fridolin;
import org.usfirst.frc.team6417.robot.RobotMap;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem responsible for driving the robot
 */
public class Drive extends Subsystem {

	private Fridolin motorFrontRight, motorRearRight, motorFrontLeft, motorRearLeft;
	private RobotDrive robotDrive;
	private Encoder encoderFrontRight, encoderRearRight, encoderFrontLeft, encoderRearLeft;

	/**
	 * Enables all Drive class features, like access to motor control or to
	 * encoders
	 */
	public Drive() {
		motorFrontRight = new Fridolin(RobotMap.MOTOR.FRONT_RIGHT);
		motorRearRight = new Fridolin(RobotMap.MOTOR.REAR_RIGHT);
		motorFrontLeft = new Fridolin(RobotMap.MOTOR.FRONT_LEFT);
		motorRearLeft = new Fridolin(RobotMap.MOTOR.REAR_LEFT);
		
		robotDrive = new RobotDrive(motorFrontLeft, motorRearLeft, motorFrontRight, motorRearRight);

		encoderFrontRight = new Encoder(RobotMap.ENCODER.FRONT_RIGHT_A, RobotMap.ENCODER.FRONT_RIGHT_B);
		encoderRearRight = new Encoder(RobotMap.ENCODER.REAR_RIGHT_A, RobotMap.ENCODER.REAR_RIGHT_B);
		encoderFrontLeft = new Encoder(RobotMap.ENCODER.FRONT_LEFT_A, RobotMap.ENCODER.FRONT_LEFT_B);
		encoderRearLeft = new Encoder(RobotMap.ENCODER.REAR_LEFT_A, RobotMap.ENCODER.REAR_LEFT_B);

		initializeEncoders();
	}

	/**
	 * Put initialize code for the encoders in this method.
	 */
	private void initializeEncoders() {
		encoderFrontRight.setDistancePerPulse(RobotMap.ROBOT.DIST_PER_PULSE);
		encoderFrontRight.setReverseDirection(true);
		encoderRearRight.setDistancePerPulse(RobotMap.ROBOT.DIST_PER_PULSE);
		encoderRearRight.setReverseDirection(true);
		encoderFrontLeft.setDistancePerPulse(RobotMap.ROBOT.DIST_PER_PULSE);
		encoderRearLeft.setDistancePerPulse(RobotMap.ROBOT.DIST_PER_PULSE);
	}

	/**
	 * Returns the current count from the Encoder
	 */
	public int getFrontLeftEncoder() {
		return encoderFrontLeft.get();
	}

	/**
	 * Returns the current count from the Encoder
	 */
	public int getRearLeftEncoder() {
		return encoderRearLeft.get();
	}

	/**
	 * Returns the current count from the Encoder
	 */
	public int getFrontRightEncoder() {
		return encoderFrontRight.get();
	}

	/**
	 * Returns the current count from the Encoder
	 */
	public int getRearRightEncoder() {
		return encoderRearRight.get();
	}

	/**
	 * Returns the distance driven since the last reset in centimeters
	 */
	public double getFrontLeftDistance() {
		return encoderFrontLeft.getDistance();
	}

	/**
	 * Returns the distance driven since the last reset in centimeters
	 */
	public double getRearLeftDistance() {
		return encoderRearLeft.getDistance();
	}

	/**
	 * Returns the distance driven since the last reset in centimeters
	 */
	public double getFrontRightDistance() {
		return encoderFrontRight.getDistance();
	}

	/**
	 * Returns the distance driven since the last reset in centimeters
	 */
	public double getRearRightDistance() {
		return encoderRearRight.getDistance();
	}

	/**
	 * Resets the Encoders distance to zero. Resets the current count to zero on
	 * the encoders.
	 */
	public void resetEncoders() {
		encoderFrontRight.reset();
		encoderRearRight.reset();
		encoderFrontLeft.reset();
		encoderRearLeft.reset();
	}

	/**
	 * Access Motor
	 * 
	 * @return motorFrontRight
	 */
	public Fridolin getMotorFrontRight() {
		return motorFrontRight;
	}

	/**
	 * Access Motor
	 * 
	 * @return motorRearRight
	 */
	public Fridolin getMotorRearRight() {
		return motorRearRight;
	}

	/**
	 * Access Motor
	 * 
	 * @return motorFrontLeft
	 */
	public Fridolin getMotorFrontLeft() {
		return motorFrontLeft;
	}

	/**
	 * Access Motor
	 * 
	 * @return motorRearLeft
	 */
	public Fridolin getMotorRearLeft() {
		return motorRearLeft;
	}

	/**
	 * Set motorFrontRight speed
	 * 
	 * @param speed
	 */
	public void setMotorFrontRight(double speed) {
		motorFrontRight.set(speed);
	}

	/**
	 * Set motorRearRight speed
	 * 
	 * @param speed
	 */
	public void setMotorRearRight(double speed) {
		motorRearRight.set(speed);
	}

	/**
	 * Set motorFrontLeft speed
	 * 
	 * @param speed
	 */
	public void setMotorFrontLeft(double speed) {
		motorFrontLeft.set(speed);
	}

	/**
	 * Set motorRearLeft speed
	 * 
	 * @param speed
	 */
	public void setMotorRearLeft(double speed) {
		motorRearLeft.set(speed);
	}

	/**
	 * Drive method for Mecanum wheeled robots.
	 *
	 * <p>
	 * A method for driving with Mecanum wheeled robots. There are 4 wheels on
	 * the robot, arranged so that the front and back wheels are toed in 45
	 * degrees. When looking at the wheels from the top, the roller axles should
	 * form an X across the robot.
	 *
	 * <p>
	 * This is designed to be directly driven by joystick axes.
	 *
	 * @param x
	 *            The speed that the robot should drive in the X direction.
	 *            [-1.0..1.0]
	 * @param y
	 *            The speed that the robot should drive in the Y direction. This
	 *            input is inverted to match the forward == -1.0 that joysticks
	 *            produce. [-1.0..1.0]
	 * @param rotation
	 *            The rate of rotation for the robot that is completely
	 *            independent of the translation. [-1.0..1.0]
	 */
	public void mecanumDrive_Default(double x, double y, double rotation) {
		robotDrive.mecanumDrive_Cartesian(-x, y, rotation, 0);
	}

	/**
	 * Drive method for Mecanum wheeled robots. With two joysticks driven like a
	 * tank
	 * 
	 * @param xRightJoystick
	 * @param yRightJoystick
	 * @param xLeftJoystick
	 * @param yLeftJoystick
	 */
	public void mecanumDrive_Tank(double xRightJoystick, double yRightJoystick, double xLeftJoystick,
			double yLeftJoystick) {
		mecanumDrive_Default((xRightJoystick + xLeftJoystick) / 2, (yRightJoystick + yLeftJoystick) / 2,
				(yRightJoystick - yLeftJoystick) / 2);
	}

	/**
	 * Drive method for Mecanum wheeled robots. With a controller.
	 * 
	 * @param xRightThumbstick
	 * @param yRightThumbstick
	 * @param yLeftThumbstick
	 */
	public void mecanumDrive_Controller(double xLeftThumbstick, double yLeftThumbstick, double xRightThumbstick) {
		mecanumDrive_Default(xLeftThumbstick, yLeftThumbstick, xRightThumbstick);
	}
	
	public void mecanumDrive_Autonomous(double x, double y, double rotation){
		mecanumDrive_Default(x, -y, rotation);
	}
	
	public void forward(double speed){
		mecanumDrive_Autonomous(0,speed,0);
	}
	
	public void backward(double speed){
		mecanumDrive_Autonomous(0, -speed, 0);
	}
	
	public void right(double speed){
		mecanumDrive_Autonomous(speed, 0, 0);
	}
	
	public void left(double speed){
		mecanumDrive_Autonomous(-speed, 0, 0);
	}
	
	public void turnRight(double speed){
		mecanumDrive_Autonomous(0, 0, speed);
	}
	
	public void turnLeft(double speed){
		mecanumDrive_Autonomous(0, 0, -speed);
	}

	/**
	 * Stops robot form driving
	 */
	public void stop() {
		robotDrive.stopMotor();
	}
	public void resetRobot() {
		this.resetEncoders();
		this.stop();
	}

	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
	}
}
