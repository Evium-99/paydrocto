package org.firstinspires.ftc.teamcode;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;

@Configurable
@TeleOp
public class pedroTeleop extends OpMode {
    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this
    private TelemetryManager telemetryM;
    private Shooter shooter;
    private Intake intake;
    public static double rpm = 10;

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
    }

    @Override
    public void loop() {
        //Call this once per loop
        follower.update();
        telemetryM.update();
        shooter.periodic();
        intake.periodic();

        //Gamepad 2 controls:
        if (gamepad2.right_trigger > 0.05) {
            while (gamepad2.right_trigger > 0.05) {
                shooter.setVelocity(rpm);
                intake.servospin(1);
            }
            shooter.setVelocity(0);
            intake.servospin(0);
        }
        else if (gamepad2.left_trigger > 0.05) {
            while (gamepad2.left_trigger > 0.05) {
                intake.servospin(-1);
            }
            intake.servospin(0);
        }
        else if (gamepad2.a) {
            while (gamepad2.a) {
                shooter.setVelocity(-rpm / 4);
                intake.servospin(1);
            }
            shooter.setVelocity(0);
            intake.servospin(0);
        }
        if (gamepad2.left_stick_y < -0.1) {
            while (gamepad2.left_stick_y < -0.1) {
                intake.servospin(-1);
            }
            intake.servospin(0);
        }
        if (gamepad2.left_stick_y > 0.1) {
            while (gamepad2.left_stick_y > 0.1) {
                intake.servospin(1);
            }
            intake.servospin(0);
        }
        if (gamepad2.right_stick_y < -0.1) {
            while (gamepad2.right_stick_y < -0.1) {
                shooter.setVelocity(-rpm);
            }
            shooter.setVelocity(0);
        }
        if (gamepad2.right_stick_y > 0.1) {
            while (gamepad2.right_stick_y > 0.1) {
                shooter.setVelocity(rpm);
            }
            shooter.setVelocity(0);
        }


        //Gamepad 1 controls
        double slowModeMultiplier = 0.25;
        if (gamepad1.right_trigger > 0.05) follower.setTeleOpDrive(
                -gamepad1.left_stick_y * slowModeMultiplier,
                -gamepad1.left_stick_x * slowModeMultiplier,
                -gamepad1.right_stick_x * slowModeMultiplier,
                true // Robot Centric
        );
        else follower.setTeleOpDrive(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                true // Robot Centric
        );
    }
}