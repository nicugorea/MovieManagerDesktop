package mmd.util.errorhandling;

public class ErrorHandlerUtil
{

    public static void handleThrowable(final Throwable e)
    {
	System.out.println("\nError:\t" + e.toString());
	System.out.println("Cause:\t" + e.getCause());
	for (StackTraceElement element : e.getStackTrace())
	{
	    System.out.println("\tIn " + element.getFileName() + " in class " + element.getClassName() + " method "
		    + element.getMethodName() + " at line " + element.getLineNumber());
	}
    }

}
