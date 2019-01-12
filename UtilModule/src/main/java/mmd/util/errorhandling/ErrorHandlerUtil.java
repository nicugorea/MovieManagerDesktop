package mmd.util.errorhandling;

/**
 * 
 * Util for handling errors
 */
public class ErrorHandlerUtil
{
	
	/**
	 * 
	 * @param e Throwable that need to be handled
	 */
	public static void handleThrowable(final Throwable e)
	{
		System.out.println("\nError:\t" + e.toString());
		System.out.println("Cause:\t" + e.getCause());
		for (StackTraceElement element : e.getStackTrace())
		{
			System.out.println("\tIn " + element.getFileName() + " in class "
			        + element.getClassName() + " method " + element.getMethodName() + " at line "
			        + element.getLineNumber());
		}
	}
	
}
