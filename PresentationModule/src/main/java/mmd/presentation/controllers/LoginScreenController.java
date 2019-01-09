package mmd.presentation.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import mmd.common.enums.SceneNameEnum;
import mmd.presentation.stages.StageManager;
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
	//	AuthenticationResult authResult = AuthenticationHandler.getInstance().logIn(this.usernameField.getText(),
	//	        this.passwordField.getText());
	//	if(authResult.equals(AuthenticationResult.OK))
	//	{
	//	    PresentationHandler.getInstance().changeScene(SceneName.MainScreen);
	//	}
	//	else
	//	{
	//	    ErrorHandler.handleError(authResult, ErrorType.AuthenticationError);
	//	}

    }

    @FXML
    private void onRegisterBtnClicked(final MouseEvent e)
    {
	try
	{
	    StageManager.changeScene(StageManager.getMainStage(),SceneNameEnum.Register);
	}
	catch (Exception ex)
	{
	    ErrorHandlerUtil.handleThrowable(ex);
	}
    }

}
