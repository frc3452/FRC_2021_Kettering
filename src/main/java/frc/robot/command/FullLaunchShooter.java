package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class FullLaunchShooter extends ParallelCommandGroup {
    public FullLaunchShooter(double speed) {
        addCommands(
                new LaunchShooter(speed).withTimeout(5),
                new MoveCellLift(.45).withTimeout(3),
                new MoveConveyor(-.35).withTimeout(3)
        );
    }

    //If you want it to be immediately complete, override the isFinished method
    //Otherwise, it will be complete when all of the commands are done

    //    @Override
    //    public boolean isFinished() {
    //        return true;
    //    }
}