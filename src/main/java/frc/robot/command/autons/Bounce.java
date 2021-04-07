package frc.robot.command.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.movements.DriveDistance;

public class Bounce extends SequentialCommandGroup {

    public Bounce() {
        addCommands(
                new DriveDistance(0, 90, 0),
                new DriveDistance(60, 90, 0),
                new DriveDistance(0, 0, 0),
                new DriveDistance(48, 0, 0),
                new DriveDistance(156, 53, 0),
                new DriveDistance(1, 87, 0),
                new DriveDistance(1, 86, 0),
                new DriveDistance(60, 0, 0)
        );
    }
}