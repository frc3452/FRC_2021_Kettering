package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystem.CellLiftSubsystem;

public class MoveCellLift extends CommandBase {

    private CellLiftSubsystem celllift;
    private double speed; 
    
    public MoveCellLift(double speed) {
        this.celllift = new CellLiftSubsystem();
        this.speed = speed; 

        addRequirements(celllift); 
    }
    
    public void initialize() {

    }
    
    public void execute() {
        celllift.Forward(speed);
    }

    @Override
    public void end(boolean interrupted) {
        celllift.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}