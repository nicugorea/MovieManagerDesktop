package mmd.authentication.io;

import java.util.List;

import mmd.common.models.UserDM;
import mmd.persistence.io.PropertyIO;
import mmd.util.MagicValues;

/**
 * Class that contains static methods for operations of input and output with
 * users
 */
public class AuthIO
{
	/**
	 * Method to get an user by its username if exist
	 * 
	 * @param username Username to check
	 * @return Instance of UserDM
	 * @throws Exception
	 */
	public static UserDM getUserByUsername(final String username) throws Exception
	{
		List<UserDM> users = PropertyIO.getDMDefinitionsFromFile(MagicValues.UserDMPath,
		        UserDM.class);
		
		UserDM user = null;
		for (UserDM userDM : users)
		{
			if (userDM.getUsername().equals(username))
			{
				user = userDM;
				break;
			}
		}
		return user;
	}
	
	/**
	 * Method to save a user
	 * 
	 * @param user User to save
	 */
	public static void saveUser(final UserDM user)
	{
		PropertyIO.addDMDefinitionToFile(user, MagicValues.UserDMPath, MagicValues.UsersTagName);
	}
	
}
