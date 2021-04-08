package frc.robot.command;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Util;
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
        double forward = Util.applyDeadband(controls.getRawAxis(1), DEADBAND);
        double strafe = Util.applyDeadband(-controls.getRawAxis(0), DEADBAND);
        double yaw = Util.applyDeadband(-controls.getRawAxis(4), DEADBAND);
        if (forward == 0 && strafe == 0 && yaw == 0) {
            drive.stopAll();
        } else {
            drive.drive(forward, strafe, yaw);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drive.drive(0.0, 0.0, 0.0);
    }

}
