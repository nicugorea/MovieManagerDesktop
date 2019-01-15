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
 * Controller for Register Window, there are handled the following:
 * <ul>
 * <li>Register a user</li>
 * <li>Go to login Scene</li>
 * </ul>
 */
public class RegisterScreenController extends ControllerBase
{
	
	@FXML
	private Label confirmPasswordErrorLabel;
	
	@FXML
	private PasswordField confirmPasswordField;
	
	@FXML
	private Label firstNameErrorLabel;
	
	@FXML
	private TextField firstNameField;
	
	@FXML
	private Label lastNameErrorLabel;
	
	@FXML
	private TextField lastNameField;
	
	@FXML
	private Label passwordErrorLabel;
	
	@FXML
	private PasswordField passwordField;
	
	@FXML
	private Label usernameErrorLabel;
	
	@FXML
	private TextField usernameField;
	
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
			ViewManager.changeScene(ViewManager.getMainWindow().getStage(), SceneNameEnum.Login);
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
			if (isInputValid())
			{
				
				UserDM user = new UserDM();
				user.setUsername(this.usernameField.getText());
				user.setPassword(this.passwordField.getText());
				
				if (AuthManager.signUp(user))
				{
					AuthManager.signIn(user);
				}
				else
				{
					usernameErrorLabel.setText("Username used !");
				}
				
				if (AuthManager.isAnyUserLoggedIn())
				{
					ViewManager.changeScene(ViewManager.getMainWindow().getStage(),
					        SceneNameEnum.MainScreen);
				}
				
			}
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	private boolean isInputValid()
	{
		boolean result = true;
		try
		{
			
			usernameErrorLabel.setText("");
			passwordErrorLabel.setText("");
			confirmPasswordErrorLabel.setText("");
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
			if (!passwordField.getText().equals(confirmPasswordField.getText()))
			{
				
				confirmPasswordErrorLabel.setText("Passwords do not match !");
				result = false;
			}
			
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		return result;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
	@Override
	protected void initName()
	{
		this.stageName = SceneNameEnum.Register;
	}
	
}
