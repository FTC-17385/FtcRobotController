package org.firstinspires.ftc.teamcode.Scott;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
//designates this code into autonomous section on the driver hub
public class HelloZach extends OpMode {
    //The code/public class extends OpMode. Everything below is the code
    @Override
    //overrides parent class. public class above, but it goes into public void
    public void init() {
        telemetry.addData("Hello","Zach");
        //Adds string values onto the driver hub. telemetry.addData does that
    }

    @Override
    //overrides public class and void to create what's below
    public void loop (){
//code required to have init and loop. Loop isn't used, so we have to add it at the end to make the code complete
    }
}
