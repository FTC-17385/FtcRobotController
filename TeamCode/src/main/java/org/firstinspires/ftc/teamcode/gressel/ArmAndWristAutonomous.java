package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "ArmAndWristAutonomous", group = "Autonomous")
public class ArmAndWristAutonomous extends LinearOpMode {

    private DcMotor armMotor;
    private Servo wristServo;

    // Constants for positions
    private static final double DROPOFF_POSITION = 0.15;
    private static final double ARM_DOWN_POWER = -0.2; // Adjust this value for slower arm movement

    @Override
    public void runOpMode() {
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        wristServo = hardwareMap.get(Servo.class, "wristServo");

        armMotor.setDirection(DcMotor.Direction.FORWARD);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        wristServo.setPosition(DROPOFF_POSITION);

        waitForStart();

        // Move the arm down to position 0.0
        armMotor.setPower(ARM_DOWN_POWER);

        while (opModeIsActive() && !isStopRequested()) {
            // Wait for the arm to reach the desired position
            if (armMotor.getCurrentPosition() <= 0) {
                armMotor.setPower(0);
                break;
            }
        }

        // Stop the arm
        armMotor.setPower(0);

        // Optionally, you can add more code here for additional actions in the autonomous routine.

        // The OpMode should end automatically after this point.
    }
}
