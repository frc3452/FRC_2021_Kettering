package frc.robot.command.movements;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.DriveSubsystem;

public class DriveTrajectory extends CommandBase {

    private final DriveSubsystem driveSubsystem;
    private final String name;
    private final double targetYaw;
    private final boolean isDriftOut;

    public DriveTrajectory(DriveSubsystem driveSubsystem, String name, double targetYaw) {
        this(driveSubsystem, name, targetYaw, true);
    }

    public DriveTrajectory(DriveSubsystem driveSubsystem, String name, double targetYaw, boolean isDriftOut) {
        this.driveSubsystem = driveSubsystem;
        this.name = name;
        this.targetYaw = targetYaw;
        this.isDriftOut = isDriftOut;
        addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        driveSubsystem.startPath(name, targetYaw, isDriftOut);
    }

    @Override
    public boolean isFinished() {
        return driveSubsystem.isPathFinished();
    }
  
    /* @Override
    public void interrupted() {
      DRIVE.interruptPath();
    } */

}
