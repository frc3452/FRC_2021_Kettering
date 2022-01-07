package frc.robot;

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.CellLift.CellLiftSpeeds;
import frc.robot.Constants.Conveyor.ConveyorSpeeds;
import frc.robot.Constants.Intake.IntakeSpeeds;
import frc.robot.command.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.command.autons.Bounce;
import frc.robot.command.autons.TestAuto;


import frc.robot.subsystem.CellLiftSubsystem;
import frc.robot.subsystem.DriveSubsystem;
import frc.robot.subsystem.ShooterSubsystem;
import frc.robot.subsystem.ConveyorSubsystem;
import frc.robot.subsystem.IntakeSubsystem;
//import org.strykeforce.thirdcoast.telemetry.TelemetryController;
//import org.strykeforce.thirdcoast.telemetry.TelemetryService;


public class RobotContainer {
    //public static TelemetryService TELEMETRY;
    private final DriveSubsystem drive = new DriveSubsystem();
    private final XboxController controls = new XboxController(0);
    private final XboxController operator = new XboxController(1);
    private final ShooterSubsystem shooter = new ShooterSubsystem();
    private final ConveyorSubsystem conveyor = new ConveyorSubsystem();
    private final CellLiftSubsystem cellLift = new CellLiftSubsystem();
    private final IntakeSubsystem intake = new IntakeSubsystem();

    private SendableChooser<Command> command = new SendableChooser<>();

    public RobotContainer() {

        if (RobotBase.isReal()) {

            //TELEMETRY = new TelemetryService(TelemetryController::new);

            // Display current speed multiplier
            Preferences prefs = Preferences.getInstance();
            int driveSpeed = prefs.getInt("MaxDriveSpeed", 1);
            SmartDashboard.putNumber("Drive Speed", driveSpeed);

            //TELEMETRY.start();
            drive.stopAll();
            drive.setDefaultCommand(new TeleOpDriveCommand(drive, controls));
            // Shooter 100%
            new JoystickButton(operator, Button.kA.value)
                    .whileHeld(new OpenLoopShooter(shooter, 1));
            // ShooterButtonA.whileHeld(new LaunchShooter(21000));

            new JoystickButton(controls, Button.kA.value)
                    .whileHeld(new FullLaunchShooter(shooter, cellLift, conveyor, 21000));


            // Shooter 70%
            new JoystickButton(operator, Button.kB.value)
                    .whileHeld(new LaunchShooter(shooter, 13800));
            // ShooterButtonB.whileHeld(new MoveShooter(.7));

            new JoystickButton(controls, Button.kB.value)
                    .whileHeld(new LaunchShooter(shooter, 13800));

            // Shooter 74%
            new JoystickButton(operator, Button.kY.value)
                    .whileHeld(new LaunchShooter(shooter, 14875));
            // ShooterButtonY.whileHeld(new MoveShooter(.74));

            // Shooter 81%
            new JoystickButton(operator, Button.kX.value)
                    .whileHeld(new LaunchShooter(shooter, 16075));
            // ShooterButtonX.whileHeld(new MoveShooter(.81));

            // Shooter velocity
            new JoystickButton(operator, Button.kStart.value)
                    .whileHeld(new LaunchShooter(shooter, 15800));

            // Conveyor openLoop
            new JoystickButton(operator, Button.kBumperRight.value)
                    .whileHeld(new MoveConveyor(conveyor, ConveyorSpeeds.FORWARDS));

            // Conveyor backward
            // ConveyorBackwardButton = new JoystickButton(OPERATOR, Button.kBumperLeft.value);
            // ConveyorBackwardButton.whileHeld(new MoveConveyor(-0.50));

            // Cell Lift openLoop
            new JoystickButton(operator, Button.kBumperLeft.value)
                    .whileHeld(new MoveCellLift(cellLift, CellLiftSpeeds.MOVE_CELL_FORWARD));


            // Cell lift backward
            new JoystickButton(controls, Button.kBumperLeft.value)
                    .whileHeld(new MoveCellLift(cellLift, CellLiftSpeeds.MOVE_CELL_BACKWARDS));

            new JoystickButton(controls, Button.kBumperRight.value)
                    .whileHeld(new MoveConveyor(conveyor, ConveyorSpeeds.BACKWARDS));

            new JoystickButton(controls, Button.kX.value)
                    .whileHeld(new MoveIntake(intake, IntakeSpeeds.FORWARDS));

            new JoystickButton(controls, Button.kY.value)
                    .whileHeld(new MoveIntake(intake, IntakeSpeeds.RELEASE));

            //Zero Gyro Command
           // new JoystickButton(controls, Button.kStart.value)
             //       .whenPressed(() -> drive.zeroGyro());

            //Zero Azimuths Command
            //    new JoystickButton(operator, Button.kStart.value)
            // .whenPressed(() -> drive.zeroAzimuths());

            //Save Azimuth zeroes Command
            //new JoystickButton(controls, Button.kStart.value)
           //  .whenPressed(() -> drive.saveAzimuthPositions());
        }
        command.setDefaultOption("Test", new TestAuto(drive));
        command.addOption("Bounce", new Bounce(drive));
        Shuffleboard.getTab(Constants.SBTabDriverDisplay)
                .getLayout("Auto", BuiltInLayouts.kList).withPosition(Constants.autoColumn, 0).withSize(3, 1)
                .add("Choose an Auto Mode", command).withWidget(BuiltInWidgets.kSplitButtonChooser);

        drive.zeroGyro();
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
