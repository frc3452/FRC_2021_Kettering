package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {

    private VictorSPX intakeLeft;

    public IntakeSubsystem() {
        this.intakeLeft = new VictorSPX(Constants.Intake.INTAKE_PORT);
        // this.intakeLeft.configFactoryDefault();

        intakeLeft.setInverted(false);
    }

    public void openLoop(double power) {
        intakeLeft.set(ControlMode.PercentOutput, power);
    }

    public void stop() {
        intakeLeft.set(ControlMode.PercentOutput, 0);
    }

}