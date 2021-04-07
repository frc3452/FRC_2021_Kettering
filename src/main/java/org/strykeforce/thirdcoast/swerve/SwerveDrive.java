package org.strykeforce.thirdcoast.swerve;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.*;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.shuffleboard.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.strykeforce.thirdcoast.talon.Errors;

/**
 * Control a Third Coast swerve drive.
 *
 * <p>Wheels are a array numbered 0-3 from front to back, with even numbers on the left side when
 * facing forward.
 *
 * <p>Derivation of inverse kinematic equations are from Ether's <a
 * href="https://www.chiefdelphi.com/media/papers/2426">Swerve Kinematics and Programming</a>.
 *
 * @see Wheel
 */
@SuppressWarnings("unused")
public class SwerveDrive {

  public static final int DEFAULT_ABSOLUTE_AZIMUTH_OFFSET = 0;
  //private static final Logger logger = LoggerFactory.getLogger(SwerveDrive.class);
  private static final int WHEEL_COUNT = 4;
  private final AHRS gyro;
  private final double kLengthComponent;
  private final double kWidthComponent;
  private final double kGyroRateCorrection;
  private final Wheel[] wheels;
  public final double[] ws = new double[WHEEL_COUNT];
  private final double[] wa = new double[WHEEL_COUNT];
  private boolean isFieldOriented;

  public SwerveDrive(SwerveDriveConfig config) {
    gyro = config.gyro;
    wheels = config.wheels;

    final boolean summarizeErrors = config.summarizeTalonErrors;
    Errors.setSummarized(summarizeErrors);
    Errors.setCount(0);
    //logger.debug("TalonSRX configuration errors summarized = {}", summarizeErrors);

    double length = config.length;
    double width = config.width;
    double radius = Math.hypot(length, width);
    kLengthComponent = length / radius;
    kWidthComponent = width / radius;

    //logger.info("gyro is configured: {}", gyro != null);
    //logger.info("gyro is connected: {}", gyro != null && gyro.isConnected());
    setFieldOriented(gyro != null && gyro.isConnected());

    if (isFieldOriented) {
      gyro.enableLogging(config.gyroLoggingEnabled);
      double robotPeriod = config.robotPeriod;
      double gyroRateCoeff = config.gyroRateCoeff;
      int rate = gyro.getActualUpdateRate();
      double gyroPeriod = 1.0 / rate;
      kGyroRateCorrection = (robotPeriod / gyroPeriod) * gyroRateCoeff;
      //logger.debug("gyro frequency = {} Hz", rate);
    } else {
      //logger.warn("gyro is missing or not enabled");
      kGyroRateCorrection = 0;
    }

    //logger.debug("length = {}", length);
    //logger.debug("width = {}", width);
    //logger.debug("enableGyroLogging = {}", config.gyroLoggingEnabled);
    //logger.debug("gyroRateCorrection = {}", kGyroRateCorrection);
  }

  /**
   * Return key that wheel zero information is stored under in WPI preferences.
   *
   * @param wheel the wheel number
   * @return the String key
   */
  public static String getPreferenceKeyForWheel(int wheel) {
    return String.format("%s-wheel.%d", SwerveDrive.class.getSimpleName(), wheel);
  }

  /**
   * Set the drive mode.
   *
   * @param driveMode the drive mode
   */
  public void setDriveMode(DriveMode driveMode) {
    for (Wheel wheel : wheels) {
      wheel.setDriveMode(driveMode);
    }
    //logger.info("drive mode = {}", driveMode);
  }

  /**
   * Set all four wheels to specified values.
   *
   * @param azimuth -0.5 to 0.5 rotations, measured clockwise with zero being the robot
   *     straight-ahead position
   * @param drive 0 to 1 in the direction of the wheel azimuth
   */
  public void set(double azimuth, double drive) {
    for (Wheel wheel : wheels) {
      // System.out.print("SwerveDrive Set Azimuth ");
      // System.out.print(azimuth);
      // System.out.print(" Drive ");
      // System.out.println(drive);
      // SmartDashboard.putNumber("Drive Set", drive);
      // SmartDashboard.putNumber("Azimuth Set", azimuth);
      // SmartDashboard.putNumber("CanCoder Position", wheels[0].getAzimuthAbsolutePosition());
      wheel.set(azimuth, drive, 5);
    }
  }

