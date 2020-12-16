/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.Robot;
import frc.robot.subsystems.SBSDriveTrain;

//import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * An example command that uses an example subsystem.
 */
public class CmdTeleopDrive extends CommandBase {
  @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
  private final SBSDriveTrain m_driveTrain;
  //private DoubleSupplier leftDriveInput,rightDriveInput;



  /*DoubleSupplier leftDriveInput, DoubleSupplier rightDriveInput*/

  public CmdTeleopDrive(SBSDriveTrain subsystem) {
    this.m_driveTrain = subsystem;
    //this.leftDriveInput = leftDriveInput;
    //this.rightDriveInput = rightDriveInput;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    
    double sensitivity = 0.9;
    //double sensitivity = Robot.m_oi.joy1.getRawAxis(3) * -1/2 + 0.5;
    double throttle = -Robot.m_oi.normalize(Robot.m_oi.joy1.getRawAxis(1), 0.07) * sensitivity;
    double angle = Robot.m_oi.normalize(Robot.m_oi.joy1.getRawAxis(0), 0.07) * sensitivity;
    
    
    SmartDashboard.putNumber("Throttle: ", throttle);
    SmartDashboard.putNumber("Angle: ", angle);
    m_driveTrain.TankDrive(throttle, angle);

        
    //m_driveTrain.ArcadeDrive(angle, throttle);
    //m_driveTrain.m_motorRL.set(0.5);
    //m_driveTrain.m_motorRR.set(-0.5);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
