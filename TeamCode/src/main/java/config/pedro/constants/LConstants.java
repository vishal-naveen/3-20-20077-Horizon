package config.pedro.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {

        PinpointConstants.forwardY = -5;
        PinpointConstants.strafeX = -2;
        PinpointConstants.hardwareMapName = "pinpoint";

        PinpointConstants.useYawScalar = false;
        PinpointConstants.yawScalar = 1.0;

        PinpointConstants.useCustomEncoderResolution = false;
        PinpointConstants.encoderResolution = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;
        PinpointConstants.customEncoderResolution = 13.26291192;

        PinpointConstants.forwardEncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;
        PinpointConstants.strafeEncoderDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED;

    }
}




