package frc.robot.command.autons;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.command.movements.DriveTrajectory;

public class TestAuto extends SequentialCommandGroup {

    public TestAuto() {
        addCommands(
                new DriveTrajectory("Slalom", 0.0, false)
        );
    }

    /**
     If you want to one command, then the next, then the next, use {@link SequentialCommandGroup}
     */
}