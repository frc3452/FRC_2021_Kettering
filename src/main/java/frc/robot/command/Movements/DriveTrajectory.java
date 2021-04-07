package frc.robot.command.Movements;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.DriveSubsystem;
import frc.robot.RobotContainer;

public class DriveTrajectory extends CommandBase {
 
    private static final DriveSubsystem DRIVE = RobotContainer.DRIVE;
    private final String name;
    private final double targetYaw;
    private final boolean isDriftOut;
  
    public DriveTrajectory(String name, double targetYaw) {
      this(name, targetYaw, true);
    }
  
    public DriveTrajectory(String name, double targetYaw, boolean isDriftOut) {
      this.name = name;
      this.targetYaw = targetYaw;
      this.isDriftOut = isDriftOut;
      // requires(DRIVE);
    }
  
    @Override
    public void initialize() {
      DRIVE.startPath(name, targetYaw, isDriftOut);
    }
  
    @Override
    public boolean isFinished() {
      return DRIVE.isPathFinished();
    }
  
    /* @Override
    public void interrupted() {
      DRIVE.interruptPath();
    } */

}
