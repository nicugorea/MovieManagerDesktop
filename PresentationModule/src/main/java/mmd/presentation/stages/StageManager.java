package mmd.presentation.stages;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.SceneNameEnum;
import mmd.common.types.GenericData;
import mmd.common.types.Tuple;
import mmd.presentation.PresentationModule;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class StageManager
{
    private static Stage mainWindow = null;

    private static HashMap<SceneNameEnum, String> scenePathMap = null;

    private static Stack<Tuple<SceneNameEnum, GenericData>> windows = null;

    public static void changeScene(final Stage stage, final SceneNameEnum sceneName) throws Exception
    {
	if(!scenePathMap.containsKey(sceneName))
	{
	    throw new Exception("Scene path for " + sceneName.toString() + " do not exist");
	}
	stage.setScene(new Scene(getFXMLLoaderFromFile(scenePathMap.get(sceneName)).load()));
    }

    public static void closeParentStage(final Node element)
    {
	Stage stage = ((Stage) element.getScene().getWindow());
	FXMLLoader loader = (FXMLLoader) stage.getUserData();
	stage.close();
	windows.remove(getWindow(
		((ControllerBase) loader.getController()).getName()));

    }

    public static boolean existWindow(final SceneNameEnum stageName)
    {

	for (Tuple<SceneNameEnum, GenericData> tuple : windows)
	{
	    if(tuple.getFirst().equals(stageName))
	    {
		return true;
	    }
	}
	return false;
    }

    public static FXMLLoader getFXMLLoaderFromFile(final String fileName) throws IOException
    {
	String scenePath = "views/".concat(fileName).concat(".fxml");
	FXMLLoader result = new FXMLLoader(PresentationModule.class.getResource(scenePath));
	return result;
    }

    public static Stage getMainStage()
    {
	return mainWindow;
    }

    public static GenericData getStageData(final SceneNameEnum stageName)
    {
	GenericData result = null;
	try
	{
	    if(existWindow(stageName))
	    {
		result = getWindow(stageName).getSecond();
	    }
	    else
	    {
		throw new NullPointerException("Stagename " + stageName.toString() + " not found in windows stack");
	    }
	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return result;

    }

    public static Tuple<SceneNameEnum, GenericData> getWindow(final SceneNameEnum stageName)
    {
	Tuple<SceneNameEnum, GenericData> result = null;
	if(existWindow(stageName))
	{
	    for (Tuple<SceneNameEnum, GenericData> tuple : windows)
	    {
		if(tuple.getFirst().equals(stageName))
		{
		    result = tuple;
		    break;
		}
	    }
	}
	else
	{
	    throw new NullPointerException("Stagename " + stageName.toString() + " not found in windows stack");
	}
	return result;
    }

    public static void init()
    {
	windows = new Stack<Tuple<SceneNameEnum, GenericData>>();
	scenePathMap = new HashMap<SceneNameEnum, String>();
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
	return fc.showOpenDialog(mainWindow);
    }

    public static void setMainWindow(final Stage stage) {
	mainWindow=stage;
    }

    public static void setStageData(final SceneNameEnum stageName, final GenericData stageData)
    {
	try
	{
	    if(existWindow(stageName))
	    {
		windows.peek().setSecond(stageData);
	    }
	    else
	    {
		throw new NullPointerException("Stagename " + stageName.toString() + " not found in windows stack");
	    }
	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

    }

    public static Stage showStage(final SceneNameEnum stageName)
    {
	return showStage(stageName, Modality.APPLICATION_MODAL, null, null);
    }


    public static Stage showStage(final SceneNameEnum stageName, final EventHandler<WindowEvent> function,
	    final GenericData data)
    {
	return showStage(stageName, Modality.APPLICATION_MODAL, function, data);
    }

    public static Stage showStage(final SceneNameEnum stageName, final GenericData data)
    {
	return showStage(stageName, Modality.APPLICATION_MODAL, null, data);
    }

    public static Stage showStage(final SceneNameEnum stageName, final Modality modality,
	    final EventHandler<WindowEvent> event, final GenericData data)
    {
	Stage result = null;
	try
	{
	    FXMLLoader loader = getFXMLLoaderFromFile(scenePathMap.get(stageName));
	    addWindowToStack(stageName, data);
	    Stage stage = new Stage();
	    stage.setScene(new Scene(loader.load()));
	    stage.setUserData(loader);
	    stage.initModality(modality);
	    stage.initOwner(mainWindow);
	    if(event != null)
	    {
		stage.setOnHiding(event);
	    }
	    stage.show();
	    result = stage;
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return result;
    }

    private static void addWindowToStack(final SceneNameEnum stageName, final GenericData data)
    {
	try
	{
	    windows.push(new Tuple<SceneNameEnum, GenericData>(stageName, data));
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

    }

    private static void registerPaths()
    {
	scenePathMap.put(SceneNameEnum.AddMovie, "AddMovieStage");
	scenePathMap.put(SceneNameEnum.MainScreen, "MainScreenScene");
	scenePathMap.put(SceneNameEnum.MovieDetails, "MovieDetailsStage");
	scenePathMap.put(SceneNameEnum.Login, "LoginScreenScene");
	scenePathMap.put(SceneNameEnum.Register, "RegisterScreenScene");

    }
}
