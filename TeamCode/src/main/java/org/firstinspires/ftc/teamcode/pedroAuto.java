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
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@Configurable
@Autonomous
public class pedroAuto extends OpMode {
    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this
    private TelemetryManager telemetryM;
    private Shooter shooter;
    private Intake intake;
    public static double rpm = 10;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        follower = Constants.createFollower(hardwareMap);
        follower.setStartingPose(startingPose == null ? new Pose() : startingPose);
        follower.update();
        telemetryM = PanelsTelemetry.INSTANCE.getTelemetry();
        shooter = new Shooter(hardwareMap, "leftShooter", "rightShooter", telemetry);
        intake = new Intake(hardwareMap, "lpull", "rpull", telemetry);
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
        intake.periodic();


        while ((runtime.seconds() >= 3.0 && runtime.seconds() < 4.0)) {
            follower.setTeleOpDrive(
                    1,
                    0,
                    0,
                    true // Robot Centric
            );
        }
        while ((runtime.seconds() > 4.0 && runtime.seconds() < 5.0)) {
            follower.setTeleOpDrive(
                    0,
                    0,
                    0,
                    true // Robot Centric
            );
        }
        while ((runtime.seconds() > 5.0 && runtime.seconds() < 7.0)) {
            shooter.setVelocity(rpm);
        }
        while ((runtime.seconds() > 7.0 && runtime.seconds() < 10.0)) {
            intake.servospin(1);
        }
        while ((runtime.seconds() > 10.0 && runtime.seconds() < 11.0)) {
            shooter.setVelocity(0);
            intake.servospin(0);
        }
        while ((runtime.seconds() > 11.0 && runtime.seconds() < 12.0)) {
            follower.setTeleOpDrive(
                    1,
                    0,
                    0,
                    true // Robot Centric
            );
        }
        while ((runtime.seconds() > 12.0 && runtime.seconds() < 13.0)) {
            follower.setTeleOpDrive(
                    0,
                    0,
                    0,
                    true // Robot Centric
            );
        }
    }

    public void setRuntime(ElapsedTime runtime) {
        this.runtime = runtime;
    }
}
