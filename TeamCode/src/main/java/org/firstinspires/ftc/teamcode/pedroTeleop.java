package org.firstinspires.ftc.teamcode;
import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.bylazar.telemetry.TelemetryManager;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
//import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Shooter;
//import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Configurable
@TeleOp
public class pedroTeleop extends OpMode {
    private Follower follower;
    public static Pose startingPose; //See ExampleAuto to understand how to use this
    private TelemetryManager telemetryM;
    private boolean slowMode;
    private double slowModeMultiplier = 0.20;
    private boolean powersave = true;
    private Shooter shooter;
    private double cruise = 0;
    //    private Intake intake;
    public static double rpm = 10;
    private double slowin;

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
    }

    @Override
    public void loop() {
        //Call this once per loop
        follower.update();
        telemetryM.update();
        shooter.periodic();
//        intake.periodic();
//        if (gamepad2.left_stick_y>.1 || gamepad2.left_stick_y<-.1) {
//            shooter.setVelocity(gamepad2.left_stick_y);
//        }
        if (powersave) {
            shooter.setVelocity(0);
        }
        else if (gamepad2.x || gamepad1.x) {
            shooter.setVelocity(1);
        } else if (gamepad2.y || gamepad1.y) {
            shooter.setVelocity(cruise);
        }
        else if (true) {
            cruise = gamepad2.left_stick_y + gamepad1.left_trigger;
            shooter.setVelocity((-cruise*.7)-.3);
        }
        if (gamepad2.a || gamepad1.a){
            powersave = true;
        }
        if (gamepad2.b || gamepad1.b) {
            powersave = false;
        }

        if (gamepad2.right_trigger > .1) {
            slowin = .25;
        } else{
            slowin = 1;
        }
        if (!slowMode) follower.setTeleOpDrive(
                gamepad1.left_stick_y,
                gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                true // Robot Centric
        );

            //This is how it looks with slowMode on
        else follower.setTeleOpDrive(
                gamepad1.left_stick_y * slowModeMultiplier,
                gamepad1.left_stick_x * slowModeMultiplier,
                -gamepad1.right_stick_x * slowModeMultiplier,
                true // Robot Centric
        );

        //Slow Mode
        if (gamepad1.right_trigger > .1) {
            slowMode = true;
        } else {
            slowMode = false;
        }

        // telematree
        telemetry.addData("powersave: ", powersave);

    }
}
