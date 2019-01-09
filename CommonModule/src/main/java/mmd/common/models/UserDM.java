package mmd.common.models;

import mmd.common.bases.DMBase;
import mmd.util.MagicValues;

public class UserDM extends DMBase<UserDM>
{

    private String Password;
    private String Username;


    @Override
    public String getName()
    {
	return MagicValues.UserDMName;
    }


    public String getPassword()
    {
	return this.Password;
    }


    public String getUsername()
    {
	return this.Username;
    }


    public void setPassword(final String password)
    {
	this.Password = password;
    }

    public void setUsername(final String username)
    {
	this.Username = username;
    }


}
