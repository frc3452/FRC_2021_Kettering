package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CellLiftSubsystem extends SubsystemBase {

    private TalonSRX cellLiftLeft;

    public CellLiftSubsystem() {
        this.cellLiftLeft = new TalonSRX(Constants.CellLift.CELL_LIFT_PORT);
        this.cellLiftLeft.configFactoryDefault();

        cellLiftLeft.setInverted(false);
        // rightMotor.follow(leftMotor);
    }

    public void openLoop(double power) {
        cellLiftLeft.set(ControlMode.PercentOutput, power);
    }

    public void stop() {
        cellLiftLeft.set(ControlMode.PercentOutput, 0);
    }

}