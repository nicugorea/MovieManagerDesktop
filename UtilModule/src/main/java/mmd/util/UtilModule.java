package mmd.util;

import java.util.logging.Logger;

import mmd.util.logging.LoggingUtil;

public class UtilModule
{
    public static Logger getLogger()
    {
	return LoggingUtil.getLogger();
    }
}
