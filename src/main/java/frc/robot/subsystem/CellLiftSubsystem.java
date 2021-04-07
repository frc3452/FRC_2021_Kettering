package frc.robot.subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CellLiftSubsystem extends SubsystemBase {

    private TalonSRX cellliftleftMotor;
    // private TalonSRX conveyorMotor;

    public CellLiftSubsystem() {
        this.cellliftleftMotor = new TalonSRX(Constants.CELL_LIFT_PORT);
        // this.conveyorMotor = new TalonSRX(Constants.CELL_LIFT_PORT);

        cellliftleftMotor.setInverted(true);
        // conveyorMotor.setInverted(true);
        //rightMotor.setInverted(true);

        // rightMotor.follow(leftMotor);
    }

    public void Forward(double power) {
        cellliftleftMotor.set(ControlMode.PercentOutput, -power);
        // double conveyorpower = power /2;
        // conveyorMotor.set(ControlMode.PercentOutput, -conveyorpower);
    }

    public void Backward(double power) {
        cellliftleftMotor.set(ControlMode.PercentOutput, power);
        // double conveyorpower = power * 1.5;
        // conveyorMotor.set(ControlMode.PercentOutput, conveyorpower);
    }

    public void stop() {
        cellliftleftMotor.set(ControlMode.PercentOutput, 0);
        // conveyorMotor.set(ControlMode.PercentOutput, 0);
    }

}