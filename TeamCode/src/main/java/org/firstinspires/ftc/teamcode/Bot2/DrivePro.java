package org.firstinspires.ftc.teamcode.Bot2;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.shared.MotionHardware;
import org.firstinspires.ftc.teamcode.shared.MotionHardware2;
import org.firstinspires.ftc.teamcode.shared.VisionHardware;

@TeleOp
public class DrivePro extends LinearOpMode {

    /*private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;

    private DcMotor rightLeadScrew;
    private DcMotor leftLeadScrew;
    private DcMotor linearExtension;

    private Servo leftIntakeLifter;
    private Servo rightIntakeLifter;
    private Servo intakeWrist;
    private Servo intake;
    private Servo bucketWrist;
    private Servo leftClimb;
    private Servo rightClimb;
    private Servo planeServo;
    private FtcDashboard dashboard;
    public static double INTAKE_WRIST_UP = 1;
    public static double INTAKE_WRIST_DOWN = 1;
    public static double INTAKE_LIFTER_UP = 1;
    public static double INTAKE_LIFTER_DOWN = 1;
    public static double LEFT_CLIMB_UP_POSITION = 1;
    public static double LEFT_CLIMB_DOWN_POSITION = 1;
    public static double RIGHT_CLIMB_UP_POSITION = 1;

    public static double RIGHT_CLIMB_DOWN_POSITION = 1;
    public static double INTAKE_SPEED = 1;
    public static double driveScale = .3;
    public static double strafeScale = .5;
    public static double rotateScale = .3;
    public static double FAST_ROTATE_SPEED = 1.0;
    public static long TURN_180_TIME_MS = 333;
    private boolean isTurning180 = false;
    private long turnStartTime = 0;
    private TelemetryPacket packet;*/
    MotionHardware2 robot = new MotionHardware2(this);

    @Override
    public void runOpMode() {

        robot.init();

        waitForStart();





    }
}

