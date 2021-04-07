package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.ShooterSubsystem;

public class MoveShooter extends CommandBase {

    private ShooterSubsystem shooter;
    private double speed;

    public MoveShooter(double speed) {
        this.shooter = new ShooterSubsystem();
        this.speed = speed;

        addRequirements(shooter);
    }

    public void initialize() {

    }

    public void execute() {
        shooter.Forward(speed);
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