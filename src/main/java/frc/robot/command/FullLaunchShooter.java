package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.Command;

public class FullLaunchShooter extends CommandGroupBase {
    private CommandGroupBase auto;
    private boolean done = false;

    public FullLaunchShooter(double speed) {
        addCommands(
                new LaunchShooter(speed).withTimeout(5),
                new MoveCellLift(.45).withTimeout(3),
                new MoveConveyor(-.35).withTimeout(3)
        );
    }

    public void initialize() {

    }

    @Override
    public void addCommands(Command... commands) {
        auto = parallel(commands);
    }

    public void execute() {
        if (!done) {
            auto.schedule();
            done = true;
        }
    }

    public boolean isFinished() {
        return done;
    }

    public void end(boolean interrupted) {
        done = false;
    }

    ////////


}