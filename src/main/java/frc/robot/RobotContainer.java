package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.CellLift.CellLiftSpeeds;
import frc.robot.Constants.Conveyor.ConveyorSpeeds;
import frc.robot.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.command.Autons.bounce;
import frc.robot.command.Autons.testAuto;


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
    public JoystickButton DriveButtonA;
    public JoystickButton DriveButtonB;
    public JoystickButton DriveButtonX;
    public JoystickButton DriveButtonY;
    public JoystickButton DriveButtonStart;
    public JoystickButton DriveButtonBack;
    public JoystickButton DriveButtonLBump;
    public JoystickButton DriveButtonRBump;
    public JoystickButton ConveyorForwardButton;
    public JoystickButton ConveyorBackwardButton;
    public JoystickButton CellLiftForwardButton;
    public JoystickButton CellLiftBackwardButton;
    private SendableChooser<Command> command = new SendableChooser<>();

    public RobotContainer() {

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
            // ShooterButtonA.whileHeld(new LaunchShooter(21000));
            ShooterButtonA.whileHeld(new MoveShooter(1));

            DriveButtonA = new JoystickButton(CONTROLS, Button.kA.value);
            DriveButtonA.whileHeld(new FullLaunchShooter(21000));


            // Shooter 70%
            ShooterButtonB = new JoystickButton(OPERATOR, Button.kB.value);
            ShooterButtonB.whileHeld(new LaunchShooter(13800));
            // ShooterButtonB.whileHeld(new MoveShooter(.7));

            DriveButtonB = new JoystickButton(CONTROLS, Button.kB.value);
            DriveButtonB.whileHeld(new LaunchShooter(13800));

            // Shooter 74%
            ShooterButtonY = new JoystickButton(OPERATOR, Button.kY.value);
            ShooterButtonY.whileHeld(new LaunchShooter(14875));
            // ShooterButtonY.whileHeld(new MoveShooter(.74));

            // Shooter 81%
            ShooterButtonX = new JoystickButton(OPERATOR, Button.kX.value);
            ShooterButtonX.whileHeld(new LaunchShooter(16075));
            // ShooterButtonX.whileHeld(new MoveShooter(.81));

            // Shooter velocity
            ShooterButtonStart = new JoystickButton(OPERATOR, Button.kStart.value);
            ShooterButtonStart.whileHeld(new LaunchShooter(15800));

            // Conveyor Forward
            ConveyorForwardButton = new JoystickButton(OPERATOR, Button.kBumperRight.value);
            ConveyorForwardButton.whileHeld(new MoveConveyor(ConveyorSpeeds.FORWARDS));

            // Conveyor Backward
            // ConveyorBackwardButton = new JoystickButton(OPERATOR, Button.kBumperLeft.value);
            // ConveyorBackwardButton.whileHeld(new MoveConveyor(-0.50));

            // Cell Lift Forward
            CellLiftForwardButton = new JoystickButton(OPERATOR, Button.kBumperLeft.value);
            CellLiftForwardButton.whileHeld(new MoveCellLift(CellLiftSpeeds.MOVE_CELL_FORWARD));


            // Cell lift Backward
            DriveButtonLBump = new JoystickButton(CONTROLS, Button.kBumperLeft.value);
            DriveButtonLBump.whileHeld(new MoveCellLift(CellLiftSpeeds.MOVE_CELL_BACKWARDS));

            DriveButtonRBump = new JoystickButton(CONTROLS, Button.kBumperRight.value);
            DriveButtonRBump.whileHeld(new MoveConveyor(ConveyorSpeeds.BACKWARDS));


            //Zero Gyro Command
            new JoystickButton(CONTROLS, Button.kStart.value)
                    .whenPressed(() -> DRIVE.zeroGyro());

            //Zero Azimuths Command
            // new JoystickButton(CONTROLS, Button.kB.value)
            // .whenPressed(() -> DRIVE.zeroAzimuths());

            //Save Azimuth zeroes Command
            // new JoystickButton(CONTROLS, Button.kX.value)
            //  .whenPressed(() -> DRIVE.saveAzimuthPositions());
        }
        command.setDefaultOption("Test", new testAuto());
        command.addOption("Bounce", new bounce());
        Shuffleboard.getTab(Constants.SBTabDriverDisplay)
                .getLayout("Auto", BuiltInLayouts.kList).withPosition(Constants.autoColumn, 0).withSize(3, 1)
                .add("Choose an Auto Mode", command).withWidget(BuiltInWidgets.kSplitButtonChooser);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return command.getSelected();
    }
}
