package mmd.util.logging;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import mmd.util.UtilModule;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class LoggingUtil
{
	
	/**
	 * 
	 * <h1>Default Constructor</h1>
	 * <p>Create a file to save logs</p>
	 * <p>Add handlers to handle logs</p>
	 * 
	 */
	public LoggingUtil()
	{
		try
		{
			this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
			LogManager.getLogManager().reset();
			
			ConsoleHandler consoleHandler = new ConsoleHandler();
			consoleHandler.setLevel(LogLevel.ALL);
			
			File file = new File("C:\\ProgramData\\MovieManagerDesktop\\logs\\log"
			        + UtilModule.getDateNowString() + ".log");
			file.getParentFile().mkdirs();
			file.createNewFile();
			
			FileHandler fileHandler = new FileHandler(file.getAbsolutePath(), true);
			fileHandler.setFormatter(new SimpleFormatter());
			fileHandler.setLevel(Level.ALL);
			
			this.logger.addHandler(fileHandler);
			
			this.logger.addHandler(consoleHandler);
			this.logger.setLevel(Level.ALL);
		}
		catch (Throwable e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	/**
	 * LoggingUtil Singleton instance
	 */
	private static LoggingUtil instance = null;
	
	/**
	 * LoggingUtil Singleton getter
	 * @return Singleton instance, if null create a new one
	 */
	public static LoggingUtil getInstance()
	{
		if (instance == null)
		{
			instance = new LoggingUtil();
		}
		return instance;
	}
	
	/**
	 * Method to get logger from LoggingUtil Singleton
	 * @return {@link Logger} from {@link LoggingUtil} instance
	 */
	public static Logger getLogger()
	{
		return getInstance().logger;
	}
	
	private Logger logger = null;
}
