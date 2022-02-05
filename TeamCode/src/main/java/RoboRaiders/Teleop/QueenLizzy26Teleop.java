package RoboRaiders.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;


import RoboRaiders.Robot.QueenLizzy26;

/**
 *  Created by Steve Kocik
 */

@TeleOp(name="Teleop: queenLizzy26")
//@Disabled

public class QueenLizzy26Teleop extends OpMode {

    public QueenLizzy26 robot = new QueenLizzy26();



    /* Define variables */
    double LeftBack;   // Power for left back motor
    double RightBack;  // Power for right back motor
    double LeftFront;  // Power for left front motor
    double RightFront; // Power for right front motor
    double maxpwr;     // Maximum power of the four motors
    double powermultiplyer = 1.0;

    public boolean prevStateRightB = false;
    public boolean curStateRightB = false;

    public boolean curStateUDpad = false;
    public boolean curStateXbutton = false;

    public boolean curStateDDpad = false;

    public boolean prevStateLDpad = false;
    public boolean curStateLDpad = false;






    double rTrigger = 0.0;
    double lTrigger = 0.0;

    boolean xButton = false;
    boolean yButton = false;
    boolean bButton = false;
    boolean aButton = false;
    boolean rBumper2 = false;

    double rTrigger2 = 0.0;
    double lTrigger2 = 0.0;

    @Override
    public void init() {

        robot.initialize(hardwareMap);

        telemetry.addData("Initialized", true);
        telemetry.update();
    }

    @Override
    public void start() {

        //   robot.initializeServosTeleOp();
    }

