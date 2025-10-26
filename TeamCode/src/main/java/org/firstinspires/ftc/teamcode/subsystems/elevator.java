package org.firstinspires.ftc.teamcode.subsystems;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class elevator extends SubsystemBase {
    private Motor left;
    private Motor right;
    private double rpmTarget;
    private Telemetry telemetry;
    /**
     * Creates a new Shooter.
     */
    public elevator(HardwareMap hardwareMap, String leftMotorName, String rightMotorName, Telemetry telemetry) {
        this.left = new Motor(hardwareMap, leftMotorName);
        this.right = new Motor(hardwareMap, rightMotorName);
        this.telemetry = telemetry;
    }

    public void setVelocity(double rpm) {
        this.rpmTarget = rpm;
    }

    public double getSetpointVelocity() {
        return this.rpmTarget;
    }

    @Override
    public void periodic() {
        left.set(-rpmTarget);
        right.set(-rpmTarget);
        telemetry.addData("ElevatorCPR", leftShooter.getCorrectedVelocity());
    }
}
