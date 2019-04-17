/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CmdTeleopDrive;

/**
 * Add your docs here.
 */
public class SBSDriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public SpeedController m_motorRL = new VictorSP(0);
  public SpeedController m_motorRR = new VictorSP(1);
  public Servo m_Servo1 = new Servo(2);
	public Servo m_Servo2 = new Servo(3);
	public Encoder m_encoderRL = new Encoder(8,9, false, Encoder.EncodingType.k4X);
	public Encoder m_encoderRR = new Encoder(0,1, false, Encoder.EncodingType.k4X);
	public Ultrasonic m_Ultrasonic = new Ultrasonic(2, 3);
	public PowerDistributionPanel m_pdp = new PowerDistributionPanel();

  private DifferentialDrive m_transmission = new DifferentialDrive(m_motorRL, m_motorRR);
  public BuiltInAccelerometer m_accelerometer = new BuiltInAccelerometer();


	double masterSensitivity = 1.0;
	public void initDriveTrain()
	{
		m_Ultrasonic.setAutomaticMode(true);
		m_encoderRL.setMaxPeriod(.1);
		m_encoderRL.setMinRate(10);
		m_encoderRL.setDistancePerPulse(5);
		m_encoderRL.setReverseDirection(true);
		m_encoderRL.setSamplesToAverage(7);

		m_encoderRR.setMaxPeriod(.1);
		m_encoderRR.setMinRate(10);
		m_encoderRR.setDistancePerPulse(5);
		m_encoderRR.setReverseDirection(true);
		m_encoderRR.setSamplesToAverage(7);

		resetSensors();
	}

  public void TankDrive(double left, double right)
	{
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
		if(throttle > 1 || throttle < -1)
		{
			DriverStation.reportError("ARCADE DRIVE INPUTS EXCCEDED", true);
		}
		else
		{
			m_transmission.arcadeDrive(throttle * masterSensitivity, angle * masterSensitivity);
		}
	}


  public void setServo1(double input)
  {
    m_Servo1.set(input);

  }

  public void setServo2(double input)
  {
    m_Servo2.set(input);
  }

	public void resetSensors()
	{
		m_encoderRL.reset();
		m_encoderRR.reset();
	}


  public void logToDashboardDT()
	{
		SmartDashboard.putNumber("motorRL: ", m_motorRL.get());
		SmartDashboard.putNumber("motorRR: ", m_motorRR.get());
		SmartDashboard.putNumber("Accelerometer X: ", m_accelerometer.getX());
		SmartDashboard.putNumber("Accelerometer Y: ", m_accelerometer.getY());
		SmartDashboard.putNumber("Accelerometer Z: ", m_accelerometer.getZ());
		SmartDashboard.putNumber("Encoder RL:", m_encoderRL.getRaw());
		SmartDashboard.putNumber("Encoder RR:", m_encoderRR.getRaw());
		SmartDashboard.putNumber("Ultrasonic Distance", m_Ultrasonic.getRangeMM());
		logToDashboardPDP();
		
	}

	public void logToDashboardPDP()
	{
		SmartDashboard.putNumber("Temperature (c)", m_pdp.getTemperature());
		SmartDashboard.putNumber("Current Ch 0 (A)", m_pdp.getCurrent(0));
		SmartDashboard.putNumber("Current Ch 15 (A)", m_pdp.getCurrent(15));
	}
	
	@Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    setDefaultCommand(new CmdTeleopDrive());
  }
}
