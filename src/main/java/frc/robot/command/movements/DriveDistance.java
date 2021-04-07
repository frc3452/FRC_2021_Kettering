package frc.robot.command.movements;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystem.DriveSubsystem;
import org.strykeforce.thirdcoast.swerve.SwerveDriveConfig;
import com.kauailabs.navx.frc.AHRS;

public class DriveDistance extends CommandBase {

    boolean isFinished = false;
    double motorSpeed;

    // distance is in inches, velocity is in inches/sec, time is in s
    double distance = 0;
    double azimuthAngle;
    double yaw;
    double velocity = 170.4; // inches per sec
    double time;
    double targetAngle;
    double minCommand = 0.05;
    double threshold = 3.0;
    double kp = 0.1;

    private static final DriveSubsystem DRIVE = RobotContainer.DRIVE;
    private SwerveDriveConfig config = new SwerveDriveConfig();

    AHRS gyro = config.gyro;

    public DriveDistance(double inches, double angle, double yaw) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        this.distance = inches;
        this.azimuthAngle = angle;
        this.yaw = yaw;

    }

    // Called just before this Command runs the first time
    @Override
    public void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
    public void execute() {

        time = distance / velocity;

        // double headingError = azimuthAngle - (gyro.getAngle() % 360);
        double steeringAdjust = 0.0;

    /* if (headingError > threshold){
        steeringAdjust = kp*headingError - minCommand;
    } else if(headingError < -threshold){
        steeringAdjust = kp*headingError + minCommand;
    } */

        //for time t, set the motor speed to 1. when time is up, set motor speed to 0. wait 20ms between each iteration.
        for (double t = 0; t <= time; t += 0.01) {
            motorSpeed = 1;
            DRIVE.drive(motorSpeed, azimuthAngle, yaw);
            if (t >= time) {
                DRIVE.stopAll();
            }

            try {
                wait(10, 0);
                // TimeUnit.SECONDS.sleep(0.02);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                System.out.println("DriveDistance Wait error");
            }

        }
        isFinished = true;
    }


    // Make this return true when this Command no longer needs to run execute()
    @Override
    public boolean isFinished() {
        return isFinished;
    }

    // Called once after isFinished returns true
    @Override
    public void end(boolean interrupted) {
    }

  /*
  void integrateAcceleration(){
    acceleration = gyro.getAccelFullScaleRangeG();
    velocity = (Math.pow(acceleration, 2) / 2);
    position = (Math.pow(velocity, 2) / 2); 
  }
*/
}