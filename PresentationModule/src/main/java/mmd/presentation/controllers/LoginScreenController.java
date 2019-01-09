package mmd.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mmd.authentication.auth.AuthManager;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.UserDM;
import mmd.presentation.managers.ViewManager;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class LoginScreenController
{
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    private void onLoginBtnClicked(final MouseEvent e)
    {
	try
	{
	    UserDM user = new UserDM();
	    user.setUsername(this.usernameField.getText());
	    user.setPassword(this.passwordField.getText());
	    AuthManager.signIn(user);
	    if(AuthManager.isAnyUserLoggedIn())
	    {
		ViewManager.changeScene(ViewManager.getMainStage().getStage(), SceneNameEnum.MainScreen);
	    }
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
	    ViewManager.changeScene(ViewManager.getMainStage().getStage(),SceneNameEnum.Register);
	}
	catch (Exception ex)
	{
	    ErrorHandlerUtil.handleThrowable(ex);
	}
    }

}