    @Override
    public void loop() {

        // "Mecanum Drive" functionality
        LeftBack = gamepad1.right_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x;
        RightBack = -gamepad1.right_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x;
        LeftFront = gamepad1.right_stick_y - gamepad1.right_stick_x + gamepad1.left_stick_x;
        RightFront = -gamepad1.right_stick_y + gamepad1.right_stick_x + gamepad1.left_stick_x;

        curStateRightB = gamepad1.b;
        rTrigger = gamepad1.right_trigger;
        lTrigger = gamepad1.left_trigger;

        curStateDDpad = gamepad2.dpad_down;
        curStateUDpad = gamepad2.dpad_up;
        curStateLDpad = gamepad2.dpad_left;
        curStateXbutton = gamepad2.x;

        xButton = gamepad2.x;
        yButton = gamepad2.y;
        bButton = gamepad2.b;
        aButton = gamepad2.a;
        rBumper2 = gamepad2.right_bumper;
        rTrigger2 = gamepad2.right_trigger;
        lTrigger2 = gamepad2.left_trigger;

        //We are normalizing the motor powers
        maxpwr = findMaxPower((double)LeftBack, (double)LeftFront, (double)RightBack, (double)RightFront);

        LeftBack = LeftBack / maxpwr;
        RightBack = RightBack / maxpwr;
        LeftFront = LeftFront / maxpwr;
        RightFront = RightFront / maxpwr;

        LeftBack = (float) scaleInput(LeftBack);
        RightBack = (float) scaleInput(RightBack);
        LeftFront = (float) scaleInput(LeftFront);
        RightFront = (float) scaleInput(RightFront);

        //This toggles on the halving of the power either when its pressed down and it wasn't previously pressed, or when it wasn't pressed but was previously pressed
        if ((curStateRightB && !prevStateRightB) || (!curStateRightB && prevStateRightB)) {
            prevStateRightB = true;
            powermultiplyer = .5;

            telemetry.addLine().addData("Drive Motors are set to: Half Power", true);

        }
        //This is to toggles off the halving of the power
        else if (curStateRightB && prevStateRightB) {
            prevStateRightB = false;
            powermultiplyer = 1.0;

            telemetry.addLine().addData("Drive Motors are set to: Half power", false);
        }

        robot.setDriveMotorPower(LeftFront * 0.95 * powermultiplyer,
                RightFront * 0.95 * powermultiplyer,
                LeftBack * 0.95 * powermultiplyer,
                RightBack * 0.95 * powermultiplyer);

        //Set the motor power for the carousel based on either the right or left trigger
        if (rTrigger != 0.0) {
            robot.setCarouselMotorPower(rTrigger * .75);
            telemetry.addLine("Carousel Spinning Direction: CounterClockwise");
            telemetry.addLine("Carousel: Blue Side");

        } else if (lTrigger != 0.0) {
            robot.setCarouselMotorPower(lTrigger * .75 * -1);
            telemetry.addLine("Carousel Spinning Direction: Clockwise");
            telemetry.addLine("Carousel: Red Side");

        } else if (lTrigger == 0.0 && rTrigger == 0.0) {
            robot.setCarouselMotorPower(0.0);
        }

        //Set the position for the servos based on D-Pad buttons

        if (curStateDDpad){
            robot.scoopMove.setPosition(1.0);

        }


        if (curStateUDpad){
            robot.scoopMove.setPosition(0.0);



        }


        if (rTrigger2 != 0.0){
            robot.scoop.setPosition(1.0);
        }
        else if (lTrigger2 != 0.0){
            robot.scoop.setPosition(-1.0);
        }
        else if (lTrigger2 == 0.0 && rTrigger2 == 0.0){
            robot.scoop.setPosition(0.5);
            telemetry.addLine().addData("Neither direction is in use", lTrigger2);

        }


        if ((curStateLDpad && !prevStateLDpad) || (!curStateLDpad && prevStateLDpad)) {
            robot.scoopDoor.setPosition(1.0);


        } else{
            robot.scoopDoor.setPosition(0.0);
        }

        if (curStateXbutton) {

            robot.depositDoor.setPosition(1.0);

        } else{
            robot.depositDoor.setPosition(0.0);
        }

            telemetry.addLine().addData("Left Front:", LeftFront * 0.95 * powermultiplyer);
            telemetry.addLine().addData("Right Front", RightFront * 0.95 * powermultiplyer);
            telemetry.addLine().addData("Left Rear", LeftBack * 0.95 * powermultiplyer);
            telemetry.addLine().addData("Right Rear", RightBack * 0.95 * powermultiplyer);

            telemetry.addLine().addData("right stick X:", gamepad1.right_stick_x);
            telemetry.addLine().addData("left stick X:", gamepad1.left_stick_x);
            telemetry.addLine().addData("left stick Y:", gamepad1.left_stick_y);
            telemetry.update();


            if (yButton) {
                robot.depositBrace.setPosition(0.71);
                robot.depositMove.setPosition(0.73);

            } else if (aButton) {
                robot.depositBrace.setPosition(0.50);
                robot.depositMove.setPosition(1.0);

            } else if (bButton) {
                robot.depositBrace.setPosition(0.62);
                robot.depositMove.setPosition(0.90);

            } else if (rBumper2) {
                robot.depositBrace.setPosition(1.0);
                robot.depositMove.setPosition(0.0);

            }

            telemetry.addLine().addData("the DEPOSIT servo is ", robot.depositMove);
            telemetry.addLine().addData("the BRACE servo is ", robot.depositBrace);

        }




    @Override
    public void stop() {

    }

