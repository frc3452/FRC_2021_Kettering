package frc.robot.command.Autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.Movements.DriveDistance;

public class bounce extends SequentialCommandGroup {

    public bounce() {
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