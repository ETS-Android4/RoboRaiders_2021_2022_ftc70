package RoboRaiders.Auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import RoboRaiders.PID.PidUdpReceiver;
import RoboRaiders.PID.RoboRaidersPID;
import RoboRaiders.Robot.QueenLizzy26;
import RoboRaiders.Robot.TestRobot;

@Autonomous
//@Disabled

public class QL26MotorTest extends LinearOpMode {
    double power = 0.20;



    //----------------------------------------------------------------------------------------------
        // Main logic
        //----------------------------------------------------------------------------------------------

        @Override
        public void runOpMode() throws InterruptedException {

            // Create an instance of the robot and store it into QueenLizzy26
            QueenLizzy26 robot = new QueenLizzy26();


            // Initialize stevesRobot and tell user that the robot is initialized
            robot.initialize(hardwareMap);
            telemetry.addData("Robot Initialized waiting your command", true);
            telemetry.update();

            telemetry.addData("Status", "Initialized");
            telemetry.update();



            // Wait for start to be pushed
            waitForStart();
            robot.runWithEncoders();
            robot.setDriveMotorPower(-power, power, -power, power);
            robot.setDriveMotorPower(power,power,power,power);

            while (opModeIsActive()) {

                telemetry.addLine().addData("Back Left Encoder Count: ", robot.getBackLeftDriveEncoderCounts());
                telemetry.addLine().addData("Back Right Encoder Count: ", robot.getBackRightDriveEncoderCounts());
                telemetry.addLine().addData("Front Left Encoder Count: ", robot.getFrontLeftDriveEncoderCounts());
                telemetry.addLine().addData("Front Right Encoder Count: ", robot.getFrontRightDriveEncoderCounts());
                telemetry.addLine().addData("Power applied: ", power);
                telemetry.update();
            }
            robot.setDriveMotorPower(0, 0, 0, 0);
            telemetry.addData("setting power to zero", true);

        }


}
