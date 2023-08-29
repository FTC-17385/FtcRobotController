
package org.firstinspires.ftc.teamcode.Thomas;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous
/* Makes robot move without driver input */
public class HelloBen extends OpMode {

    @Override
    /* This allows a class to inherit behavior close enough and to modify it */
    public void init() { telemetry.addData("Hello","Ben"); }
    @Override
    public void loop() {

    }
}

