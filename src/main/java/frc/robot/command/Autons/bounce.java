package frc.robot.command.Autons;

import edu.wpi.first.wpilibj2.command.CommandGroupBase;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.command.Movements.DriveDistance;

public class bounce extends CommandGroupBase {

    private CommandGroupBase auto;
    private boolean done = false;

    public bounce() {
        addCommands(
                new DriveDistance(0, 90, 0),
                new DriveDistance(60, 90, 0),
                new DriveDistance(0, 0, 0),
                new DriveDistance(48, 0, 0),
                new DriveDistance(156, 53, 0),
                new DriveDistance(1, 87, 0),
                new DriveDistance(1, 86, 0),
                new DriveDistance(60, 0, 0)
        );
    }

    public void initialize() {

    }

    @Override
    public void addCommands(Command... commands) {
        auto = sequence(commands);
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
}