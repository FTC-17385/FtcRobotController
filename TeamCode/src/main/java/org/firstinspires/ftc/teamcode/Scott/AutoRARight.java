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
                    robot.moveRobot(.5, -33, 10);
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.wristUp();
                    sleep(1000);
                    robot.gripperClose();
                    break;
                case LEFT:
                    robot.moveRobot(.5, -33, 10);
                    sleep(1000);
                    robot.turnRobot(MotionHardware.Direction.LEFT, 10, 10, 10);
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.wristUp();
                    sleep(1000);
                    robot.gripperClose();
                    break;
                case RIGHT:
                    robot.moveRobot(.5, -33, 10);
                    sleep(1000);
                    robot.turnRobot(MotionHardware.Direction.RIGHT, 10, 10, 10);
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.wristUp();
                    sleep(1000);
                    robot.gripperClose();
                    break;
                case MIDDLE:
                    robot.moveRobot(.5, -33, 10);
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.wristUp();
                    sleep(1000);
                    robot.gripperClose();
                    break;

            }
            break;
        }
    }
}