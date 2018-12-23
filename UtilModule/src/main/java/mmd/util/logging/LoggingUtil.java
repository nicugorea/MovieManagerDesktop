package mmd.util.logging;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggingUtil
{

    public LoggingUtil()
    {
	this.logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	LogManager.getLogManager().reset();

	ConsoleHandler consoleHandler = new ConsoleHandler();
	consoleHandler.setLevel(LogLevel.ALL);

	try
	{
	    File file = new File("C:\\ProgramData\\MovieManagerDesktop\\logs\\log" +
	            new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date()) + ".log");
	    file.getParentFile().mkdirs();
	    file.createNewFile();

	    FileHandler fileHandler = new FileHandler(file.getAbsolutePath(),
	            true);
	    fileHandler.setFormatter(new SimpleFormatter());
	    fileHandler.setLevel(Level.ALL);

	    this.logger.addHandler(fileHandler);
	}
	catch (SecurityException | IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	this.logger.addHandler(consoleHandler);
	this.logger.setLevel(Level.ALL);
    }

    private static LoggingUtil instance = null;

    public static LoggingUtil getInstance()
    {
	if(instance == null)
	{
	    instance = new LoggingUtil();
	}
	return instance;
    }

    public static Logger getLogger()
    {
	return getInstance().logger;
    }

    private Logger logger = null;
}
