package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystem.ConveyorSubsystem;

public class MoveConveyor extends CommandBase {

    private ConveyorSubsystem conveyor;
    private double speed;

    public MoveConveyor(ConveyorSubsystem conveyor, double speed) {
        this.conveyor = conveyor;
        this.speed = speed;

        addRequirements(conveyor);
    }

    public void initialize() {

    }

    public void execute() {
        conveyor.forward(speed);
    }

    @Override
    public void end(boolean interrupted) {
        conveyor.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}