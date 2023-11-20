package org.firstinspires.ftc.teamcode.kuykendall;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

// no @TeleOp(name="PlaneTest", group="Test")
@TeleOp
@Config
public class planetest extends LinearOpMode {

    // Servo
    private Servo planeServo;

    // FTC Dashboard
    private FtcDashboard dashboard;
    private TelemetryPacket packet;

    // Wrist positions (making these public and static allows them to be modified from the Dashboard's config)
    public static double Launch_POSITION = 1;
    public static double HOLD_POSITION = 0;

    @Override
    public void runOpMode() {

        // Hardware mapping
        planeServo = hardwareMap.get(Servo.class, "planeServo");

        // FTC Dashboard initialization
        dashboard = FtcDashboard.getInstance();
        packet = new TelemetryPacket();

        waitForStart();

        while (opModeIsActive()) {
            // Controls for the wrist servo
            if (gamepad1.dpad_up) {
                planeServo.setPosition(Launch_POSITION);
            } else if (gamepad1.dpad_right) {
                planeServo.setPosition(HOLD_POSITION);
            }

            // Send telemetry to the dashboard
            String currentPosition = planeServo.getPosition() == Launch_POSITION ? "Launch" : "Hold";
            packet.put("Wrist Servo Position", currentPosition);
            dashboard.sendTelemetryPacket(packet);
        }
    }
}
