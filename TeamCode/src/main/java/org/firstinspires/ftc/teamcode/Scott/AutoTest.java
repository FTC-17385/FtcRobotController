package org.firstinspires.ftc.teamcode.Scott;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.shared.RobotHardware;


@Autonomous(name = "Autonomous Test", group = "Auto")
public class AutoTest extends LinearOpMode {
    RobotHardware robot       = new RobotHardware(this);

    @Override
    public void runOpMode() throws InterruptedException {


        robot.init();
        waitForStart();
        while (opModeIsActive()) {
            robot.driveRobot(2);
        }
        sleep(50);
    }

}
