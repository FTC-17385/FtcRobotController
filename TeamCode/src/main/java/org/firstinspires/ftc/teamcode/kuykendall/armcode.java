package org.firstinspires.ftc.teamcode.kuykendall;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "ArmCode", group = "TeleOp")
public class armcode extends OpMode {

    private DcMotor armMotor; // Assuming you are using a DC motor for the arm
    private FtcDashboard dashboard;

    @Override
    public void init() {
        // Initialize the arm motor
        armMotor = hardwareMap.get(DcMotor.class, "armMotor");
        armMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER); // Assuming you're not using encoders

        // Initialize the FTC Dashboard
        dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();
    }

    @Override
    public void loop() {
        // Check the gamepad buttons
        if (gamepad1.a) {
            // If "Y" is pressed, move the arm up
            armMotor.setPower(1.0);
        } else if (gamepad1.y) {
            // If "A" is pressed, move the arm down
            armMotor.setPower(-1.0);
        } else {
            // If neither button is pressed, stop the arm
            armMotor.setPower(0.0);
        }

        // Send telemetry data to the FTC Dashboard
        TelemetryPacket packet = new TelemetryPacket();
        packet.put("Arm Power", armMotor.getPower());
        dashboard.sendTelemetryPacket(packet);
    }
}