    /**
     * scaleInput will attempt to smooth or scale joystick input when driving the
     * robot in teleop mode.  By smoothing the joystick input more controlled movement
     * of the robot will occur, especially at lower speeds.
     * <br><br>
     * To scale the input, 16 values are used that increase in magnitude, the algorithm
     * will determine where the input value roughly falls in the array by multiplying it
     * by 16, then will use the corresponding array entry from the scaleArray variable to
     * return a scaled value.
     * <br><br>
     * <b>Example 1:</b> dVal (the input value or value passed to this method) is set to 0.76
     * <br>
     * Stepping through the algorithm
     * <ol>
     * <li> 0.76*16 = 12.16, but because we cast the calculations as an integer (int)
     * we lose the .16 so the value just is 12, variable index now contains 12.  <b>Note:</b>
     * the index variable will tell us which of the array entries in the scaleArray array to
     * use.</li>
     * <li> Check if the index is negative (less than zero), in this example the
     * variable index contains a positive 12</li>
     * <li> Check if the variable index is greater than 16, this is done so the
     * algorithm does not exceed the number of entries in the scaleArray array</li>
     * <li> Initialize the variable dScale to 0.0 (not really needed but we are
     * just being safe)</li>
     * <li> If dVal (value passed to this method) was initially negative, then
     * set the variable dScale to the negative of the scaleArray(index), in this example
     * dVal was initially 0.76 so not negative</li>
     * <li> If dVal (value passed to this method) was initially positive, then
     * set the variable dScale to the scaleArray(index), since index is 12, then
     * scaleArray(12) = 0.60.  <b>Remember, in java the first array index is 0,
     * this is why scaleArray(12) is not 0.50</b></li>
     * <li> Return the dScale value (0.60)</li>
     * </ol>
     * <p>
     * <br><br>
     * <b>Example 2</b> dVal (the input value or value passed to this method) is set to -0.43
     * <br>
     * Stepping through the algorithm
     * <ol>
     * <li> -0.43*16 = -6.88, but because we cast the calculations as an integer (int)
     * we lose the .88 so the value just is -6, variable index now contains -6.  <b>Note:</b>
     * the index variable will tell us which of the array entries in the scaleArray array to
     * use.</li>
     * <li> Check if the index is negative (less than zero), in this example the
     * variable index is negative, so make the negative a negative (essentially
     * multiplying the variable index by -1, the variable index now contains 6</li>
     * <li> Check if the variable index is greater than 16, this is done so the
     * algorithm does not exceed the number of entries in the scaleArray array</li>
     * <li> Initialize the variable dScale to 0.0 (not really needed but we are
     * just being safe)</li>
     * <li> If dVal (value passed to this method) was initially negative, then
     * set the variable dScale to the negative of the scaleArray(index), in this example
     * dVal was initially -0.43, so make sure to return a negative value of scaleArray(6).
     * scaleArray(6) is equal to 0.18 and the negative of that is -0.18 <b>Remember,
     * in java the first array index is 0, this is why scaleArray(6) is not 0.15</b></li>
     * <li> Return the dScale value (-0.18)</li>
     * </ol>
     *
     * @param dVal the value to be scaled -between -1.0 and 1.0
     * @return the scaled value
     * <B>Author(s)</B> Unknown - copied from internet
     */
    double scaleInput(double dVal) {
        // in the floats.

        double[] scaleArray = {0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
                0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00};

        // get the corresponding index for the scaleInput array.
        int index = (int) (dVal * 16.0);

        // index should be positive.
        if (index < 0) {
            index = -index;
        }

        // index cannot exceed size of array minus 1.
        if (index > 16) {
            index = 16;
        }

        // get value from the array.
        double dScale = 0.0;
        if (dVal < 0) {
            dScale = -scaleArray[index];
        } else {
            dScale = scaleArray[index];
        }

        // return scaled value.
        return dScale;
    }

    /**
     * findMaxPower - finds the maximum power of four power values
     *
     * @param pwr1 first power
     * @param pwr2 second power
     * @param pwr3 third power
     * @param pwr4 fourth power
     * @return maximum power of the four values
     * <B>Author(s):</B> Jason Sember and Steeeve
     */
    double findMaxPower(double pwr1, double pwr2, double pwr3, double pwr4) {

        double maxpwrA = Math.max(Math.abs(pwr1), Math.abs(pwr2));
        double maxpwrB = Math.max(Math.abs(pwr3), Math.abs(pwr4));
        double maxpwr = Math.max(Math.abs(maxpwrA), Math.abs(maxpwrB));

        if (maxpwr > 1.0) {

            return maxpwr;
        } else {

            return 1;
        }


    }


}