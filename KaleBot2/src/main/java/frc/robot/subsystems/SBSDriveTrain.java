/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SBSDriveTrain extends SubsystemBase {

  public SpeedController m_motorRR = new VictorSP(0);
  public SpeedController m_motorRL = new VictorSP(1);

  private DifferentialDrive m_transmission = new DifferentialDrive(m_motorRR, m_motorRL);
  


  double masterSensitivity = 1.0;
  public void TankDrive(double left, double right)
	{
    DriverStation.reportError("In TD", true);
		if(left > 1 || left < -1 || right > 1 || right < -1)
		{
			DriverStation.reportError("TANK DRIVE INPUTS EXCEEDED", true);
		}
		else
		{
			m_transmission.tankDrive((left * masterSensitivity), (right * masterSensitivity));
		}
	}
	
	public void ArcadeDrive(double throttle, double angle)
	{
    DriverStation.reportError("In AC", true);
		if(throttle > 1 || throttle < -1)
		{
			DriverStation.reportError("ARCADE DRIVE INPUTS EXCCEDED", true);
		}
		else
		{
			m_transmission.arcadeDrive(throttle, angle);
		}
	}


  /**
   * Creates a new ExampleSubsystem.
   */
  public SBSDriveTrain() {

  }

  public void initDT()
  {
    m_transmission.setSafetyEnabled(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("motorRR", m_motorRR.get());
    SmartDashboard.putNumber("motorRL", m_motorRL.get());
  }
}
