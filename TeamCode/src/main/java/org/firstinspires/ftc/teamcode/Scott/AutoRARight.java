package org.firstinspires.ftc.teamcode.Scott;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.shared.GlobalState;
import org.firstinspires.ftc.teamcode.shared.MotionHardware;
import org.firstinspires.ftc.teamcode.shared.VisionHardware;
import org.firstinspires.ftc.teamcode.shared.VisionHardware.PropPosition;

@Config
@Autonomous(name = "Auto - RA Right", group = "Auto")
public class AutoRARight extends LinearOpMode {

    MotionHardware robot = new MotionHardware(this);
    VisionHardware vision = new VisionHardware(this, GlobalState.ALLIANCE_POS.RIGHT);

    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();
        vision.init();
        waitForStart();


        while (opModeIsActive()) {
            PropPosition propPosition = vision.detectProp();
            telemetry.addData("Found Pixel: ", propPosition.toString());
            telemetry.update();
            sleep(5000);


            // Center Strike Line
            // - Forward: 31.75
            // - Reverse: -44.75

            switch (propPosition) {
                case RIGHT:
                    robot.moveRobot(.5, -33, 10);
                    sleep(1000);
                    robot.turnRobot(MotionHardware.Direction.LEFT, 5, .5, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.moveArm(.5, 10, 10);
                    sleep(1000);
                    robot.moveRobot(.5, 7, 10);
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.moveRobot(.5, -2, 10);
                    break;
                case MIDDLE:
                    robot.moveRobot(.5, -35, 10);
                    sleep(1000);
                    robot.moveArm(.5, 8, 10);
                    sleep(1000);
                    robot.wristDown();
                    sleep(1000);
                    robot.gripperOpen();
                    sleep(1000);
                    robot.moveArm(.5, 20, 10);
                    break;
                default:
                    robot.moveRobot(.5, -20, 10);
                    sleep(500);
                    robot.moveArm(.5, 10, 10);
                    robot.wristDown();
                    sleep(500);
                    robot.turnRobot(MotionHardware.Direction.RIGHT, 16, .5, 10);
                    sleep(500);

                    robot.gripperOpen();
                    sleep(500);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.moveRobot(.5, -5, 10);
                    break;

            }
            break;
        }
    }
}