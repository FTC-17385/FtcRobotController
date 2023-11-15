package org.firstinspires.ftc.teamcode.kuykendall;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

// no @TeleOp(name="WristTest", group="Test")
@TeleOp
@Config
public class wristtest extends LinearOpMode {
@Disabled
    // Servo
    private Servo wristServo;

    // FTC Dashboard
    private FtcDashboard dashboard;
    private TelemetryPacket packet;

    // Wrist positions (making these public and static allows them to be modified from the Dashboard's config)
    public static double PICKUP_POSITION = 0.8;
    public static double DROPOFF_POSITION = 0.15;

    @Override
    public void runOpMode() {

        // Hardware mapping
        wristServo = hardwareMap.get(Servo.class, "wristServo");

        // FTC Dashboard initialization
        dashboard = FtcDashboard.getInstance();
        packet = new TelemetryPacket();

        waitForStart();

        while (opModeIsActive()) {
            // Controls for the wrist servo
            if (gamepad1.a) {
                wristServo.setPosition(PICKUP_POSITION);
            } else if (gamepad1.b) {
                wristServo.setPosition(DROPOFF_POSITION);
            }

            // Send telemetry to the dashboard
            String currentPosition = wristServo.getPosition() == PICKUP_POSITION ? "Pickup" : "Dropoff";
            packet.put("Wrist Servo Position", currentPosition);
            dashboard.sendTelemetryPacket(packet);
        }
    }
}
