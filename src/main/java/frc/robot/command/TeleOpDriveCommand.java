package frc.robot.command;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.DriveSubsystem;

public final class TeleOpDriveCommand extends CommandBase {

    private static final double DEADBAND = 0.1;
    private final DriveSubsystem drive;
    private final XboxController controls;

    public TeleOpDriveCommand(DriveSubsystem drive, XboxController controls) {
        this.drive = drive;
        this.controls = controls;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        double forward = deadband(controls.getRawAxis(1));
        double strafe = deadband(-controls.getRawAxis(0));
        double yaw = deadband(-controls.getRawAxis(4));
    /* System.out.print("Teleop openLoop: ");
    System.out.print(openLoop);
    System.out.print(" strafe: ");
    System.out.print(strafe);
    System.out.print(" yaw: ");
    System.out.println(yaw); */
        if (forward == 0 && strafe == 0 && yaw == 0) {
            drive.stopAll();
        }
        drive.drive(forward, strafe, yaw);
    }

    @Override
    public void end(boolean interrupted) {
        drive.drive(0.0, 0.0, 0.0);
    }

    private double deadband(double value) {
        if (Math.abs(value) < DEADBAND) return 0.0;
        return value;
    }
}
