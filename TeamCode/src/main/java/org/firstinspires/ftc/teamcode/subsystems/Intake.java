package org.firstinspires.ftc.teamcode.subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake extends SubsystemBase {
    private Motor lpull;
    private Motor  rpull;
    private double lp;
    private double rp;
    private boolean workin;

    /**
     * Creates a new pull.
     */
    public Intake(HardwareMap hardwareMap, String lsn, String rsn, Telemetry telemetry) {
        this.lpull = new Motor(hardwareMap, lsn);
        this.rpull = new Motor(hardwareMap, rsn);
    }
    public void servospin(double yer) {
        this.lp = yer;
        this.rp = yer;
    }

    @Override
    public void periodic() {
        lpull.set(-lp);
        rpull.set(rp);
    }
}