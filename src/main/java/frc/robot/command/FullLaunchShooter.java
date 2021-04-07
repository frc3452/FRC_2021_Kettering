package frc.robot.command;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.CellLift.CellLiftSpeeds;
import frc.robot.Constants.Conveyor.ConveyorSpeeds;

public class FullLaunchShooter extends ParallelCommandGroup {
    public FullLaunchShooter(double speed) {
        addCommands(
                new LaunchShooter(speed).withTimeout(5),
                new MoveCellLift(CellLiftSpeeds.MOVE_CELL_FORWARD).withTimeout(3),
                new MoveConveyor(ConveyorSpeeds.FORWARDS).withTimeout(3)
        );
    }

    //If you want it to be immediately complete, override the isFinished method
    //Otherwise, it will be complete when all of the commands are done

    //    @Override
    //    public boolean isFinished() {
    //        return true;
    //    }
}