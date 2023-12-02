package org.firstinspires.ftc.teamcode.Scott;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.shared.MotionHardware;
import org.firstinspires.ftc.teamcode.shared.VisionHardware;
import org.firstinspires.ftc.teamcode.shared.VisionHardware.PropPosition;

@Config
@Autonomous(name = "Auto - RA Right", group = "Auto")
public class AutoRARight extends LinearOpMode {

    MotionHardware robot = new MotionHardware(this);
    VisionHardware vision = new VisionHardware(this);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();
        vision.init();

        waitForStart();

        while (opModeIsActive()) {
            PropPosition propPosition = vision.detectProp();


            // Center Strike Line
            // - Forward: 31.75
            // - Reverse: -44.75

            switch (propPosition) {
                case UNKNOWN:
                    robot.moveArm(.5, 45, 10);
                    break;

                    /*robot.moveRobot(.5, -35, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.wristUp();
                    sleep(1000);
                    robot.gripperClose();
                    sleep(1000);
                    robot.moveRobot(.5, 35, 10);
                    break*/
                case LEFT:
                    robot.moveArm(.5, 45, 10);
                    break;
                    /*robot.moveRobot(.5, -35, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.wristUp();
                    sleep(1000);
                    robot.gripperClose();
                    sleep(1000);
                    robot.moveRobot(.5, 35, 10);
                    break;*/
                case RIGHT:
                    robot.moveArm(.5, 45, 10);
                    break;
                    /*robot.moveRobot(.5, -35, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.wristUp();
                    sleep(1000);
                    robot.gripperClose();
                    sleep(1000);
                    robot.moveRobot(.5, 35, 10);
                    break;*/
                case MIDDLE:
                    robot.moveArm(.5, 45, 10);
                    break;
                   /* robot.moveRobot(.5, -35, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.wristUp();
                    sleep(1000);
                    robot.gripperClose();
                    sleep(1000);
                    robot.moveRobot(.5, 35, 10);
                    break;*/





            }
            break;
        }
    }
}