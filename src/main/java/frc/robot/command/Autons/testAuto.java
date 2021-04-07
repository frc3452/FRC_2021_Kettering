package frc.robot.command.Autons;

import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.command.Movements.DriveTrajectory;

public class testAuto extends CommandGroupBase {

    private CommandGroupBase auto;
    private boolean done = false;

    public testAuto() {
        addCommands(
           // new LaunchShooter(17000).withTimeout(7),
           // new MoveCellLift(.75).withTimeout(5),
           // new MoveConveyor(-.5).withTimeout(5)
          new DriveTrajectory("Slalom", 0.0, false)
        );
    }

    public void initialize(){

    }

    @Override
    public void addCommands(Command... commands) {
        auto=parallel(commands);
     }
     public void execute(){
        if(!done){
           auto.schedule();
           done=true;
        }
     }
     public boolean isFinished(){
        return done;
     }
     public void end(boolean interrupted){
        done=false;
     }
}