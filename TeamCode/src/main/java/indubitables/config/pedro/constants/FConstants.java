package indubitables.config.pedro.constants;

import com.pedropathing.localization.Localizers;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.pathgen.Vector;
import com.pedropathing.util.CustomFilteredPIDFCoefficients;
import com.pedropathing.util.CustomPIDFCoefficients;
import com.pedropathing.util.KalmanFilterParameters;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;
        
        FollowerConstants.leftFrontMotorName = "leftFront";
        FollowerConstants.leftRearMotorName = "leftRear";
        FollowerConstants.rightFrontMotorName = "rightFront";
        FollowerConstants.rightRearMotorName = "rightRear";

        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.REVERSE;
        
        FollowerConstants.xMovement = 74.4652;
        FollowerConstants.yMovement = 61.55590;

        FollowerConstants.forwardZeroPowerAcceleration = -30.4793;
        FollowerConstants.lateralZeroPowerAcceleration = -63.8818;
        
        FollowerConstants.mass = 12.4;
        
        FollowerConstants.centripetalScaling = 0.0003;

        FollowerConstants.zeroPowerAccelerationMultiplier = 7;

        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
        FollowerConstants.pathEndTValueConstraint = 0.95;
        FollowerConstants.pathEndTimeoutConstraint = 50;

        FollowerConstants.useSecondaryTranslationalPID = true;
        FollowerConstants.useSecondaryHeadingPID = true;
        FollowerConstants.useSecondaryDrivePID = true;

        FollowerConstants.translationalPIDFCoefficients = new CustomPIDFCoefficients(
                0.05,
                0,
                0.0001,
                0);
        FollowerConstants.translationalIntegral = new CustomPIDFCoefficients(
                0,
                0,
                0,
                0);
        FollowerConstants.translationalPIDFFeedForward = 0.015;
        FollowerConstants.secondaryTranslationalPIDFCoefficients = new CustomPIDFCoefficients(
                0.1,
                0,
                0.05,
                0);
        FollowerConstants.secondaryTranslationalIntegral = new CustomPIDFCoefficients(
                0,
                0,
                0,
                0);
        FollowerConstants.secondaryTranslationalPIDFFeedForward = 0.0005;

        FollowerConstants.headingPIDFCoefficients = new CustomPIDFCoefficients(
                5,
                0,
                0.5,
                0);
        FollowerConstants.headingPIDFFeedForward = 0.01;
        FollowerConstants.secondaryHeadingPIDFCoefficients = new CustomPIDFCoefficients(
                3,
                0,
                0.1,
                0);
        FollowerConstants.secondaryHeadingPIDFFeedForward = 0.0005;

        FollowerConstants.drivePIDFCoefficients = new CustomFilteredPIDFCoefficients(
                0.01,
                0,
                0.0001,
                0.6,
                0);
        FollowerConstants.drivePIDFFeedForward = 0.01;
        FollowerConstants.secondaryDrivePIDFCoefficients = new CustomFilteredPIDFCoefficients(
                0.02,
                0,
                0.0005,
                0.6,
                0);
        FollowerConstants.secondaryDrivePIDFFeedForward = 0.01;
    }
}
