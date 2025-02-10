package config.core.gamepad;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.Gamepad;
import config.core.gamepad.GamepadKeys.Button;
import config.core.gamepad.commands.GamepadButton;

import java.util.HashMap;

/**
 * An extended gamepad for more advanced toggles, key events,
 * and other control processors.
 */
public class ExGamepad {

    /**
     * The retrievable gamepad object
     */
    public Gamepad gamepad;

    private HashMap<Button, ButtonReader> buttonReaders;
    private HashMap<Button, GamepadButton> gamepadButtons;

    private final Button[] buttons = {
            Button.Y, Button.X, Button.A, Button.B, Button.LEFT_BUMPER, Button.RIGHT_BUMPER, Button.BACK,
            Button.START, Button.OPTIONS, Button.DPAD_UP, Button.DPAD_DOWN, Button.DPAD_LEFT, Button.DPAD_RIGHT,
            Button.LEFT_STICK_BUTTON, Button.RIGHT_STICK_BUTTON,
            Button.TRIANGLE, Button.SQUARE, Button.CROSS, Button.CIRCLE,
            Button.PS, Button.SHARE, Button.TOUCHPAD, Button.TOUCHPAD_FINGER_1, Button.TOUCHPAD_FINGER_2
    };

    /**
     * The constructor, that contains the gamepad object from the
     * opmode.
     *
     * @param gamepad the gamepad object from the opmode
     */
    public ExGamepad(Gamepad gamepad) {
        this.gamepad = gamepad;
        buttonReaders = new HashMap<>();
        gamepadButtons = new HashMap<>();
        for (Button button : buttons) {
            buttonReaders.put(button, new ButtonReader(this, button));
            gamepadButtons.put(button, new GamepadButton(this, button));
        }
    }

    /**
     * @param button the button object
     * @return the boolean value as to whether the button is active or not
     */
    public boolean getButton(Button button) {
        boolean buttonValue = false;
        switch (button) {
            case A:
            case CROSS:
                buttonValue = gamepad.a;
                break;
            case B:
            case CIRCLE:
                buttonValue = gamepad.b;
                break;
            case SQUARE:
            case X:
                buttonValue = gamepad.x;
                break;
            case TRIANGLE:
            case Y:
                buttonValue = gamepad.y;
                break;
            case LEFT_BUMPER:
                buttonValue = gamepad.left_bumper;
                break;
            case RIGHT_BUMPER:
                buttonValue = gamepad.right_bumper;
                break;
            case DPAD_UP:
                buttonValue = gamepad.dpad_up;
                break;
            case DPAD_DOWN:
                buttonValue = gamepad.dpad_down;
                break;
            case DPAD_LEFT:
                buttonValue = gamepad.dpad_left;
                break;
            case DPAD_RIGHT:
                buttonValue = gamepad.dpad_right;
                break;
            case BACK:
                buttonValue = gamepad.back;
                break;
            case START:
                buttonValue = gamepad.start;
                break;
            case OPTIONS:
                buttonValue = gamepad.options;
                break;
            case LEFT_STICK_BUTTON:
                buttonValue = gamepad.left_stick_button;
                break;
            case RIGHT_STICK_BUTTON:
                buttonValue = gamepad.right_stick_button;
                break;
            case PS:
                buttonValue = gamepad.ps;
                break;
            case SHARE:
                buttonValue = gamepad.share;
                break;
            case TOUCHPAD:
                buttonValue = gamepad.touchpad;
                break;
            case TOUCHPAD_FINGER_1:
                buttonValue = gamepad.touchpad_finger_1;
                break;
            case TOUCHPAD_FINGER_2:
                buttonValue = gamepad.touchpad_finger_2;
                break;
            default:
                buttonValue = false;
                break;
        }
        return buttonValue;
    }

    /**
     * @param trigger the trigger object
     * @return the value returned by the trigger in question
     */
    public double getTrigger(GamepadKeys.Trigger trigger) {
        double triggerValue = 0;
        switch (trigger) {
            case LEFT_TRIGGER:
                triggerValue = gamepad.left_trigger;
                break;
            case RIGHT_TRIGGER:
                triggerValue = gamepad.right_trigger;
                break;
            default:
                break;
        }
        return triggerValue;
    }

    /**
     * @return the y-value on the left analog stick
     */
    public double getLeftY() {
        return gamepad.left_stick_y;
    }

    /**
     * @return the y-value on the right analog stick
     */
    public double getRightY() {
        return gamepad.right_stick_y;
    }

    /**
     * @return the x-value on the left analog stick
     */
    public double getLeftX() {
        return gamepad.left_stick_x;
    }

    /**
     * @return the x-value on the right analog stick
     */
    public double getRightX() {
        return gamepad.right_stick_x;
    }

    /**
     * Returns if the button was just pressed
     *
     * @param button the desired button to read from
     * @return if the button was just pressed
     */
    public boolean wasJustPressed(Button button) {
        return buttonReaders.get(button).wasJustPressed();
    }

    /**
     * Returns if the button was just released
     *
     * @param button the desired button to read from
     * @return if the button was just released
     */
    public boolean wasJustReleased(Button button) {
        return buttonReaders.get(button).wasJustReleased();
    }

    /**
     * Updates the value for each {@link ButtonReader}.
     * Call this once in your loop.
     */
    public void readButtons() {
        for (Button button : buttons) {
            buttonReaders.get(button).readValue();
        }
    }

    /**
     * Returns if the button is down
     *
     * @param button the desired button to read from
     * @return if the button is down
     */
    public boolean isDown(Button button) {
        return buttonReaders.get(button).isDown();
    }

    /**
     * Returns if the button's state has just changed
     *
     * @param button the desired button to read from
     * @return if the button's state has just changed
     */
    public boolean stateJustChanged(Button button) {
        return buttonReaders.get(button).stateJustChanged();
    }

    /**
     * @param button the matching button key to the gamepad button
     * @return the commandable button
     */
    public GamepadButton getGamepadButton(Button button) {
        return gamepadButtons.get(button);
    }

    @NonNull
    @Override
    public String toString() {
        return "ExGamepad{" +
                "gamepad=" + gamepad +
                "A=" + getButton(Button.A) +
                ", B=" + getButton(Button.B) +
                ", X=" + getButton(Button.X) +
                ", Y=" + getButton(Button.Y) +
                ", leftBumper=" + getButton(Button.LEFT_BUMPER) +
                ", rightBumper=" + getButton(Button.RIGHT_BUMPER) +
                ", dpadUp=" + getButton(Button.DPAD_UP) +
                ", dpadDown=" + getButton(Button.DPAD_DOWN) +
                ", dpadLeft=" + getButton(Button.DPAD_LEFT) +
                ", dpadRight=" + getButton(Button.DPAD_RIGHT) +
                ", leftStickButton=" + getButton(Button.LEFT_STICK_BUTTON) +
                ", rightStickButton=" + getButton(Button.RIGHT_STICK_BUTTON) +
                ", leftTrigger=" + getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) +
                ", rightTrigger=" + getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) +
                ", leftY=" + getLeftY() +
                ", rightY=" + getRightY() +
                ", leftX=" + getLeftX() +
                ", rightX=" + getRightX() +
                '}';
    }
}
