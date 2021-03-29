package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CellLiftSubsystem extends SubsystemBase {
    
    private TalonSRX cellliftleftMotor;

    public CellLiftSubsystem() {
        this.cellliftleftMotor = new TalonSRX(Constants.CELL_LIFT_PORT);

        cellliftleftMotor.setInverted(true);
        //rightMotor.setInverted(true);

        // rightMotor.follow(leftMotor);
    }

    public void Forward(double power) {
        cellliftleftMotor.set(ControlMode.PercentOutput, -power);
    }

    public void Backward(double power) {
        cellliftleftMotor.set(ControlMode.PercentOutput, power);
    }

    public void stop() {
        cellliftleftMotor.set(ControlMode.PercentOutput, 0);
    }

}