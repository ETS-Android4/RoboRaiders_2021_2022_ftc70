/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package RoboRaiders.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import RoboRaiders.Robot.QueenLizzy26;


/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

// START OF KEEP IN MIND
//**********************************************************************************************

    //The Front of the robot is the intake side
    //the back of the robot is the deposit side
    //the left and right sides are determined when looking the direction of the robot (the way the front of the robot is looking)

//**********************************************************************************************
// END OF KEEP IN MIND



// START OF PROGRAM
//**********************************************************************************************

@Autonomous(name="EXAMPLE")
//@Disabled
public class EXAMPLE extends LinearOpMode {
    public QueenLizzy26 stevesRobot;
    double numofticks;

    @Override
    public void runOpMode() {

        // Create an instance of the TestRobot and store it into StevesRobot
        stevesRobot = new QueenLizzy26();

        // Initialize stevesRobot and tell user that the robot is initialized
        stevesRobot.initialize(hardwareMap);
        telemetry.addData("Robot Initialized waiting your command", true);
        telemetry.update();

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

//**********************************************************************************************
// END OF START OF PROGRAM

        // EXAMPLE
        //**********************************************************************************************

        // DRIVE FORWARD

            stevesRobot.resetEncoders();
            stevesRobot.runWithEncoders();
            numofticks = stevesRobot.driveTrainCalculateCounts(1);
            stevesRobot.setDriveMotorPower(-1, 1, -1, 1);

            while (opModeIsActive() && stevesRobot.getSortedEncoderCount() <= numofticks){
                sleep(1);
            }

            stevesRobot.setDriveMotorPower(0, 0, 0, 0);

        //**********************************************************************************************
        // END OF EXAMPLE


        // STRAFE
        //**********************************************************************************************

        //Strafe Left
        stevesRobot.setDriveMotorPower(.30, -.30, -.30, .30);

        //Strafe Right
        stevesRobot.setDriveMotorPower(-.30, .30, .30, -.30);


        //**********************************************************************************************
        // END OF STRAFE



        // TURN
        //**********************************************************************************************

        //Turn right
        stevesRobot.setDriveMotorPower(.50, .50, .50, .50);

        //Turn left
        stevesRobot.setDriveMotorPower(-.50, -.50, -.50, -.50);


        //**********************************************************************************************
        // END OF TURN



        // DRIVE
        //**********************************************************************************************

        //Drive forward
        stevesRobot.setDriveMotorPower(-1, 1, -1, 1);

        //Drive backward
        stevesRobot.setDriveMotorPower(1, -1, 1, -1);


        //**********************************************************************************************
        // END OF DRIVE

        // CARROUSEL
        //**********************************************************************************************

            //spin carrousel motor counter clockwise
            stevesRobot.csRunWithoutEncoders();
            stevesRobot.setCarouselMotorPower(-.55);

            //the amount of time it takes to spin a duck on the carrousel
            sleep(4000);

        //**********************************************************************************************
        // END OF CARROUSEL


        // OTHER
        //**********************************************************************************************

        //Display Encoder Count
        while (opModeIsActive() && stevesRobot.getSortedEncoderCount() <= numofticks) {

            telemetry.addData("number of ticks: ", stevesRobot.getSortedEncoderCount());
            telemetry.update();
        }

        //Set motor power to zero
        stevesRobot.setDriveMotorPower(0, 0, 0, 0);

        //**********************************************************************************************
        // END OF OTHER


    }
}
