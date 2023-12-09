package org.firstinspires.ftc.teamcode.Bot2;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.shared.MotionHardware2;
import org.firstinspires.ftc.teamcode.shared.VisionHardware;

public class AutoBALeft2 {
    @Config
    @Autonomous(name = "Auto - BA Left2", group = "Auto")
    public class AutoBARight extends LinearOpMode {

        MotionHardware2 robot = new MotionHardware2(this);
        VisionHardware vision = new VisionHardware(this);

        @Override
        public void runOpMode() throws InterruptedException {
            robot.init();
            vision.init();

            waitForStart();

            while (opModeIsActive()) {
                VisionHardware.PropPosition propPosition = vision.detectProp();
            }
        }
    }
}
