package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
    
    private TalonFX leftMotor;
    private TalonFX rightMotor;

    public ShooterSubsystem() {
        this.leftMotor = new TalonFX(Constants.SHOOTER_LEFT_PORT);
        this.rightMotor = new TalonFX(Constants.SHOOTER_RIGHT_PORT);

        rightMotor.setInverted(true);
        leftMotor.setInverted(true);
        //rightMotor.setInverted(true);

        // rightMotor.follow(leftMotor);
    }

    public void Forward(double power) {
        leftMotor.set(ControlMode.PercentOutput, -power);
        rightMotor.set(ControlMode.PercentOutput, power);
    }

    public void Backward(double power) {
        leftMotor.set(ControlMode.PercentOutput, power);
        rightMotor.set(ControlMode.PercentOutput, -power);
    }

    public void stop() {
        leftMotor.set(ControlMode.PercentOutput, 0);
        rightMotor.set(ControlMode.PercentOutput, 0);
    }

}