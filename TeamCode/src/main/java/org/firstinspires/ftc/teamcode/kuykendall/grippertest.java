package org.firstinspires.ftc.teamcode.kuykendall;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="GripperTest", group="Test")
@Config
public class grippertest extends LinearOpMode {

    // Define the servos
    private Servo leftServo;
    private Servo rightServo;

    // Define the servo positions for open and close.
    // These values can be adjusted in the FTC Dashboard.
    public static double LEFT_SERVO_OPEN = 0.35;
    public static double LEFT_SERVO_CLOSE = 0.0;
    public static double RIGHT_SERVO_OPEN = 0.25;
    public static double RIGHT_SERVO_CLOSE = 0.3;

    @Override
    public void runOpMode() {
        // Initialize the servos
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        // Initialize the dashboard
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();

        waitForStart();

        while (opModeIsActive()) {
            // Open gripper when 'A' button is pressed
            if (gamepad1.a) {
                leftServo.setPosition(LEFT_SERVO_OPEN);
                rightServo.setPosition(RIGHT_SERVO_OPEN);
                telemetry.addData("Gripper", "Open");
            }

            // Close gripper when 'B' button is pressed
            if (gamepad1.b) {
                leftServo.setPosition(LEFT_SERVO_CLOSE);
                rightServo.setPosition(RIGHT_SERVO_CLOSE);
                telemetry.addData("Gripper", "Closed");
            }

            telemetry.update();
        }
    }
}
