package org.firstinspires.ftc.teamcode.comp.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.shared.GlobalConfig;
import org.firstinspires.ftc.teamcode.shared.GlobalConfig.ALLIANCE_POS;
import org.firstinspires.ftc.teamcode.shared.MotionHardware2;
import org.firstinspires.ftc.teamcode.shared.MotionHardware2.Direction;
import org.firstinspires.ftc.teamcode.shared.VisionHardware2;
import org.firstinspires.ftc.teamcode.shared.VisionHardware2.PropPosition;

@Config
@Autonomous(name = "Auto - BA Right", group = "Auto")
public class AutoBR extends LinearOpMode {

    public ALLIANCE_POS alliancePos = ALLIANCE_POS.RIGHT;

    public GlobalConfig globalConfig = new GlobalConfig(GlobalConfig.AUTONOMOUS_DELIVERY_MODES.DROPPER);
    MotionHardware2 robot = new MotionHardware2(this, globalConfig);
    VisionHardware2 vision = new VisionHardware2(this, alliancePos);
    @Override
    public void runOpMode() throws InterruptedException {
        robot.init();
        vision.init();

        robot.moveArm(.5, 5, 5);

        waitForStart();

        //TODO Add Park from BR

        // Center Strike Line
        // - Forward: 31.75
        // - Reverse: -44.75
        while(opModeIsActive()) {
            PropPosition propPosition = vision.detectProp();

            switch(propPosition) {
                case MIDDLE:
                    robot.moveRobot(.5, -16, 10);
                    robot.dropPixel();
                    sleep(1000);
                    break;
                case RIGHT:
                    //Dropper Mode
                    robot.moveRobot(.5, -14, 10);
                    robot.turnRobot(Direction.RIGHT, 6, .5, 10);
                    robot.dropPixel();
                    break;
                default:
                    //Dropper Mode
                    robot.moveRobot(.5, -14, 10);
                    robot.turnRobot(Direction.LEFT, 8, .5, 10);
                    robot.moveRobot(.5, -5, 10);
                    robot.dropPixel();
                    break;
            }

            sleep(20);
            break;
        }
    }
}

