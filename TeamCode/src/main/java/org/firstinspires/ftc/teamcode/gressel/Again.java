package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name = "Again", group = "Autonomous")
public class Again extends OpMode {

    private Servo wristServo;
    private Servo leftServo;
    private Servo rightServo;

    public static double PICKUP_POSITION = 0.7;
    public static double LEFT_SERVO_OPEN = 0.35;
    public static double RIGHT_SERVO_OPEN = 0.25;

    @Override
    public void init() {
        wristServo = hardwareMap.get(Servo.class, "wristServo");
        leftServo = hardwareMap.get(Servo.class, "leftServo");
        rightServo = hardwareMap.get(Servo.class, "rightServo");

        wristServo.setPosition(PICKUP_POSITION); // Move the wrist to the pickup position initially
        leftServo.setPosition(LEFT_SERVO_OPEN);  // Open the gripper
        rightServo.setPosition(RIGHT_SERVO_OPEN);

        // Add any other initialization or setup code for your autonomous here
    }

    @Override
    public void loop() {
        // Your autonomous logic goes here
        // This is where you can add movements or actions your robot should perform
        // after moving the wrist and opening the gripper.
    }
}

