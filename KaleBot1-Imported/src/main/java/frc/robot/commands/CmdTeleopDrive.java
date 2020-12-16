/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class CmdTeleopDrive extends Command {
  public CmdTeleopDrive() {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_DriveTrain);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Robot.m_oi.joy1.getRawButton(2))
    {
      double servo1Speed = -0.5*Robot.m_oi.normalize(Robot.m_oi.joy1.getRawAxis(0), 0.07)+0.5;
      double servo2Speed = 0.85*(0.5*Robot.m_oi.normalize(Robot.m_oi.joy1.getRawAxis(1), 0.07)+0.5); //The 0.85 is cause the servo is shitty
      Robot.m_DriveTrain.setServo1(servo1Speed);
      Robot.m_DriveTrain.setServo2(servo2Speed);
      Robot.m_DriveTrain.ArcadeDrive(0, 0);

    }
    else
    {
      double throttle = -Robot.m_oi.normalize(Robot.m_oi.joy1.getRawAxis(1), 0.07);
      double angle = Robot.m_oi.normalize(Robot.m_oi.joy1.getRawAxis(0), 0.07);
      double sensitivity = Robot.m_oi.joy1.getRawAxis(3) * -1/2 + 0.5;
      SmartDashboard.putNumber("Throttle: ", throttle);
      SmartDashboard.putNumber("Angle: ", angle);

       Robot.m_DriveTrain.ArcadeDrive(throttle * sensitivity, angle * sensitivity);
    }
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
