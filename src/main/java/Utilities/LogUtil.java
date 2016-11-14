package Utilities;

import java.io.IOException;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class LogUtil {
	 static Logger errorLogger;
	 static Logger normalLogger;
	 static FileAppender normalFileApp;
	 static FileAppender errorFileApp;
	 static ConsoleAppender conApp;
	 static RollingFileAppender normalRap,errorRap;
	 public static boolean isInit=false;
	
	//If log4j.property file is not in the root dir of project
	 //String log4jConfigFile = System.getProperty("user.dir")
     //          + File.separator + "log4j.properties";
      // DOMConfigurator.configure(log4jConfigFile);
	
	static PatternLayout patternLayout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p - %m%n");
	static PatternLayout consolePatternLayout = new PatternLayout("\tLOG-: [%m -  %d{yyyy-MM-dd HH:mm:ss a}] %n");
	
	
	 public static void init(Class clazz) {
		 if(!isInit){
		 
		 normalLogger = Logger.getLogger(clazz+",NormalLogger");
		 normalLogger.setLevel(Level.INFO);
		 
		 normalFileApp= new FileAppender();
		 normalFileApp.setLayout(patternLayout);
		 normalFileApp.setFile(ConfigReader.getValue("logInfoFilePath"));
		 
		 normalFileApp.setImmediateFlush(true);
		 normalLogger.addAppender(normalFileApp);
		 normalFileApp.activateOptions();
		 
		 //Rolling File Appender for maximum 5 mb log file size 
		 try {
			 normalRap = new RollingFileAppender(patternLayout, normalFileApp.getFile());
			 normalRap.setMaxBackupIndex(5);
			 normalRap.setMaximumFileSize(5);
			 normalRap.activateOptions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 errorLogger = Logger.getLogger(clazz+",ErrorLogger");
		 errorLogger.setLevel(Level.ERROR);
		 errorFileApp= new FileAppender();
		 errorFileApp.setLayout(patternLayout);
		 errorFileApp.setFile(ConfigReader.getValue("logErrorFilePath"));
		 
		 errorFileApp.setImmediateFlush(true);
		 errorLogger.addAppender(errorFileApp);
		 errorFileApp.activateOptions();
		 
		 try {
			 errorRap = new RollingFileAppender(patternLayout, errorFileApp.getFile());
			 normalRap.setMaxBackupIndex(5);
			 normalRap.setMaximumFileSize(5);
			 normalRap.activateOptions();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 conApp = new ConsoleAppender();
		 conApp.setLayout(consolePatternLayout);
		 conApp.setTarget("System.out");
		 conApp.activateOptions();
		 
		 normalLogger.addAppender(conApp);
		
		 		 
		 isInit=true;
		 }
	 }
	
	 
	 
	 public static void init(String className){
		 if(!isInit){
		 
		 normalLogger = Logger.getLogger(className+",NormalLogger");
		 normalLogger.setLevel(Level.INFO);
		 
		 normalFileApp= new FileAppender();
		 normalFileApp.setLayout(patternLayout);
		 normalFileApp.setFile("Logs\\AppLog.txt");
		 normalFileApp.setImmediateFlush(true);
		 normalLogger.addAppender(normalFileApp);
		 normalFileApp.activateOptions();
		 
		 errorLogger = Logger.getLogger(className+",ErrorLogger");
		 errorLogger.setLevel(Level.ERROR);
		 errorFileApp= new FileAppender();
		 errorFileApp.setLayout(patternLayout);
		 errorFileApp.setFile("Logs\\ErrorLog.txt");
		 errorFileApp.setImmediateFlush(true);
		 errorLogger.addAppender(errorFileApp);
		 errorFileApp.activateOptions();
		 
		 
		 conApp = new ConsoleAppender();
		 conApp.setLayout(consolePatternLayout);
		 conApp.setTarget("System.out");
		 conApp.activateOptions();
		 
		 normalLogger.addAppender(conApp);
		
		 		 
		 isInit=true;
		 }
	 }
	
	 
	 public static void infoLog(Class clazz,String message) {
		 init(clazz); 
		 normalLogger.info(message);
		 
		 	
		 	 
	 }
	 
	 public static void infoLog(String className,String message){
		 init(className); 
		 normalLogger.info(message);
		 
		 	 
	 }
	 
	 public static void errorLog(Class clazz,String message, Throwable t) {
		 init(clazz);	
	 
		 errorLogger.error(message,t);
		 errorLogger.error("-----------------------------------------------------------------------");
				 	 
	 }
	 
	 public static void errorLog(Class clazz,String message) {
		 init(clazz);	
		errorLogger.error(message);
		errorLogger.error("-----------------------------------------------------------------------");
				 	 
	 }
	 
	 public static void errorLog(String name,String message){
		 init(name);	
		errorLogger.error(message);
		errorLogger.error("-----------------------------------------------------------------------");
				 	 
	 }
	 

}
