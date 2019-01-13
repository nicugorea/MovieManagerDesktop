package mmd.authentication.auth;

import mmd.authentication.io.AuthIO;
import mmd.common.models.UserDM;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.logging.LogLevel;
import mmd.util.logging.LoggingUtil;

public class AuthManager
{
	/**
	 * An instance of UserDM that is used to keep current logged user
	 */
	private static UserDM localUser = null;

	/**
	 * Method to check if and user with given username exist
	 * 
	 * @param username Username to check
	 * @return boolean value that tell if the username exist(true) or not(false)
	 */
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
	
	/**
	 * Method to get current user
	 * 
	 * @return Current user of type UserDM
	 */
	public static UserDM getLocalUser()
	{
		return localUser;
	}
	
	/**
	 * Method to check if any user is logged in
	 * 
	 * @return boolean value that tell if true a user is logged in or false if no user is logged in
	 */
	public static boolean isAnyUserLoggedIn()
	{
		return localUser != null;
	}
	
	/**
	 * Method to sign in a user
	 * 
	 * @param user User to sign in
	 */
	public static void signIn(final UserDM user)
	{
		try
		{
			localUser = AuthIO.getUserByUsername(user.getUsername());
			if (localUser != null)
			{
				
				if (localUser.getPassword().equals(user.getPassword()))
				{
					LoggingUtil.getLogger().info("User \"" + user.getUsername() + "\" signed in");
				}
				else
				{
					localUser = null;
					LoggingUtil.getLogger().warning("Wrong password!");
				}
			}
			else
			{
				localUser = null;
				LoggingUtil.getLogger()
				        .warning("Username \"" + user.getUsername() + "\" cannot be found!");
			}
			
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		
	}
	
	/**
	 * Method to sign out current user if exist
	 */
	public static void signOut()
	{
		localUser = null;
	}
	
	/**
	 * Method to sign up a user
	 * 
	 * @param user User to sign up
	 */
	public static void signUp(final UserDM user)
	{
		try
		{
			if (!existUsername(user.getUsername()))
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
