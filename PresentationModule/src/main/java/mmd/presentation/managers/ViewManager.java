package mmd.presentation.managers;

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
import mmd.common.types.WindowData;
import mmd.presentation.PresentationModule;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class ViewManager
{
    private static WindowData mainWindow = null;

    private static HashMap<SceneNameEnum, String> scenePathMap = null;

    private static Stack<Tuple<SceneNameEnum, WindowData>> windows = null;

    private static Tuple<SceneNameEnum, WindowData> getWindowDataByStage(final Stage stage) {
	Tuple<SceneNameEnum, WindowData> result = new Tuple<SceneNameEnum, WindowData>(null, null);

	for (Tuple<SceneNameEnum, WindowData> tuple : windows)
	{
	    if(tuple.getSecond().getStage().equals(stage))
	    {
		result=tuple;
		break;
	    }
	}

	return result;

    }

    public static void changeScene(final Stage stage, final SceneNameEnum sceneName) throws Exception
    {
	if(!scenePathMap.containsKey(sceneName))
	{
	    throw new Exception("Scene path for " + sceneName.toString() + " do not exist");
	}
	getWindowDataByStage(stage).setFirst(sceneName);
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

	for (Tuple<SceneNameEnum, WindowData> tuple : windows)
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

    public static WindowData getMainStage()
    {
	return mainWindow;
    }

    public static WindowData getStageData(final SceneNameEnum stageName)
    {
	WindowData result = null;
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

    public static Tuple<SceneNameEnum, WindowData> getWindow(final SceneNameEnum stageName)
    {
	Tuple<SceneNameEnum, WindowData> result = null;
	if(existWindow(stageName))
	{
	    for (Tuple<SceneNameEnum, WindowData> tuple : windows)
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
	mainWindow=new WindowData(null, null);
	windows = new Stack<Tuple<SceneNameEnum, WindowData>>();
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
	return fc.showOpenDialog(mainWindow.getStage());
    }

    public static void setMainWindow(final Stage stage) {
	mainWindow.setStage(stage);
    }

    public static void setWindowData(final SceneNameEnum stageName, final GenericData stageData)
    {
	try
	{
	    if(existWindow(stageName))
	    {
		windows.peek().getSecond().setData(stageData);
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

    public static void setWindowStage(final SceneNameEnum stageName, final Stage stage)
    {
	try
	{
	    if(existWindow(stageName))
	    {
		windows.peek().getSecond().setStage(stage);
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

    public static void showStage(final SceneNameEnum stageName)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, null, null);
    }


    public static void showStage(final SceneNameEnum stageName, final EventHandler<WindowEvent> function,
	    final GenericData data)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, function, data);
    }

    public static void showStage(final SceneNameEnum stageName, final GenericData data)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, null, data);
    }

    public static void showStage(final SceneNameEnum stageName, final Modality modality,
	    final EventHandler<WindowEvent> event, final GenericData data)
    {
	try
	{
	    FXMLLoader loader = getFXMLLoaderFromFile(scenePathMap.get(stageName));
	    addWindowToStack(stageName);
	    setWindowData(stageName, data);

	    Stage stage = new Stage();
	    stage.setScene(new Scene(loader.load()));
	    stage.setUserData(loader);
	    stage.initModality(modality);
	    if(mainWindow!=null)
	    {
		stage.initOwner(mainWindow.getStage());
	    }

	    if(event != null)
	    {
		stage.setOnHiding(event);
	    }

	    setWindowStage(stageName, stage);

	    stage.show();
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

    private static void addWindowToStack(final SceneNameEnum stageName)
    {
	try
	{
	    windows.push(new Tuple<SceneNameEnum, WindowData>(stageName, new WindowData(null, null)));
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
