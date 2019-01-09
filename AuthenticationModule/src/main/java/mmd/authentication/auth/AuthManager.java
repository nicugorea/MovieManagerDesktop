package mmd.authentication.auth;

import mmd.authentication.io.AuthIO;
import mmd.common.models.UserDM;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class AuthManager
{
    private static UserDM localUser = null;

    public static boolean existUsername(final String username)
    {
	try
	{
	    return AuthIO.getUserByUsername(username) != null;
	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return false;
    }

    public static UserDM getLocalUser()
    {
	return localUser;
    }

    public static boolean isAnyUserLoggedIn()
    {
	return localUser != null;
    }

    public static void signIn(final UserDM user)
    {
	try
	{
	    localUser = AuthIO.getUserByUsername(user.getUsername());
	    if(localUser != null)
	    {
		if(!localUser.getPassword().equals(user.getPassword()))
		{
		    localUser = null;
		}
	    }

	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

    }

    public static void signOut()
    {
	localUser = null;
    }

    public static void signUp(final UserDM user)
    {
	try
	{
	    if(!existUsername(user.getUsername()))
	    {
		AuthIO.saveUser(user);
	    }
	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

    }

}
