package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystem.ShooterSubsystem;

public class MoveShooter extends CommandBase {

    private ShooterSubsystem shooter;
    private double speed;

    public MoveShooter(ShooterSubsystem shooter, double speed) {
        this.shooter = shooter;
        this.speed = speed;

        addRequirements(shooter);
    }

    public void initialize() {

    }

    public void execute() {
        shooter.forward(speed);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}