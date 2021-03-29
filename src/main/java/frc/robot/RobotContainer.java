package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.command.*;
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
  public JoystickButton Shooter100Button;
  public JoystickButton Shooter75Button;
  public JoystickButton Shooter50Button;
  public JoystickButton ShooterReverseButton;
  public JoystickButton ConveyorForwardButton;
  public JoystickButton ConveyorBackwardButton;
  public JoystickButton CellLiftForwardButton;
  public JoystickButton CellLiftBackwardButton;

  public RobotContainer() {
    ;

    if (RobotBase.isReal()) {

      //TELEMETRY = new TelemetryService(TelemetryController::new);


      //TELEMETRY.start();
      DRIVE.stopall();
      DRIVE.setDefaultCommand(new TeleOpDriveCommand());
      // Shooter 100%
      Shooter100Button = new JoystickButton(OPERATOR, Button.kA.value);
      Shooter100Button.whileHeld(new MoveShooter(1.0));

      // Shooter 75%
      Shooter75Button = new JoystickButton(OPERATOR, Button.kB.value);
      Shooter75Button.whileHeld(new MoveShooter(.75));

      // Shooter 50%
      Shooter50Button = new JoystickButton(OPERATOR, Button.kY.value);
      Shooter50Button.whileHeld(new MoveShooter(.5));

      // Shooter 10% Reverse
      ShooterReverseButton = new JoystickButton(OPERATOR, Button.kX.value);
      ShooterReverseButton.whileHeld(new MoveShooter(-.25));

      // Conveyor Forward
      ConveyorForwardButton = new JoystickButton(OPERATOR, Button.kBumperRight.value);
      ConveyorForwardButton.whileHeld(new MoveConveyor(0.75));

      // Conveyor Backward
      ConveyorBackwardButton = new JoystickButton(OPERATOR, Button.kBumperLeft.value);
      ConveyorBackwardButton.whileHeld(new MoveConveyor(-0.50));

      // Cell Lift Forward
      CellLiftForwardButton = new JoystickButton(CONTROLS, Button.kBumperRight.value);
      CellLiftForwardButton.whileHeld(new MoveCellLift(0.50));

      // Cell lift Backward
      CellLiftBackwardButton = new JoystickButton(CONTROLS, Button.kBumperLeft.value);
      CellLiftBackwardButton.whileHeld(new MoveCellLift(-0.25));

      //Zero Gyro Command
       new JoystickButton(CONTROLS, Button.kA.value)
         .whenPressed(() -> DRIVE.zeroGyro());

      //Zero Azimuths Command
      // new JoystickButton(CONTROLS, Button.kB.value)
      //  .whenPressed(() -> DRIVE.zeroAzimuths());
      
      //Save Azimuth zeroes Command
      // new JoystickButton(CONTROLS, Button.kX.value)
      //   .whenPressed(() -> DRIVE.saveAzimuthPositions());
    }
  }
}