  /**
   * Drive the robot in given field-relative direction and with given rotation.
   *
   * @param forward Y-axis movement, from -1.0 (reverse) to 1.0 (forward)
   * @param strafe X-axis movement, from -1.0 (left) to 1.0 (right)
   * @param azimuth robot rotation, from -1.0 (CCW) to 1.0 (CW)
   */
  public void drive(double forward, double strafe, double azimuth) {

    // Use gyro for field-oriented drive. We use getAngle instead of getYaw to enable arbitrary
    // autonomous starting positions.
    if (isFieldOriented) {
      // System.out.println("Apply the gyro");
      double angle = gyro.getAngle();
      angle += gyro.getRate() * kGyroRateCorrection;
      angle = Math.IEEEremainder(angle, 360.0);

      angle = Math.toRadians(angle);
      final double temp = forward * Math.cos(angle) + strafe * Math.sin(angle);
      strafe = strafe * Math.cos(angle) - forward * Math.sin(angle);
      forward = temp;
    }

    final double a = strafe - azimuth * kLengthComponent;
    final double b = strafe + azimuth * kLengthComponent;
    final double c = forward - azimuth * kWidthComponent;
    final double d = forward + azimuth * kWidthComponent;

    // wheel speed
    ws[0] = Math.hypot(b, d);
    ws[1] = Math.hypot(b, c);
    ws[2] = Math.hypot(a, d);
    ws[3] = Math.hypot(a, c);

    // wheel azimuth
    wa[0] = Math.atan2(b, d) * 0.5 / Math.PI;
    wa[1] = Math.atan2(b, c) * 0.5 / Math.PI;
    wa[2] = Math.atan2(a, d) * 0.5 / Math.PI;
    wa[3] = Math.atan2(a, c) * 0.5 / Math.PI;

    // normalize wheel speed
    final double maxWheelSpeed = Math.max(Math.max(ws[0], ws[1]), Math.max(ws[2], ws[3]));
    if (maxWheelSpeed > 1.0) {
      for (int i = 0; i < WHEEL_COUNT; i++) {
        ws[i] /= maxWheelSpeed;
      }
    }

    // set wheels
    for (int i = 0; i < WHEEL_COUNT; i++) {
      wheels[i].set(wa[i], ws[i], i);
      // String SwerveAzimuthLabel  = "Swerve Azimuth " + i;
      // String SwerveDriveLabel = "Swerve Drive " + i;
      // String SwerveCanCoderLabel = "CanCoder Absolute Position " + i;
      // SmartDashboard.putNumber(SwerveAzimuthLabel, wa[i]);
      // SmartDashboard.putNumber(SwerveDriveLabel, ws[i]);
      // SmartDashboard.putNumber(SwerveCanCoderLabel, wheels[i].getAzimuthAbsolutePosition());

    }
  }

  /**
   * Stops all wheels' azimuth and drive movement. Calling this in the robots {@code teleopInit} and
   * {@code autonomousInit} will reset wheel azimuth relative encoders to the current position and
   * thereby prevent wheel rotation if the wheels were moved manually while the robot was disabled.
   */
  public void stop() {
    for (Wheel wheel : wheels) {
      wheel.stop();
    }
    //logger.info("stopped all wheels");
  }

  /**
   * Save the wheels' azimuth current position as read by absolute encoder. These values are saved
   * persistently on the roboRIO and are normally used to calculate the relative encoder offset
   * during wheel initialization.
   *
   * <p>The wheel alignment data is saved in the WPI preferences data store and may be viewed using
   * a network tables viewer.
   *
   * @see #zeroAzimuthEncoders()
   */
  public void saveAzimuthPositions() {
    saveAzimuthPositions(Preferences.getInstance());
  }

  void saveAzimuthPositions(Preferences prefs) {
    System.out.println("SwerveDrive Attempt to save Azimuth");
    for (int i = 0; i < WHEEL_COUNT; i++) {
      int position = wheels[i].getAzimuthAbsolutePosition();
      prefs.putInt(getPreferenceKeyForWheel(i), position);
      
      //logger.info("azimuth {}: saved zero = {}", i, position);
    }
  }

  /**
   * Set wheels' azimuth relative offset from zero based on the current absolute position. This uses
   * the physical zero position as read by the absolute encoder and saved during the wheel alignment
   * process.
   *
   * @see #saveAzimuthPositions()
   */
  public void zeroAzimuthEncoders() {
    zeroAzimuthEncoders(Preferences.getInstance());
  }

  void zeroAzimuthEncoders(Preferences prefs) {
    Errors.setCount(0);
    for (int i = 0; i < WHEEL_COUNT; i++) {
      int position = prefs.getInt(getPreferenceKeyForWheel(i), DEFAULT_ABSOLUTE_AZIMUTH_OFFSET);
      wheels[i].setAzimuthZero(position);
      // System.out.print("SwerveDrive ZeroAzimuth Encoder for ");
      // System.out.print(i);
      // System.out.print(" Position ");
      // System.out.println(position);
      //logger.info("azimuth {}: loaded zero = {}", i, position);
    }
    int errorCount = Errors.getCount();
    //if (errorCount > 0) logger.error("TalonSRX set azimuth zero error count = {}", errorCount);
  }

  /**
   * Returns the four wheels of the swerve drive.
   *
   * @return the Wheel array.
   */
  public Wheel[] getWheels() {
    return wheels;
  }

  /**
   * Get the gyro instance being used by the drive.
   *
   * @return the gyro instance
   */
  public AHRS getGyro() {
    return gyro;
  }

  /**
   * Get status of field-oriented driving.
   *
   * @return status of field-oriented driving.
   */
  public boolean isFieldOriented() {
    return isFieldOriented;
  }

  /**
   * Enable or disable field-oriented driving. Enabled by default if connected gyro is passed in via
   * {@code SwerveDriveConfig} during construction.
   *
   * @param enabled true to enable field-oriented driving.
   */
  public void setFieldOriented(boolean enabled) {
    isFieldOriented = enabled;
    // isFieldOriented = false;
    //logger.info("field orientation driving is {}", isFieldOriented ? "ENABLED" : "DISABLED");
  }

  /**
   * Unit testing
   *
   * @return length
   */
  double getLengthComponent() {
    return kLengthComponent;
  }

  /**
   * Unit testing
   *
   * @return width
   */
  double getWidthComponent() {
    return kWidthComponent;
  }

  /** Swerve Drive drive mode */
  public enum DriveMode {
    OPEN_LOOP,
    CLOSED_LOOP,
    TELEOP,
    TRAJECTORY,
    AZIMUTH
  }
}
