package edu.greenblitz.utils.hid;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;

public class CustomControlBoard extends GenericHID {


    public class CustomButton extends Button {
        private int port;

        public CustomButton(int port) {
            this.port = port;
        }

        @Override
        public boolean get() {
            return getRawButton(port);
        }
    }

    // The names are cringe. You are more than invited to find better ones. ~ karlo
    public final CustomButton
            BIG_RED, BIG_BLUE, BIG_YELLOW, BIG_GREEN,
            RIGHT_BLACK, RIGHT_WHITE, LEFT_BLACK, LEFT_WHITE,
            STATE_SWITCH, SMALL_RED, SMALL_BLUE, SMALL_YELLOW,
            BOTTOM_SMALL_GREEN, TOP_SMALL_GREEN;


    public CustomControlBoard(int port) {
        super(port);

        BIG_RED = new CustomButton(0);
        BIG_BLUE = new CustomButton(0);
        BIG_YELLOW = new CustomButton(0);
        BIG_GREEN = new CustomButton(0);
        RIGHT_BLACK = new CustomButton(0);
        RIGHT_WHITE = new CustomButton(0);
        LEFT_BLACK = new CustomButton(0);
        LEFT_WHITE = new CustomButton(0);
        STATE_SWITCH = new CustomButton(0);
        SMALL_RED = new CustomButton(0);
        SMALL_BLUE = new CustomButton(0);
        SMALL_YELLOW = new CustomButton(0);
        BOTTOM_SMALL_GREEN = new CustomButton(0);
        TOP_SMALL_GREEN = new CustomButton(0);
    }

    @Override
    public double getX(Hand hand) {
        throw new UnsupportedOperationException("control board has no axes");
    }

    @Override
    public double getY(Hand hand) {
        throw new UnsupportedOperationException("control board has no axes");
    }
}
