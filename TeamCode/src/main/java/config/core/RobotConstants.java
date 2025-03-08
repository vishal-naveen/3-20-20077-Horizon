package config.core;

import com.acmerobotics.dashboard.config.Config;

@Config
public class RobotConstants {

    // Outtake
    public static double outtakeGrabClose = 0.03;
    public static double outtakeGrabOpen = 0.3;
    public static double outtakeRotateTransfer = 0.575;
    public static double outtakeRotateSpecimenGrab180 = 0.58;
    public static double outtakeRotateSpecimenGrab0 = 0.56;
    public static double outtakeRotateLeftScore = 0.9;
    public static double outtakeRotateRightScore = 0.5;
    public static double outtakeRotateLeftSpecimenScore180 = 0.68;
    public static double outtakeRotateRightSpecimenScore180 = 0.24;
    public static double outtakeRotateLeftSpecimenScore0 = 0.68;
    public static double outtakeRotateRightSpecimenScore0 = .2;
    public static double outtakePivotTransfer= 0.8; //0
    public static double outtakePivotScore = 0.4;
    public static double outtakePivotSpecimenGrab180 = 0.87;
    public static double outtakePivotSpecimenScore180 = 0.32;

    public static double outtakePivotSpecimenGrab0 = 0.97;
    public static double outtakePivotSpecimenScore0 = 0.41;

    // Intake
    public static double intakeGrabClose = 0.095;
    public static double intakeGrabOpen = 0.35;
    public static double intakeRotateTransfer = 0.41;
    public static double intakeRotateHoverVertical = 0.81;
    public static double intakeRotateGroundVertical = 0.77;
    public static double intakeRotateCloudVertical = 0.81;
    public static double intakeRotateSpecimen = 0.45;
    public static double intakePivotTransfer= 0.38;
    public static double intakePivotGround = 0.67;
    public static double intakePivotHover = 0.43;
    public static double intakePivotCloud = 0.6;
    public static double intakePivotSpecimen = 0;
    public static double intakeRotatePerDegree = 0.0011;

    // Lift Positions
    public static int liftToZero = 0;
    public static int liftToHumanPlayer = 200;
    public static int liftToHighChamber = 200;
    public static int liftToHighBucket = 2000;
    public static int liftToTransfer = 200;
    public static int liftToPark = 0;

    // Extend Positions
    public static double extendZero = 0.5375;
    public static double extendFull = .3;
    public static double extendTransfer = .51;

}