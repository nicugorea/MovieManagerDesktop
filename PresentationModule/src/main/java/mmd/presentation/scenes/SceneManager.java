package mmd.presentation.scenes;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.SceneNameEnum;
import mmd.common.enums.StageNameEnum;
import mmd.presentation.PresentationModule;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class SceneManager
{
    private static HashMap<SceneNameEnum, String> scenePath = null;
    private static HashMap<StageNameEnum, String> stagePath = null;
    private static Stage window = null;
    private static Stack<Stage> windows = null;

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
	windows = new Stack<Stage>();
	windows.push(window);
	scenePath = new HashMap<SceneNameEnum, String>();
	stagePath = new HashMap<StageNameEnum, String>();
	registerPaths();
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
		    ExtensionFilter extensionFilter = new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg",
			    "*.bmp");
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

    public static void showStage(final StageNameEnum stageName) throws IOException
    {
	showStage(stageName, Modality.APPLICATION_MODAL);
    }

    public static void showStage(final StageNameEnum stageName, final Modality modality) throws IOException
    {
	Stage stage = getStage(stagePath.get(stageName));
	stage.initModality(modality);
	stage.initOwner(window);
	windows.push(stage);
	stage.show();
    }

    private static Scene getScene(final String name) throws IOException
    {

	Scene result = new Scene(getParentFromFile(name));
	return result;
    }

    private static Stage getStage(final String name) throws IOException
    {

	Stage result = new Stage();
	result.setScene(new Scene(getParentFromFile(name)));
	return result;
    }

    private static void registerPaths()
    {
	scenePath.put(SceneNameEnum.LoginScreen, "LoginScreenScene");
	scenePath.put(SceneNameEnum.MainScreen, "MainScreenScene");
	scenePath.put(SceneNameEnum.RegisterScreen, "RegisterScreenScene");
	stagePath.put(StageNameEnum.AddMovie, "AddMovieStage");
    }
}
