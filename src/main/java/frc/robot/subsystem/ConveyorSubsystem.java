package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ConveyorSubsystem extends SubsystemBase {

    private TalonSRX leftMotor;

    public ConveyorSubsystem() {
        this.leftMotor = new TalonSRX(Constants.CONVEYOR_PORT);

        leftMotor.setInverted(true);
        //rightMotor.setInverted(true);

        // rightMotor.follow(leftMotor);
    }

    public void Forward(double power) {
        leftMotor.set(ControlMode.PercentOutput, -power);
    }

    public void Backward(double power) {
        leftMotor.set(ControlMode.PercentOutput, power);
    }

    public void stop() {
        leftMotor.set(ControlMode.PercentOutput, 0);
    }

}