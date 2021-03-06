package frc.robot.motion;

import edu.wpi.first.wpilibj.Notifier;
import frc.robot.subsystem.DriveSubsystem;

import java.io.File;

import org.strykeforce.thirdcoast.swerve.SwerveDrive;
import org.strykeforce.thirdcoast.swerve.Wheel;

public class PathController implements Runnable {

    private static final int NUM_WHEELS = 4;
    private static final int TICKS_PER_INCH = 2300;
    private final DriveSubsystem drive;

    @SuppressWarnings("FieldCanBeLocal")
    private static final double yawKp = 0.01; // 0.03

    private static final double percentToDone = 0.50;
    private static final double DT = 0.04;

    private static final double MIN_VEL = 45.0;
    private static final double MIN_START = 40.0;
    //  private static final double RATE_CAP = 0.35;
    //  private static final RateLimit rateLimit = new RateLimit(0.015);
    private final int PID = 0;
    private Trajectory trajectory;
    private Notifier notifier;
    private Wheel[] wheels;
    private States state;
    private double maxVelocityInSec;
    private double yawDelta;
    private int iteration;
    private double[] start;
    private Setpoint setpoint;
    private double setpointPos;
    private double yawError;
    private boolean isDriftOut;

    public PathController(DriveSubsystem driveSubsystem, String pathName, double yawDelta, boolean isDriftOut) {
        this.drive = driveSubsystem;
        this.yawDelta = yawDelta;
        this.isDriftOut = isDriftOut;
        wheels = drive.getAllWheels();
        File csvFile = new File("home/lvuser/deploy/paths/" + pathName + ".pf1.csv");

        trajectory = new Trajectory(csvFile);
    }

    public void start() {
        start = new double[4];
        notifier = new Notifier(this);
        notifier.startPeriodic(DT);
        state = States.STARTING;
    }

    public boolean isFinished() {
        return state == States.STOPPED;
    }

    @Override
    public void run() {

        switch (state) {
            case STARTING:
                logState();
                double ticksPerSecMax = wheels[0].getDriveSetpointMax() * 10.0;
                maxVelocityInSec = ticksPerSecMax / TICKS_PER_INCH;
                iteration = 0;
                drive.setDriveMode(SwerveDrive.DriveMode.CLOSED_LOOP);

                for (int i = 0; i < NUM_WHEELS; i++) {
                    start[i] = wheels[i].getDriveTalon().getDrivePosition();
                }

                double currentAngle = drive.getGyro().getAngle();
                setpoint = new Setpoint(currentAngle, yawDelta, percentToDone);

                logInit();
                state = States.RUNNING;
                break;
            case RUNNING:
                if (iteration == trajectory.length() - 1) {
                    state = States.STOPPING;
                }

                Trajectory.Segment segment = trajectory.getIteration(iteration);

                double currentProgress = iteration / (double) trajectory.length();

                double segmentVelocity = segment.velocity;
                if (isDriftOut && currentProgress < percentToDone && segment.velocity < MIN_START) {
                    segmentVelocity = MIN_START;
                }

                double setpointVelocity = segmentVelocity / maxVelocityInSec;

                double forward = Math.cos(segment.heading) * setpointVelocity;
                double strafe = Math.sin(segment.heading) * setpointVelocity;

                if (currentProgress > percentToDone && segment.velocity < MIN_VEL) {
                    state = States.STOPPING;
                }

                setpointPos = setpoint.getSetpoint(currentProgress);

                yawError = setpointPos - drive.getGyro().getAngle();
                double yaw;

                yaw = yawError * yawKp;

                // if (openLoop > 1d || strafe > 1d) logger.warn("openLoop = {} strafe = {}", openLoop, strafe);

                drive.drive(forward, strafe, yaw);
                iteration++;
                break;
            case STOPPING:
                drive.setDriveMode(SwerveDrive.DriveMode.OPEN_LOOP);
                logState();
                state = States.STOPPED;
                break;
            case STOPPED:
                logState();
                notifier.close();
                break;
        }
    }

    private void logState() {
        // logger.info("{}", state);
    }

    private void logInit() {
    /* logger.info(
        "Path start yawKp = {} yawDelta = {} maxVelocity in/s = {}",
        yawKp,
        yawDelta,
        maxVelocityInSec); */
    }

    public double getYawError() {
        return yawError;
    }

    public double getSetpointPos() {
        return setpointPos;
    }

    public void interrupt() {
        // logger.info("interrupted");
        state = States.STOPPED;
    }
}