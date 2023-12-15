package org.firstinspires.ftc.teamcode.Scott;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.shared.GlobalState;
import org.firstinspires.ftc.teamcode.shared.MotionHardware;
import org.firstinspires.ftc.teamcode.shared.VisionHardware;
import org.firstinspires.ftc.teamcode.shared.VisionHardware.PropPosition;

@Config
@Autonomous(name = "Auto - RA Left", group = "Auto")
public class AutoRALeft extends LinearOpMode {

    MotionHardware robot = new MotionHardware(this);
    VisionHardware vision = new VisionHardware(this, GlobalState.ALLIANCE_POS.LEFT);

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
                case LEFT:
                    robot.moveRobot(.5, -18, 10);
                    sleep(500);
                    robot.turnRobot(MotionHardware.Direction.RIGHT, 16, .5, 10);
                    sleep(500);
                    robot.moveRobot(.5, -8, 10);
                    sleep(500);
                    sleep(500);
                    robot.moveArm(.5, 10, 10);
                    sleep(500);
                    robot.wristDown();
                    sleep(500);
                    robot.gripperOpen();
                    sleep(500);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.moveRobot(.5, -5, 10);
                    break;
                    /*robot.moveRobot(.5, -33, 10);
                    sleep(500);
                    robot.turnRobot(MotionHardware.Direction.RIGHT, 7, .5, 10);
                    sleep(500);
                    robot.moveRobot(.5, 10, 10);
                    sleep(500);
                    robot.moveArm(.5, -5, 10);
                    sleep(500);
                    robot.moveRobot(.5, -10, 10);
                    sleep(500);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.wristDown();
                    sleep(500);
                    robot.gripperOpen();
                    sleep(500);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.moveRobot(.5, -5, 10);
                    break;*/
                case MIDDLE:
                    robot.moveRobot(.5, -33, 10);
                    sleep(500);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    robot.wristDown();
                    sleep(750);
                    robot.gripperOpen();
                    sleep(500);
                    robot.moveArm(.5, 20, 10);
                    sleep(500);
                    break;
                default:
                    robot.moveRobot(.5, -18, 10);
                    sleep(500);
                    robot.moveArm(.5, 10, 10);
                    sleep(500);
                    robot.wristDown();
                    sleep(500);
                    robot.turnRobot(MotionHardware.Direction.LEFT, 16, .5, 10);
                    sleep(500);
                    robot.moveRobot(.5, 10, 10);
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