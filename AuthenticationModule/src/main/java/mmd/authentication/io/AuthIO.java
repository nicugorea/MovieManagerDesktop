package mmd.authentication.io;

import mmd.common.models.UserDM;
import mmd.persistence.io.PropertyIO;
import mmd.util.MagicValues;

public class AuthIO
{
    public static UserDM getUserByUsername(final String username) throws Exception
    {
	UserDM result = null;

	return result;
    }

    public static void saveUser(final UserDM user) {
	PropertyIO.addDMDefinitionToFile(user, MagicValues.UserDMPath, MagicValues.UsersTagName);

    }

}
