package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class SensorTests extends OpMode {

    ColorSensor color_sensor;
    TouchSensor touch_sensor;
    DistanceSensor range_sensor;

    public void init() {
        color_sensor = hardwareMap.colorSensor.get("Color");
        touch_sensor = hardwareMap.touchSensor.get("Touch");
        range_sensor = hardwareMap.get(DistanceSensor.class, "sensor_range");

    }


    @Override
    public void loop() {

        telemetry.addData("Color", color_sensor.red());
        telemetry.addData("Color", color_sensor.blue());
        telemetry.addData("Touch", touch_sensor.getValue());
        telemetry.addData("Range",range_sensor.getDistance());
        telemetry.update();


    }
}