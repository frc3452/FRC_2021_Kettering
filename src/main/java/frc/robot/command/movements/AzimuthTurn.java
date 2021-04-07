package frc.robot.command.movements;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystem.DriveSubsystem;
import org.strykeforce.thirdcoast.swerve.SwerveDriveConfig;

public class AzimuthTurn extends CommandBase {
    double targetAngle;
    boolean isFinished = false;
    double minCommand = 0.05;
    double threshold = 3.0;
    double kp = 0.1;

    private static final DriveSubsystem DRIVE = RobotContainer.DRIVE;
    private SwerveDriveConfig config = new SwerveDriveConfig();
    AHRS gyro = config.gyro;

    public AzimuthTurn(double degrees) {
        this.targetAngle = degrees;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        double headingError = targetAngle - (gyro.getAngle() % 360);
        double steeringAdjust = 0.0;

        if (headingError > threshold) {
            steeringAdjust = kp * headingError - minCommand;
        } else if (headingError < -threshold) {
            steeringAdjust = kp * headingError + minCommand;
        }
        DRIVE.drive(0, steeringAdjust, 0);
        // add swerve drive here 

        if (headingError >= -threshold && headingError <= threshold) {
            isFinished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }

}
