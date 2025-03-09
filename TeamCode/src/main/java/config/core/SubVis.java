package config.core;

import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SubVis {
    private Telemetry telemetry;
    private double xOffset = 0; // Inches from the pink circle (x-direction)
    private double yOffset = 0; // Inches from the pink circle (y-direction)
    private double rotation = 0; // Degrees (counterclockwise positive)

    private static final double SUBMERSIBLE_WIDTH = 48;  // Inches
    private static final double SUBMERSIBLE_HEIGHT = 30; // Inches
    private static final int VISUAL_WIDTH = 24;          // Pixel-like grid width (maintaining 30:48 ratio)
    private static final int VISUAL_HEIGHT = 15;         // Pixel-like grid height (maintaining 30:48 ratio)

    public SubVis(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void moveForward(double inches) {
        xOffset += inches;
    }

    public void moveBackward(double inches) {
        xOffset -= inches;
    }

    public void moveLeft(double inches) {
        yOffset += inches;
    }

    public void moveRight(double inches) {
        yOffset -= inches;
    }

    public void turnLeft(double degrees) {
        rotation += degrees;
    }

    public void turnRight(double degrees) {
        rotation -= degrees;
    }

    public Pose getPose() {
        return new Pose(xOffset, yOffset, Math.toRadians(rotation));
    }

    public void update() {
        telemetry.addData("Sample Position", "X: %.2f inches, Y: %.2f inches", xOffset, yOffset);
        telemetry.addData("Rotation", "%.2f degrees", rotation);
        telemetry.addLine("Visualization:");
    }
}