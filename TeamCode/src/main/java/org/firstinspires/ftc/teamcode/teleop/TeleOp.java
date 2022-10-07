package org.firstinspires.ftc.teamcode.teleop;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp (name = "Test Code")
public class TeleOp extends OpMode {
    protected DcMotor frontLeft;
    protected DcMotor frontRight;
    //protected DcMotor backLeft;
    //protected DcMotor backRight;
    @Override
    public void init() {

        //backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        //backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        //frontRight.setDirection(DcMotor.Direction.FORWARD);
        //backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {


        double x = -gamepad1.left_stick_x;
        double y = -gamepad1.left_stick_y;
        double z = gamepad1.right_stick_x;

        double multiplier = 1;
        if (gamepad1.left_trigger > 0.1) {
            multiplier *= 0.55;
        }
        if (gamepad1.right_trigger > 0.1) {
            multiplier *= 0.55;
        }
        double v1 = Range.clip(y - x + z, -multiplier, multiplier);
        double v2 = Range.clip(y + x - z, -multiplier, multiplier);
        //double v3 = Range.clip(y + x + z, -multiplier, multiplier);
        //double v4 = Range.clip(y - x - z, -multiplier, multiplier);

        frontLeft.setPower(v1);
        frontRight.setPower(v2);
        //backLeft.setPower(v3);
        //backRight.setPower(v4);
    }
}
