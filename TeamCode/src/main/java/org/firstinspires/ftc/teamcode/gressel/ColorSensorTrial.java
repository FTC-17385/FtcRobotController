package org.firstinspires.ftc.teamcode.gressel;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
@TeleOp
public class ColorSensorTrial extends OpMode {
@Disabled
    // Declare a ColorSensor object
    private ColorSensor colorSensor;

    @Override
    public void init() {
        // Initialize the color sensor using the hardware map
        colorSensor = hardwareMap.get(ColorSensor.class, "color_sensor");

        // You should replace "color_sensor" with the actual name of your color sensor in your hardware configuration.
    }

    @Override
    public void loop() {
        // Read color sensor data
        int redValue = colorSensor.red();
        int greenValue = colorSensor.green();
        int blueValue = colorSensor.blue();
        int alphaValue = colorSensor.alpha();

        // Do something with the color sensor data
        // For example, you can print the values to the telemetry
        telemetry.addData("Red", redValue);
        telemetry.addData("Green", greenValue);
        telemetry.addData("Blue", blueValue);
        telemetry.addData("Alpha", alphaValue);

        telemetry.update();
    }
}
