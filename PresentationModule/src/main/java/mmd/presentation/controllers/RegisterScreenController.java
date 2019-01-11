package mmd.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mmd.authentication.auth.AuthManager;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.UserDM;
import mmd.presentation.managers.ViewManager;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class RegisterScreenController
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

    private boolean confirmPasswordValidation()
    {

	return true;
    }

    private boolean firstNameValidation()
    {

	return true;
    }

    private boolean inputValidation()
    {

	return this.usernameValidation() && this.passwordValidation() && this.confirmPasswordValidation()
		&& this.firstNameValidation() && this.lastNameValidation();
    }

    private boolean lastNameValidation()
    {
	return true;
    }

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

    @FXML
    private void onRegisterBtnClicked(final MouseEvent e)
    {
	try
	{

	    UserDM user = new UserDM();
	    user.setUsername(this.usernameField.getText());
	    user.setPassword(this.passwordField.getText());

	    AuthManager.signUp(user);
	    if(AuthManager.isAnyUserLoggedIn()) {
		ViewManager.changeScene(ViewManager.getMainWindow().getStage(), SceneNameEnum.MainScreen);
	    }

	}
	catch (Exception ex)
	{
	    ErrorHandlerUtil.handleThrowable(ex);
	}
    }

    private boolean passwordValidation()
    {

	return true;
    }

    private boolean usernameValidation()
    {

	return true;
    }

}
