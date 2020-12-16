/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.CmdAutoDriveFwd;
import frc.robot.commands.CmdDriveFwd;
import frc.robot.commands.CmdLimelightTest;
import frc.robot.subsystems.SBSDriveTrain;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;
  public static SBSDriveTrain m_DriveTrain;
  UsbCamera camera;
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();


  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI();
    //Robot.m_oi.initOI();
    m_DriveTrain = new SBSDriveTrain();
    Robot.m_DriveTrain.initDriveTrain();
    DriverStation.reportError("Subsystems Setup", true);
    
    camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setResolution(1660, 1000);
    camera.setFPS(25);
    DriverStation.reportError("Vision Setup", true);

    m_chooser.setDefaultOption("Default Auto", new CmdAutoDriveFwd());
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
    m_autonomousCommand = new CmdLimelightTest();

      
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    logToDumbDashboard();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() 
  {
    /*
    //m_autonomousCommand = m_chooser.getSelected();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
    */
    //new CmdLimelightTest();

    

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    DriverStation.reportError("Executing", true);
    double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double tv = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0);
    double kSteer = 0.045;
    //double throttle = Robot.m_oi.joy1.getRawAxis(3) * -1/2 + 0.5;
    double throttle = 0;

    if(tv == 1.0)
    {
      Robot.m_DriveTrain.ArcadeDrive(throttle, tx * kSteer);
      SmartDashboard.putNumber("Test1", tx * kSteer);
    }
    else
    {
      Robot.m_DriveTrain.ArcadeDrive(0, 0);
    }
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    Robot.m_DriveTrain.resetSensors();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }


  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void logToDumbDashboard()
  {
    Robot.m_DriveTrain.logToDashboardDT();
    Robot.m_oi.logToDashboardOI();
    SmartDashboard.putNumber("tV", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0));
    SmartDashboard.putNumber("tX", NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0));
    //SmartDashboard.putNumber("Center X:", centerX);
  }
}


