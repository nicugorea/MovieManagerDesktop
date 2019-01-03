package mmd.presentation.scenes;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mmd.common.enums.SceneNameEnum;
import mmd.common.enums.StageNameEnum;
import mmd.presentation.PresentationModule;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class SceneManager
{
    private static HashMap<SceneNameEnum, String> scenePath = null;
    private static Stage window = null;

    public static void changeScene(final SceneNameEnum name)
    {
	try
	{
	    window.setScene(getScene(scenePath.get(name)));
	    window.show();
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

    public static void closeStagesUntilStage(final StageNameEnum stageName)
    {

    }

    public static Parent getParentFromFile(final String fileName) throws IOException
    {
	String scenePath = "views/".concat(fileName).concat(".fxml");
	Parent parent = FXMLLoader
		.load(PresentationModule.class.getResource(scenePath));
	return parent;
    }

    public static void init(final Stage stage)
    {
	window = stage;
	scenePath = new HashMap<SceneNameEnum, String>();
	registerPaths();
    }


    private static Scene getScene(final String name) throws IOException
    {

	Scene result = new Scene(getParentFromFile(name));
	return result;
    }



    private static void registerPaths()
    {
	scenePath.put(SceneNameEnum.LoginScreen, "LoginScreenScene");
	scenePath.put(SceneNameEnum.MainScreen, "MainScreenScene");
	scenePath.put(SceneNameEnum.RegisterScreen, "RegisterScreenScene");
    }
}
