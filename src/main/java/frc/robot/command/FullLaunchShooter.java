package frc.robot.command;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.Constants.CellLift.CellLiftSpeeds;
import frc.robot.Constants.Conveyor.ConveyorSpeeds;
import frc.robot.subsystem.CellLiftSubsystem;
import frc.robot.subsystem.ConveyorSubsystem;
import frc.robot.subsystem.ShooterSubsystem;

public class FullLaunchShooter extends ParallelCommandGroup {
    public FullLaunchShooter(ShooterSubsystem shooterSubsystem, CellLiftSubsystem cellLiftSubsystem,
                             ConveyorSubsystem conveyorSubsystem, double speed) {
        addCommands(
                new LaunchShooter(shooterSubsystem, speed).withTimeout(5),
                new MoveCellLift(cellLiftSubsystem, CellLiftSpeeds.MOVE_CELL_FORWARD).withTimeout(3),
                new MoveConveyor(conveyorSubsystem, ConveyorSpeeds.FORWARDS).withTimeout(3)
        );
    }

    //If you want it to be immediately complete, override the isFinished method
    //Otherwise, it will be complete when all of the commands are done

    //    @Override
    //    public boolean isFinished() {
    //        return true;
    //    }
}