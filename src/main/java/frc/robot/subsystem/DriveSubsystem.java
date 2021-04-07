package frc.robot.subsystem;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.strykeforce.thirdcoast.swerve.SwerveDrive.DriveMode;
import frc.robot.motion.PathController;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.strykeforce.thirdcoast.swerve.SwerveDrive;
import org.strykeforce.thirdcoast.swerve.SwerveDriveConfig;
import org.strykeforce.thirdcoast.swerve.Wheel;

public class DriveSubsystem extends SubsystemBase {

    private static final double ROBOT_LENGTH = 27.5; // 27.5in 0.6985m
    private static final double ROBOT_WIDTH = 16; // 16in 0.4064m
    private PathController pathController;
    private static boolean enableDriveAxisFlip = false;
    private double targetYaw;
    private double yawError;
    private boolean isPath = false;

    private final SwerveDrive swerve = getSwerve();

    //private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public DriveSubsystem() {
        // System.out.println("DriveSubsystem Set Field Oriented");
        swerve.setFieldOriented(true);
        // System.out.println("DriveSubsystem zeroAzimuth");
        zeroAzimuths();
    }


    public void drive(double forward, double strafe, double yaw) {
        swerve.drive(forward, strafe, yaw);
    }

    public void zeroGyro() {
        AHRS gyro = swerve.getGyro();
        gyro.setAngleAdjustment(0);
        double adj = gyro.getAngle() % 360;
        gyro.setAngleAdjustment(-adj);
        //logger.info("resetting gyro: ({})", adj);
    }

    public void startPath(String path, double targetYaw, boolean isDriftOut) {
        this.targetYaw = targetYaw;
        this.pathController = new PathController(path, targetYaw, isDriftOut);
        pathController.start();
        isPath = true;
    }

    public AHRS getGyro() {
        return swerve.getGyro();
    }

    public void zeroAzimuths() {
        swerve.zeroAzimuthEncoders();
    }

    public void saveAzimuthPositions() {
        // System.out.println("DriveSubsystem Attempt to Save Azimuth Positions.");
        swerve.saveAzimuthPositions();
    }

    public void stopAll() {
        swerve.stop();
    }

    // Swerve configuration

    private SwerveDriveConfig config;

    public SwerveDriveConfig getConfig() {
        return config;
    }

    private SwerveDrive getSwerve() {
        config = new SwerveDriveConfig();

        config.gyro = new AHRS(SPI.Port.kMXP);
        //Heres where the gyro is made
        config.length = ROBOT_LENGTH;
        config.width = ROBOT_WIDTH;
        config.gyroLoggingEnabled = true;
        config.summarizeTalonErrors = false;

        /* Update Motor controller configs before calling get wheels*/
        // EX. config.driveConfig.slot0.kP = 3.0;

        config.wheels = config.getWheels();
        return new SwerveDrive(config);
    }

    public Wheel[] getAllWheels() {
        return swerve.getWheels();
    }

    ////////////////////////////////////////////////////////////////////////////
    // PATHFINDER
    ////////////////////////////////////////////////////////////////////////////

    private void setEnableDriveAxisFlip(boolean enable) {
        enableDriveAxisFlip = enable;
    }


    public void setDriveMode(DriveMode mode) {
        swerve.setDriveMode(mode);
    }

    public void zeroYawEncoders() {
        swerve.zeroAzimuthEncoders();
    }

    public boolean isPathFinished() {
        if (pathController.isFinished()) {
            isPath = false;
            return true;
        }
        return false;
    }


}
