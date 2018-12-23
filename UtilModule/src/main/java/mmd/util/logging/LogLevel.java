package mmd.util.logging;

import java.util.logging.Level;

public class LogLevel extends Level
{

    protected LogLevel(final String name, final int value)
    {
	super(name, value);
    }

    public static LogLevel DEBUG = new LogLevel("DEBUG", 750);

}
