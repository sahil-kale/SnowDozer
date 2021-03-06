/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CmdResetSensors;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
  //// CREATING BUTTONS
  // One type of button is a joystick button which is any button on a
  //// joystick.
  // You create one by telling it which joystick it's on and which button
  // number it is.
  // Joystick stick = new Joystick(port);
  // Button button = new JoystickButton(stick, buttonNumber);

  // There are a few additional built in buttons you can use. Additionally,
  // by subclassing Button you can create custom triggers and bind those to
  // commands the same as any other Button.

  //// TRIGGERING COMMANDS WITH BUTTONS
  // Once you have a button, it's trivial to bind it to a button in one of
  // three ways:

  // Start the command when the button is pressed and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenPressed(new ExampleCommand());

  // Run the command while the button is being held down and interrupt it once
  // the button is released.
  // button.whileHeld(new ExampleCommand());

  // Start the command when the button is released and let it run the command
  // until it is finished as determined by it's isFinished method.
  // button.whenReleased(new ExampleCommand());

  
  public Joystick joy1 = new Joystick(0);

  /**
	 * Allows for normalizing a joystick axis
	 * 
	 * 
	 * @param input Joystick Input (double)
	 * @param deadzone The deadzone (double - POSITIVE)
	 * @return normalized input
	 */

  double sensitivity = 1;//Settings.get("Sen", 1);
  
	public double normalize(double input, double deadzone) //Normalize inputs to prevent deadzone errors
	{
		if(input < deadzone && input > -deadzone)
		{
			return 0;
		}
		else
		{
			return input * sensitivity;
		}
	}
	
	
	/**
	 * Allows for normalizing a joystick around a new centrepoint (e.g. Joystick may be centered slightly further on one side,
	 * and large deadzones are inconvenient
	 * 
	 * @param input the input provided
	 * @param deadzone the deadzone around the centrepoint
	 * @param centrepoint the centrepoint for the joystick
	 * @return normalized input with new centrepoint
	 */
	public double normalizeWithNewCentre(double input, double deadzone, double centrepoint)
	{
		if(input < (deadzone + centrepoint) && input > (-deadzone + centrepoint))
		{
			return 0;
		}
		else
		{
			return input;
		}
  }
  
  public void logToDashboardOI()
  {
		SmartDashboard.putNumber("Sensitivity", joy1.getRawAxis(3));
  }

  public void initOI()
	{
		//SmartDashboard.putData("Reset Sensors", new CmdResetSensors());
	}
}
