package mmd.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mmd.presentation.scenes.SceneManager;
import mmd.presentation.scenes.SceneName;

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
	SceneManager.changeScene(SceneName.LoginScreen);
    }

    @FXML
    private void onRegisterBtnClicked(final MouseEvent e)
    {

//	if(!this.inputValidation())
//	{
//	    return;
//	}
//
//	UserModel user = new UserModel();
//	user.setFirstName(this.firstNameField.getText());
//	user.setLastName(this.lastNameField.getText());
//	user.setUsername(this.usernameField.getText());
//	user.setPassword(this.passwordField.getText());
//
//	RegistrationResult registrationResult = AuthenticationHandler.getInstance().register(user,
//	        this.confirmPasswordField.getText());
//
//	if(registrationResult.equals(RegistrationResult.OK))
//	{
//	    PresentationHandler.getInstance().changeScene(SceneName.MainScreen);
//	}
//	else
//	{
//	    ErrorHandler.handleError(registrationResult, ErrorType.RegistrationError);
//	}

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
