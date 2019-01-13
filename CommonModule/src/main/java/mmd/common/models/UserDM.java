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
	
	/**
	 * Get Password
	 * 
	 * @return String of password
	 */
	public String getPassword()
	{
		return this.Password;
	}
	
	/**
	 * Get Username
	 * 
	 * @return String of Username
	 */
	public String getUsername()
	{
		return this.Username;
	}
	
	/**
	 * Set Password
	 * 
	 * @param password Password to set
	 */
	public void setPassword(final String password)
	{
		this.Password = password;
	}
	
	/**
	 * Set Username
	 * 
	 * @param username Username to set
	 */
	public void setUsername(final String username)
	{
		this.Username = username;
	}
	
	@Override
	public UserDM newInstance(Object object)
	{
		UserDM result = (UserDM) object;
		if (result.getUsername() == null) result.setUsername("Unknown");
		if (result.getPassword() == null) result.setPassword("Unknown");
		return result;
	}
	
}
