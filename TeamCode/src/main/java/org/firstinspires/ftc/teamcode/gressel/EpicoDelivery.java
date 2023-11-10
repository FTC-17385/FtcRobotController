package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "EpicoDelivery", group = "Autonomous")
public class EpicoDelivery extends LinearOpMode {

    private Servo wristServo;
    private Servo leftServo;
    private Servo rightServo;

    // Wrist positions
    public static double PICKUP_POSITION = 0.7;
    // Gripper positions
    public static double LEFT_SERVO_OPEN = 0.35;
    public static double RIGHT_SERVO_OPEN = 0.25;

    @Override
    public void runOpMode() {
        // Initialize the wrist and gripper servos
        wristServo = hardwareMap.servo.get("wristServo");
        leftServo = hardwareMap.servo.get("leftServo");
        rightServo = hardwareMap.servo.get("rightServo");

        // Wait for the game to start (driver presses "Start" on the driver station)
        waitForStart();

        // Move the wrist to the pickup position
        wristServo.setPosition(PICKUP_POSITION);

        // Open the gripper
        leftServo.setPosition(LEFT_SERVO_OPEN);
        rightServo.setPosition(RIGHT_SERVO_OPEN);

        // Add any additional autonomous logic here
    }
}
