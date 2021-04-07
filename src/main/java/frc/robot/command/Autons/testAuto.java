package frc.robot.command.Autons;

import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.Movements.DriveTrajectory;

public class testAuto extends SequentialCommandGroup {

    public testAuto() {
        addCommands(
                new DriveTrajectory("Slalom", 0.0, false)
        );
    }

    /**
     If you want to one command, then the next, then the next, use {@link SequentialCommandGroup}
     */
}