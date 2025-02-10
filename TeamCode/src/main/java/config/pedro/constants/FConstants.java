package config.pedro.constants;

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
        
        FollowerConstants.xMovement = 77.6714;
        FollowerConstants.yMovement = 67.8393;

        FollowerConstants.forwardZeroPowerAcceleration = -37.6886;
        FollowerConstants.lateralZeroPowerAcceleration = -63.6665;
        
        FollowerConstants.mass = 10;
        
        FollowerConstants.centripetalScaling = 0.0005;

        FollowerConstants.zeroPowerAccelerationMultiplier = 4;

        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
        FollowerConstants.pathEndTValueConstraint = 0.95;
        FollowerConstants.pathEndTimeoutConstraint = 50;

        FollowerConstants.useSecondaryTranslationalPID = true;
        FollowerConstants.useSecondaryHeadingPID = true;
        FollowerConstants.useSecondaryDrivePID = true;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(
                .1,
                0,
                .01,
                0);
        FollowerConstants.translationalIntegral.setCoefficients(
                0,
                0,
                0,
                0);
        FollowerConstants.translationalPIDFFeedForward = 0.015;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(
                0.075,
                0,
                0.05,
                0);
        FollowerConstants.secondaryTranslationalIntegral.setCoefficients(
                0,
                0,
                0,
                0);
        FollowerConstants.secondaryTranslationalPIDFFeedForward = 0.0005;

        FollowerConstants.headingPIDFCoefficients.setCoefficients(
                5,
                0,
                0.5,
                0);
        FollowerConstants.headingPIDFFeedForward = 0.01;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(
                1.5,
                0,
                0.1,
                0);
        FollowerConstants.secondaryHeadingPIDFFeedForward = 0.0005;

        FollowerConstants.drivePIDFCoefficients.setCoefficients(
                0.01,
                0,
                0.0001,
                0.6,
                0);
        FollowerConstants.drivePIDFFeedForward = 0.01;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(
                0.02,
                0,
                0.0005,
                0.6,
                0);
        FollowerConstants.secondaryDrivePIDFFeedForward = 0.01;
    }
}
