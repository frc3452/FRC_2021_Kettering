/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final int SHOOTER_LEFT_PORT = 51;
    public static final int SHOOTER_RIGHT_PORT = 52;
    public static String SBTabDriverDisplay = "Driver Display";
    public static int autoColumn = 3;

    public static final class CellLift {
        public static final int CELL_LIFT_PORT = 54;

        public static final class CellLiftSpeeds {
            public static final double MOVE_CELL_FORWARD = 0.60;
            public static final double MOVE_CELL_BACKWARDS = -0.25;
        }
    }

    public static final class Intake {
        public static final int INTAKE_PORT = 55;

        public static final class IntakeSpeeds {
            public static final double FORWARDS = -0.65;
            public static final double BACKWARDS = -.25;
            public static final double RELEASE = .35;
        }
    }

    public static final class Conveyor {

        public static final int CONVEYOR_PORT = 53;

        public static final class ConveyorSpeeds {
            public static final double FORWARDS = 0.55;
            public static final double BACKWARDS = -0.25;
        }
    }

}
