/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class OI {


    public Joystick joy1 = new Joystick(0);
    double sensitivity = 1;//Settings.get("Sen", 1);
    /**
	 * Allows for normalizing a joystick axis
	 * @param input Joystick Input (double)
	 * @param deadzone The deadzone (double - POSITIVE)
	 * @return normalized input
	 */
	public double normalize(double input, double deadzone) //Normalize inputs to prevent deadzone errors
	{
		if(input < deadzone && input > -deadzone) {return 0;}
		else { return input * sensitivity;}
	}

}
