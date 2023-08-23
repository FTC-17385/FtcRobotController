package org.firstinspires.ftc.teamcode.kuykendall;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp()
public class helloworldKolton extends OpMode {
    @Override
    public void init() {
        telemetry.addData("Hello", "Kolton");
    }

    @Override
    public void loop() {

    }
}