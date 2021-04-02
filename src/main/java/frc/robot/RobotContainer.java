package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Preferences;

import frc.robot.subsystem.DriveSubsystem;
import frc.robot.subsystem.ShooterSubsystem;
import frc.robot.subsystem.ConveyorSubsystem;
//import org.strykeforce.thirdcoast.telemetry.TelemetryController;
//import org.strykeforce.thirdcoast.telemetry.TelemetryService;


public class RobotContainer {
  //public static TelemetryService TELEMETRY;
  public static DriveSubsystem DRIVE = new DriveSubsystem();
  public static XboxController CONTROLS = new XboxController(0);
  public static XboxController OPERATOR = new XboxController(1);
  public static ShooterSubsystem SHOOTER = new ShooterSubsystem();
  public static ConveyorSubsystem CONVEYOR = new ConveyorSubsystem();
  public JoystickButton ShooterButtonA;
  public JoystickButton ShooterButtonB;
  public JoystickButton ShooterButtonY;
  public JoystickButton ShooterButtonX;
  public JoystickButton ShooterButtonStart;
  public JoystickButton ConveyorForwardButton;
  public JoystickButton ConveyorBackwardButton;
  public JoystickButton CellLiftForwardButton;
  public JoystickButton CellLiftBackwardButton;

  public RobotContainer() {
    ;

    if (RobotBase.isReal()) {

      //TELEMETRY = new TelemetryService(TelemetryController::new);

      // Display current speed multiplier
      Preferences prefs = Preferences.getInstance();
      int DriveSpeed = prefs.getInt("MaxDriveSpeed", 1);
      SmartDashboard.putNumber("Drive Speed", DriveSpeed);

      //TELEMETRY.start();
      DRIVE.stopall();
      DRIVE.setDefaultCommand(new TeleOpDriveCommand());
      // Shooter 100%
      ShooterButtonA = new JoystickButton(OPERATOR, Button.kA.value);
      ShooterButtonA.whileHeld(new LaunchShooter(20000));
      // ShooterButtonA.whileHeld(new MoveShooter(1));

      // Shooter 70%
      ShooterButtonB = new JoystickButton(OPERATOR, Button.kB.value);
      ShooterButtonB.whileHeld(new LaunchShooter(13500));
      // ShooterButtonB.whileHeld(new MoveShooter(.7));

      // Shooter 74%
      ShooterButtonY = new JoystickButton(OPERATOR, Button.kY.value);
      ShooterButtonY.whileHeld(new LaunchShooter(16000));
      // ShooterButtonY.whileHeld(new MoveShooter(.74));

      // Shooter 81% 
      ShooterButtonX = new JoystickButton(OPERATOR, Button.kX.value);
      ShooterButtonX.whileHeld(new LaunchShooter(17000));
      // ShooterButtonX.whileHeld(new MoveShooter(.81));

      // Shooter velocity
      ShooterButtonStart = new JoystickButton(OPERATOR, Button.kStart.value);
      ShooterButtonStart.whileHeld(new LaunchShooter(15000));

      // Conveyor Forward
      ConveyorForwardButton = new JoystickButton(OPERATOR, Button.kBumperRight.value);
      ConveyorForwardButton.whileHeld(new MoveConveyor(-0.75));

      // Conveyor Backward
      // ConveyorBackwardButton = new JoystickButton(OPERATOR, Button.kBumperLeft.value);
      // ConveyorBackwardButton.whileHeld(new MoveConveyor(-0.50));

      // Cell Lift Forward
      CellLiftForwardButton = new JoystickButton(OPERATOR, Button.kBumperLeft.value);
      CellLiftForwardButton.whileHeld(new MoveCellLift(0.50));



      // Cell lift Backward
      CellLiftBackwardButton = new JoystickButton(CONTROLS, Button.kBumperLeft.value);
      CellLiftBackwardButton.whileHeld(new MoveCellLift(-0.25));

      //Zero Gyro Command
       new JoystickButton(CONTROLS, Button.kA.value)
         .whenPressed(() -> DRIVE.zeroGyro());

      //Zero Azimuths Command
       new JoystickButton(CONTROLS, Button.kB.value)
        .whenPressed(() -> DRIVE.zeroAzimuths());
      
      //Save Azimuth zeroes Command
      // new JoystickButton(CONTROLS, Button.kX.value)
      //  .whenPressed(() -> DRIVE.saveAzimuthPositions());
    }
  }
}
