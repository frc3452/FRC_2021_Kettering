package frc.robot.command.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.movements.DriveDistance;
import frc.robot.subsystem.DriveSubsystem;

public class Bounce extends SequentialCommandGroup {

    public Bounce(DriveSubsystem driveSubsystem) {
        addCommands(
                new DriveDistance(driveSubsystem, 0, 90, 0),
                new DriveDistance(driveSubsystem, 60, 90, 0),
                new DriveDistance(driveSubsystem, 0, 0, 0),
                new DriveDistance(driveSubsystem, 48, 0, 0),
                new DriveDistance(driveSubsystem, 156, 53, 0),
                new DriveDistance(driveSubsystem, 1, 87, 0),
                new DriveDistance(driveSubsystem, 1, 86, 0),
                new DriveDistance(driveSubsystem, 60, 0, 0)
        );
    }
}