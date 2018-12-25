package mmd.presentation.scenes;

import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mmd.presentation.PresentationModule;

public class SceneManager
{
    private static HashMap<SceneName, String> scenePath = new HashMap<SceneName, String>();
    private static Stage window = null;

    public static void changeScene(final SceneName name)
    {
	try
	{
	    window.setScene(getScene(scenePath.get(name)));
	    window.show();
	}
	catch (IOException e)
	{

	}
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
	scenePath.put(SceneName.LoginScreen, "LoginScreenScene");
	scenePath.put(SceneName.MainScreen, "MainScreenScene");
	scenePath.put(SceneName.RegisterScreen, "RegisterScreenScene");
    }

    private static Scene getScene(final String name) throws IOException
    {

	Scene result = new Scene(getParentFromFile(name));
	return result;
    }
}
