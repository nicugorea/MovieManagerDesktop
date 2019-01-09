package mmd.authentication.io;

import java.util.List;

import mmd.common.models.UserDM;
import mmd.persistence.io.PropertyIO;
import mmd.util.MagicValues;

public class AuthIO
{
    public static UserDM getUserByUsername(final String username) throws Exception
    {
	List<UserDM> users = PropertyIO.getDMDefinitionsFromFile(MagicValues.UserDMPath, UserDM.class);

	UserDM user = null;
	for (UserDM userDM : users)
	{
	    if(userDM.getUsername().equals(username)) {
		user=userDM;
		break;
	    }
	}
	return user;
    }

    public static void saveUser(final UserDM user) {
	PropertyIO.addDMDefinitionToFile(user, MagicValues.UserDMPath, MagicValues.UsersTagName);
    }

}
