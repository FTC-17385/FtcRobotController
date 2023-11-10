package org.firstinspires.ftc.teamcode.gressel;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="Planething", group="TeleOp")
public class Planething extends OpMode {

    // Assuming you have a servo named planeServo
    Servo planeServo;

    @Override
    public void init() {
        // Initialize your servo in the hardware map
        planeServo = hardwareMap.servo.get("planeServo");
        planeServo.setPosition(0.3);
    }

    @Override
    public void loop() {
        // Check if the 'B' button is pressed
        if (gamepad1.b) {
            // Move planeServo to position -  1
            planeServo.setPosition(-1.0);
        } else {
            // You might want to set it back to a default position when the button is not pressed
            // Replace 0.5 with your desired default position
            planeServo.setPosition(0.3);
        }
    }
}
