package mmd.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import mmd.util.logging.LoggingUtil;

/**
 * 
 * <h1>Util module main class</h1>
 * <p>Here are create all elements that are needed for this module</p>
 * <p>Here are located utilities like logging, magic values, input and output helper, etc.</p> 
 */
public class UtilModule
{
	/**
	 * 
	 * @return Logger static instance that is used for logging
	 */
    public static Logger getLogger()
    {
    	return LoggingUtil.getLogger();	
    }
    
    /**
     * 
     * @return Current time in String format yyyy-MM-dd_HH_mm_ss 
     */
    public static String getDateNowString() {
    	return new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss").format(new Date());
    }
}
