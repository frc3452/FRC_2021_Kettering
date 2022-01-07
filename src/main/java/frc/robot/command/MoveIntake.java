package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.IntakeSubsystem;

public class MoveIntake extends CommandBase {

    private IntakeSubsystem intake;
    private double speed;

    public MoveIntake(IntakeSubsystem intake, double speed) {
        this.intake = intake;
        this.speed = speed;

        addRequirements(intake);
    }

    public void initialize() {

    }

    public void execute() {
        intake.openLoop(speed);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}