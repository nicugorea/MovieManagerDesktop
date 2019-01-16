package mmd.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mmd.authentication.auth.AuthManager;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.UserDM;
import mmd.presentation.managers.ViewManager;
import mmd.util.errorhandling.ErrorHandlerUtil;

/**
 * Controller for Login Window, there are handled the following:
 * <ul>
 * <li>Login a user</li>
 * <li>Go to main screen</li>
 * </ul>
 */
public class LoginScreenController extends ControllerBase
{
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private TextField usernameField;
	
	@FXML
	private Label usernameErrorLabel;
	
	@FXML
	private Label passwordErrorLabel;
	
	/**
	 * Login button clicked event
	 * 
	 * @param event {@link MouseEvent}
	 */
	@FXML
	private void onLoginBtnClicked(final MouseEvent e)
	{
		try
		{
			if (isInputValid())
			{
				
				UserDM user = new UserDM();
				user.setUsername(this.usernameField.getText());
				user.setPassword(this.passwordField.getText());
				if (!AuthManager.existUsername(user.getUsername()))
				{
					usernameErrorLabel.setText("Can not find username !");
				}
				AuthManager.signIn(user);
				if (AuthManager.isAnyUserLoggedIn())
				{
					ViewManager.changeScene(ViewManager.getMainWindow().getStage(),
					        SceneNameEnum.MainScreen);
				}
				else
				{
					passwordErrorLabel.setText("Wrong password !");
				}
			}
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
		
	}
	
	/**
	 * Register button clicked event
	 * 
	 * @param event {@link MouseEvent}
	 */
	@FXML
	private void onRegisterBtnClicked(final MouseEvent e)
	{
		try
		{
			ViewManager.changeScene(ViewManager.getMainWindow().getStage(), SceneNameEnum.Register);
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
	@Override
	protected void initName()
	{
		this.stageName = SceneNameEnum.Login;
		
	}
	
	private boolean isInputValid()
	{
		boolean result = true;
		try
		{
			
			usernameErrorLabel.setText("");
			passwordErrorLabel.setText("");
			if (usernameField.getText().length() < 6)
			{
				usernameErrorLabel.setText("Username must be at least 6 characters long!");
				result = false;
			}
			if (passwordField.getText().length() < 6)
			{
				passwordErrorLabel.setText("Password must be at least 6 characters long!");
				result = false;
			}
			
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		return result;
	}
	
}
