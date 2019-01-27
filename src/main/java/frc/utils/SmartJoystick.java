package frc.utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is in charge of making the Joystick easy to assign things to buttons and invert the axes of the joystick.
 * The class uses m_joystick as the main joystick, and uses button values and enums in order to assign thing to those buttons.
 *
 * @see Joystick
 * @see JoystickAxis
 * @see JoystickButton
 * @see SmartButton
 */
public class SmartJoystick {
	private Joystick m_joystick;
	
	public final JoystickButton A,
								B,
								X,
								Y,
								L1,
								R1,
								START,
								BACK,
								L3,
								R3;

    /*TODO: Add support for button configuration other that XBox 360 controller.
    public static enum ButtonConfiguration {
        XBOX360(new HashMap<String, Integer>() {{put("A", 1);}});

        private HashMap<String, Integer> m_buttons;
        private ButtonConfiguration(HashMap<String, Integer> buttons) {
            m_buttons = buttons;
        }
    }
    */
    
    /**
     * This enum is in charge of all joystick axes.
     */
	public enum Axis{
		LEFT_X(0),
		LEFT_Y(1),
		LEFT_TRIGGER(2),
		RIGHT_TRIGGER(3),
		RIGHT_X(4),
        RIGHT_Y(5);
        
        private int m_axis;
        private int m_inverted = 1;

        private Axis(int axis) {
            m_axis = axis;
        }
        public void setInverted(boolean isInverted) {
            m_inverted = isInverted ? -1 : 1;
        } 

        public double getValue(SmartJoystick stick) {
            return m_inverted * stick.getRawAxis(m_axis);
        }
	}

    /**
     * This constructor constructs the joystick based on the joystick port we give it.
     *
     * @param joystick_port The port of the joystick.
     */
	public SmartJoystick(int joystick_port){
		this(new Joystick(joystick_port));
	}

    /**
     * This constructor uses a joystick and assigns it button values and numbers.
     *
     * @param stick The joystick object.
     */
	public SmartJoystick(Joystick stick){
		m_joystick = stick;
		A = new JoystickButton(m_joystick, 1);
		B = new JoystickButton(m_joystick, 2);
		X = new JoystickButton(m_joystick, 3);
		Y = new JoystickButton(m_joystick, 4);
		L1 = new JoystickButton(m_joystick, 5);
		R1 = new JoystickButton(m_joystick, 6);
		BACK = new JoystickButton(m_joystick, 7);
		START = new JoystickButton(m_joystick, 8);
		L3 = new JoystickButton(m_joystick, 9);
		R3 = new JoystickButton(m_joystick, 10);
    }

    /**
     * This function binds a joystick using a joystick object.
     *
     * @param stick This stick object which we bind the joystick to.
     */
	public void bind(Joystick stick){
		m_joystick = stick;
	}

    /**
     * This function binds a joystick using a joystick port.
     *
     * @param port The port of the joystick which we bind the joystick to.
     */
	public void bind(int port){
		bind(new Joystick(port));
	}

    /**
     * This function returns the axis based on an axis number.
     *
     * @param raw_axis The axis number we want to return.
     * @return A joystick axis based off of the joystick axis number.
     */
	public double getRawAxis(int raw_axis){
		if (m_joystick == null) return 0;
		return m_joystick.getRawAxis(raw_axis);
	}

    /**
     * This function returns a joystick
     *
     * @return The joystick used in this class.
     */
	public Joystick getRawJoystick() {
		return m_joystick;
	}
}