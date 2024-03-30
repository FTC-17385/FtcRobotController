package org.firstinspires.ftc.teamcode.comp.auto;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.shared.GlobalConfig;
import org.firstinspires.ftc.teamcode.shared.GlobalConfig.ALLIANCE_POS;
import org.firstinspires.ftc.teamcode.shared.GlobalConfig.ALLIANCE_COL;
import org.firstinspires.ftc.teamcode.shared.MotionHardware2;
import org.firstinspires.ftc.teamcode.shared.MotionHardware2.Direction;
import org.firstinspires.ftc.teamcode.shared.VisionHardware2;
import org.firstinspires.ftc.teamcode.shared.VisionHardware2.PropPosition;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Config
@Autonomous(name = "Auto - RA Right", group = "Auto")
public class AutoRR extends LinearOpMode {

    public ALLIANCE_POS alliancePos = ALLIANCE_POS.RIGHT;
    public ALLIANCE_COL allianceCol = ALLIANCE_COL.RED;
    public GlobalConfig globalConfig = new GlobalConfig(GlobalConfig.AUTONOMOUS_DELIVERY_MODES.DROPPER);

    private static int DESIRED_TAG_ID = 0;

    private static int DESIRED_DISTANCE = 6;

    private AprilTagDetection desiredTag = null;
    MotionHardware2 robot = new MotionHardware2(this, globalConfig);
    VisionHardware2 vision = new VisionHardware2(this, alliancePos);
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();
        vision.init();

        robot.moveArm(.5, 5, 5);

        waitForStart();

        //TODO In parking code, drop arms to TeleOp pickup position

        // Center Strike Line
        // - Forward: 31.75
        // - Reverse: -44.75
        while(opModeIsActive()) {
            vision.resetExposure();
            PropPosition propPosition = vision.detectProp();
            // Left = 4, Middle = 5, Right = 6

            switch(propPosition) {
                case MIDDLE:
                    // Dropper Mode
                    robot.moveRobot(.5, -16, 10);
                    robot.dropPixel();
                    sleep(1000);
                    robot.moveRobot(.5, 3, 10);
                    robot.turnRobot(Direction.LEFT, 12, .5, 10);
                    robot.moveRobot(.5, 22, 10);
                    robot.moveArmMotorToPosition(-1200, 10);
                    robot.moveRobot(.5, -4, 10);
                    robot.strafeWithTime(.5, 180, 3);
                    break;
                case RIGHT:
                    //Dropper Mode
                    robot.moveRobot(.5, -14, 10);
                    robot.turnRobot(Direction.RIGHT, 6, .5, 10);
                    robot.dropPixel();
                    robot.moveRobot(.5, 4, 10);
                    robot.turnRobot(Direction.LEFT, 12, .5, 10);
                    robot.moveRobot(.5, 3, 10);
                    robot.turnRobot(Direction.LEFT, 5.5, .5, 10);
                    robot.moveRobot(.5, 23, 10);
                    robot.moveArmMotorToPosition(-1200, 10);
                    robot.moveRobot(.5, -4, 10);
                    robot.strafeWithTime(.5, 180, 3);
                    break;
                default:
                    //Dropper Mode
                    robot.moveRobot(.5, -14, 10);
                    robot.turnRobot(Direction.LEFT, 8, .5, 10);
                    robot.moveRobot(.5, -4, 10);
                    robot.dropPixel();
                    robot.moveRobot(.5, 3, 10);
                    robot.turnRobot(Direction.LEFT, 5, .5, 10);
                    robot.moveRobot(.5, 10, 10);
                    robot.turnRobot(Direction.RIGHT, 2, .5, 10);
                    robot.moveRobot(.5, 16, 10);
                    robot.moveArmMotorToPosition(-1200, 10);
                    robot.moveRobot(.5, -4, 10);
                    robot.strafeWithTime(.5, 180, 3);
                    break;
            }

            sleep(20);
            break;
        }
    }
}
