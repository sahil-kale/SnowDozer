/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

public class CmdDriveFwd extends Command {

  private double throttle, timer;

  public CmdDriveFwd(double m_throttle, double m_timer) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    requires(Robot.m_DriveTrain);
    throttle = m_throttle;
    timer = m_timer;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    DriverStation.reportError("Triggered", true);
    setTimeout(timer);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    //Robot.m_DriveTrain.TankDrive(throttle, throttle);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double kSteer = 0.04;
    double angle = tx*kSteer;
    double throttle = Robot.m_oi.joy1.getRawAxis(3) * -1/2 + 0.5;

    if(tv == 1.0)
    {
      Robot.m_DriveTrain.ArcadeDrive(throttle, angle);
      SmartDashboard.putNumber("Angle", angle);
    }
    else
    {
      Robot.m_DriveTrain.ArcadeDrive(0, 0);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    DriverStation.reportError("Finished", true);
    return isTimedOut();
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    Robot.m_DriveTrain.TankDrive(0, 0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
