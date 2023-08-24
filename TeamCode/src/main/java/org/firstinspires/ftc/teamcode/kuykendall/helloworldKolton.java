package org.firstinspires.ftc.teamcode.kuykendall;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
@TeleOp()
public class helloworldKolton extends OpMode {
    /* This tells the code to be used in Teleop mode, and calls to the file class. */
    @Override
    public void init() {
        telemetry.addData("Hello", "Kolton");
    }
/* This is the initilisatin point of the code */
    @Override
    public void loop() {

    }
}