package frc.robot.subsystem;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {

    private TalonFX leftMotor;
    // private TalonFX rightMotor;

    public ShooterSubsystem() {
        this.leftMotor = new TalonFX(Constants.SHOOTER_LEFT_PORT);
        this.leftMotor.configFactoryDefault();
        // this.rightMotor = new TalonFX(Constants.SHOOTER_RIGHT_PORT);

        // rightMotor.setInverted(true);
        leftMotor.setInverted(false);

        //Can happen in initialization
        leftMotor.config_kP(0, .05);
        leftMotor.config_kF(0, 0.0499999523);
        ErrorCode errorCode = leftMotor.config_kD(0, .9);
        //The encoder unit converter linked below can help convert PID/control units from sensible things like
        // volts / RPM
        // to the less sensible
        // control_units(-1023 -> 1023) / ticks_per_100_ms

        //If you had multiple PID tunings, configure each slot
        //(above, you're configuring slot 0), and swap between which slot
        //is used with
        //leftMotor.selectProfileSlot(i, 0);
        //If you really want to make sure those values get set, you can use the return from most CTRE
        //methods to check the error
    }

    public void launch(double rpm) {
//        System.out.printf("Launching: %5.3f%n", rpm);
        System.out.println(String.format("Launching: %5.3f", rpm));

        // Units are NOT rpm for velocity control in CTRE...
        // Units are
        // Encoder_ticks / 100ms
        // 1 Revolution = 4096 || 1024 || whateverTheEncoderSpecSays
        //I'd use this file I made or implement it here
        //https://github.com/maccopacco/frc_lib/blob/ee8b752008bd8a98cd30727a2550575e910e7537/src/main/java/frclib/util/math/EncoderUnitConverter.java#L3
        leftMotor.set(ControlMode.Velocity, rpm);
    }

    public void openLoop(double power) {
        leftMotor.set(ControlMode.PercentOutput, power);
    }

    public void stop() {
        leftMotor.set(ControlMode.PercentOutput, 0);
    }

}