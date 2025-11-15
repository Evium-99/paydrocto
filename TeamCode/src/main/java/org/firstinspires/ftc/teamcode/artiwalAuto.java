package org.firstinspires.ftc.teamcode;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
//import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter; 

@Configurable
@Autonomous
public class artiwalAuto extends OpMode {
    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this
    private TelemetryManager telemetryM;
    private Shooter shooter;
//    private Intake intake;
    public static double rpm = 10;
    private double off = 2.5;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter = new Shooter(hardwareMap, "leftShooter", "rightShooter", telemetry);
//        intake = new Intake(hardwareMap, "lpull", "rpull", telemetry);
    }

    @Override
    public void start() {
        //The parameter controls whether the Follower should use break mode on the motors (using it is recommended).
        //In order to use float mode, add .useBrakeModeInTeleOp(true); to your Drivetrain Constants in Constant.java (for Mecanum)
        //If you don't pass anything in, it uses the default (false)
        follower.startTeleopDrive();
        resetRuntime();
    }

    @Override
    public void loop() {
        //Call this once per loop
        follower.update();
        telemetryM.update();
        shooter.periodic();
//        intake.periodic();


        if ((runtime.seconds() >= 3.0-off && runtime.seconds() < 6-off)) {
            shooter.setVelocity(0);
            follower.setTeleOpDrive(
                    -.5,
                    0,
                    0,
                    true // Robot Centric
            );
        }
        if ((runtime.seconds() >= 6.0-off && runtime.seconds() < 8-off)) {
            shooter.setVelocity(0);
            follower.setTeleOpDrive(
                    0,
                    0,
                    -.125,
                    true // Robot Centric
            );
        }
        if ((runtime.seconds() >= 8.0-off && runtime.seconds() < 10-off)) {
            follower.setTeleOpDrive(
                    -.35,
                    0,
                    0,
                    true // Robot Centric
            );
            if ((runtime.seconds() >= 8.5-off && runtime.seconds() < 9-off)){
                shooter.setVelocity(.75);
            }
        }
        if ((runtime.seconds() > 10-off && runtime.seconds() < 10.5-off)) {
            shooter.setVelocity(-1);
            follower.setTeleOpDrive(
                    0,
                    0,
                    0,
                    true // Robot Centric
            );
        }
        if ((runtime.seconds() > 10.5-off && runtime.seconds() < 12.5-off)) {
            shooter.setVelocity(0);
            follower.setTeleOpDrive(
                    .1,
                    0,
                    0,
                    true // Robot Centric
            );
        }
        if ((runtime.seconds() >= 12.5-off && runtime.seconds() < 14.5-off)) {
            shooter.setVelocity(0);
            follower.setTeleOpDrive(
                    0,
                    0,
                    .125,
                    true // Robot Centric
            );
        }
        if ((runtime.seconds() > 14.5-off && runtime.seconds() < 15.5-off)) {
            shooter.setVelocity(0);
            follower.setTeleOpDrive(
                    .25,
                    0,
                    .025,
                    true // Robot Centric
            );
        }
        if ((runtime.seconds() > 15.5-off && runtime.seconds() < 15.6-off)) {
            shooter.setVelocity(0);
            follower.setTeleOpDrive(
                    .0,
                    0,
                    0,
                    true // Robot Centric
            );
        }
    }

    }

