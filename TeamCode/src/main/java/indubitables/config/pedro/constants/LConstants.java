package indubitables.config.pedro.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {

        PinpointConstants.forwardY = 1.25;
        PinpointConstants.strafeX = 1.25;
        PinpointConstants.hardwareMapName = "pinpoint";

        PinpointConstants.useYawScalar = false;
        PinpointConstants.yawScalar = 1.0;

        PinpointConstants.useCustomEncoderResolution = false;
        PinpointConstants.encoderResolution = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;
        PinpointConstants.customEncoderResolution = 13.26291192;

        PinpointConstants.forwardEncoderDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED;
        PinpointConstants.strafeEncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;

    }
}




