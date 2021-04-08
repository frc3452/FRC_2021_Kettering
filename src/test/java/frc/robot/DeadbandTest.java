package frc.robot;

import org.junit.Test;

public class DeadbandTest {

    @Test
    public void test() {
        final double deadband = 0.1;
        for (double value = -1; value <= 1; value += 0.01) {
            System.out.printf("%f,%f,%f%n", value, Util.applyDeadband(value, deadband), basicDeadband(value, deadband));
        }
        //https://www.desmos.com/calculator/sx0erizmkd
    }

    private double basicDeadband(double input, double deadband) {
        if (Math.abs(input) < deadband) return 0;
        return input;
    }
}
