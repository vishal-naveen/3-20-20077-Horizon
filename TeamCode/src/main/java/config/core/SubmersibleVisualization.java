package config.core;

import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SubmersibleVisualization {
    private Telemetry telemetry;
    private double xOffset = 0; // Inches from the pink circle (x-direction)
    private double yOffset = 0; // Inches from the pink circle (y-direction)
    private double rotation = 0; // Degrees (counterclockwise positive)

    private static final double SUBMERSIBLE_WIDTH = 48;
    private static final double SUBMERSIBLE_HEIGHT = 24;
    private static final int DASH_COUNT = 9;
    private static final int VISUAL_WIDTH = 20;
    private static final int VISUAL_HEIGHT = 10;

    public SubmersibleVisualization(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public void moveForward(double inches) {
        yOffset += inches;
    }

    public void moveBackward(double inches) {
        yOffset -= inches;
    }

    public void moveLeft(double inches) {
        xOffset -= inches;
    }

    public void moveRight(double inches) {
        xOffset += inches;
    }

    public void turnLeft(double degrees) {
        rotation += degrees;
    }

    public void turnRight(double degrees) {
        rotation -= degrees;
    }

    public double[] getPose() {
        return new double[]{xOffset, yOffset};
    }

    private char getSampleSymbol() {
        double angle = (rotation % 360 + 360) % 360; // Normalize to 0-360
        if (angle >= 315 || angle < 45) {
            return '|'; // Upright
        } else if (angle >= 45 && angle < 135) {
            return '\\'; // Diagonal
        } else if (angle >= 135 && angle < 225) {
            return '-'; // Horizontal
        } else {
            return '/'; // Diagonal
        }
    }

    public void update() {
        telemetry.addData("Sample Position", "X: %.2f inches, Y: %.2f inches", xOffset, yOffset);
        telemetry.addData("Rotation", "%.2f degrees", rotation);
        telemetry.addLine("Visualization:");

        char sampleSymbol = getSampleSymbol();

        for (int i = 0; i < VISUAL_HEIGHT; i++) {
            StringBuilder line = new StringBuilder();
            if (i == VISUAL_HEIGHT / 2) {
                line.append("|");
                for (int j = 0; j < DASH_COUNT; j++) {
                    line.append("-");
                }
                line.append("|");
            } else {
                line.append("|");
                for (int j = 0; j < DASH_COUNT; j++) {
                    if (i == (int) ((yOffset / SUBMERSIBLE_HEIGHT) * VISUAL_HEIGHT)
                            && j == (int) ((xOffset / SUBMERSIBLE_WIDTH) * DASH_COUNT)) {
                        line.append(sampleSymbol); // Rotating sample representation
                    } else {
                        line.append(" ");
                    }
                }
                line.append("|");
            }
            telemetry.addLine(line.toString());
        }
        telemetry.update();
    }
}

