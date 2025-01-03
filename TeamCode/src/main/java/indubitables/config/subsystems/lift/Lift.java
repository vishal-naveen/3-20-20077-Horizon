package indubitables.config.subsystems.lift;

import static indubitables.config.old.util.RobotConstants.liftToHighBucket;
import static indubitables.config.old.util.RobotConstants.liftToHighChamber;
import static indubitables.config.old.util.RobotConstants.liftToHumanPlayer;
import static indubitables.config.old.util.RobotConstants.liftToPark;
import static indubitables.config.old.util.RobotConstants.liftToTransfer;
import static indubitables.config.old.util.RobotConstants.liftToZero;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Lift extends SubsystemBase {

    private Telemetry telemetry;

    public DcMotor rightLift, leftLift;
    public boolean manual = false;
    public int pos;
    public PIDController pid;
    public static int target;
    public static double p = 0.01, i = 0, d = 0, f = 0.005;


    public Lift(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        rightLift = hardwareMap.get(DcMotor.class, "rightLift");
        leftLift = hardwareMap.get(DcMotor.class, "leftLift");

        rightLift.setDirection(DcMotor.Direction.FORWARD);
        leftLift.setDirection(DcMotor.Direction.REVERSE);
        rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        pid = new PIDController(p, i, d);
    }

    public void update(){
        if (!manual) {
            updatePIDF();
        }
    }

    public void updatePIDF() {
        pid.setPID(p,i,d);

        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        double pid_output = pid.calculate(getPos(), target);
        double ticks_in_degrees = 537.7 / 360.0;
        double ff = Math.cos(Math.toRadians(target / ticks_in_degrees)) * f;
        double power = pid_output + ff;

        rightLift.setPower(power);
        leftLift.setPower(power);
    }

    public void manual(double n){
        manual = true;

        rightLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftLift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightLift.setPower(n);
        leftLift.setPower(n);
    }

    public void setTarget(int b) {
        target = b;
    }

    public int getPos() {
        pos = rightLift.getCurrentPosition();
        return rightLift.getCurrentPosition();
    }

    public void init() {
        pid.setPID(p,i,d);
    }

    public void start() {
        target = 0;
    }


    public void toZero() {
        manual = false;
        setTarget(liftToZero);
    }

    public void toHighBucket() {
        manual = false;
        setTarget(liftToHighBucket);
    }

    public void telemetry() {
        telemetry.addData("Lift Pos: ", getPos());
        telemetry.addData("Lift Target: ", target);
    }

    @Override
    public void periodic() {
        update();
        telemetry();
    }
}
