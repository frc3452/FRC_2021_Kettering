package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystem.CellLiftSubsystem;

public class MoveCellLift extends CommandBase {

    private CellLiftSubsystem cellLift;
    private double speed;

    public MoveCellLift(CellLiftSubsystem cellLift, double speed) {
        this.cellLift = cellLift;
        this.speed = speed;

        addRequirements(cellLift);
    }

    public void initialize() {

    }

    public void execute() {
        cellLift.forward(speed);
    }

    @Override
    public void end(boolean interrupted) {
        cellLift.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}