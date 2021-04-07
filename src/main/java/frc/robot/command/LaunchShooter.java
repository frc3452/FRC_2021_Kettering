package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystem.ShooterSubsystem;

public class LaunchShooter extends CommandBase {

    private ShooterSubsystem shooter;
    private double speed;

    public LaunchShooter(double speed) {
        this.shooter = RobotContainer.SHOOTER;
        this.speed = speed;

        addRequirements(shooter);
    }

    public void initialize() {

    }

    public void execute() {
        // System.out.println("launch Shooter");
        shooter.launch(speed);
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