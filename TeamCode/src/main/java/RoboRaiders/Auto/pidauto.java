package RoboRaiders.Auto;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import RoboRaiders.PID.PidUdpReceiver;
import RoboRaiders.PID.RoboRaidersPID;
import RoboRaiders.Robot.TestRobot;

@Autonomous
//@Disabled

public class pidauto extends LinearOpMode {


        private PidUdpReceiver pidUdpReceiver;
        private double kP, kI, kD, degrees, direction;
        public TestRobot stevesRobot;
        public double power;
        public RoboRaidersPID myRRPID;



    //----------------------------------------------------------------------------------------------
        // Main logic
        //----------------------------------------------------------------------------------------------

        @Override
        public void runOpMode() throws InterruptedException {
            // Create an instance of the TestRobot and store it into StevesRobot
            stevesRobot = new TestRobot();

            myRRPID = new RoboRaidersPID(0.0,0.0,0.0);

            // Initialize stevesRobot and tell user that the robot is initialized
            stevesRobot.initialize(hardwareMap);
            telemetry.addData("Robot Initialized waiting your command", true);
            telemetry.update();

            telemetry.addData("Status", "Initialized");
            telemetry.update();

            // Create new instance of receiver
            pidUdpReceiver = new PidUdpReceiver();

            // Start listening
            pidUdpReceiver.beginListening();

            // initialize the robot
            //   robot.initialize(hardwareMap);

            // set the transmission interval to 50 milliseconds
            telemetry.setMsTransmissionInterval(50);

            updatePIDCoefficients();

            myRRPID.setCoeffecients(kP,kI,kD);

            telemetry.addLine("Steve's PID Tuner");
            telemetry.addData("kP", String.valueOf(kP));
            telemetry.addData("kI", String.valueOf(kI));
            telemetry.addData("kD", String.valueOf(kD));
            telemetry.addData("Degrees", String.valueOf(degrees));

            if (direction == 0.0) {telemetry.addLine("Turn Direction - Right");}
            else {telemetry.addLine("Turn Direction - Left");}
            telemetry.update();


            // Wait for start to be pushed
            waitForStart();

            double numofticks = stevesRobot.driveTrainCalculateCounts(24);
            stevesRobot.runWithEncoders();
            power = myRRPID.pidWithCounts(numofticks, 0.0);
            stevesRobot.setDriveMotorPower(power, power, power, power);

            while (opModeIsActive() && stevesRobot.getSortedEncoderCount() <= numofticks) {
                power = myRRPID.pidWithCounts(numofticks, stevesRobot.getSortedEncoderCount());
                stevesRobot.setDriveMotorPower(power, power, power, power);

                telemetry.addData("number of ticks: ", stevesRobot.getSortedEncoderCount());
                telemetry.update();
            }
            stevesRobot.setDriveMotorPower(0, 0, 0, 0);
            telemetry.addData("setting power to zero", true);
            pidUdpReceiver.shutdown();
        }

        public void updatePIDCoefficients() {

            kP = pidUdpReceiver.getP();
            kI = pidUdpReceiver.getI();
            kD = pidUdpReceiver.getD();
            degrees = pidUdpReceiver.getDegrees();
            direction = pidUdpReceiver.getDirection();
        }
}
