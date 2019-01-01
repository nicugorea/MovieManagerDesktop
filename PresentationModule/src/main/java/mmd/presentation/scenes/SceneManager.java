package mmd.presentation.scenes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.SceneNameEnum;
import mmd.presentation.PresentationModule;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class SceneManager
{
    private static HashMap<SceneNameEnum, String> scenePath = new HashMap<SceneNameEnum, String>();
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
	scenePath.put(SceneNameEnum.LoginScreen, "LoginScreenScene");
	scenePath.put(SceneNameEnum.MainScreen, "MainScreenScene");
	scenePath.put(SceneNameEnum.RegisterScreen, "RegisterScreenScene");
	scenePath.put(SceneNameEnum.AddMovie, "AddMovieStage");
    }

    public static File openFile(final FileTypeEnum fileType)
    {
	FileChooser fc = new FileChooser();
	try
	{

	    if(!fileType.equals(FileTypeEnum.All))
	    {
		switch (fileType)
		{
		case Image:
		    ExtensionFilter extensionFilter = new ExtensionFilter("Images", "*.png","*.jpg","*.jpeg","*.bmp");
		    fc.getExtensionFilters().add(extensionFilter);
		    break;
		default:
		    break;
		}
	    }
	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return fc.showOpenDialog(window);
    }

    private static Scene getScene(final String name) throws IOException
    {

	Scene result = new Scene(getParentFromFile(name));
	return result;
    }
}
