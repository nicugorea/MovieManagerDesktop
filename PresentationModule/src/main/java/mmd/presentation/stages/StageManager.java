package mmd.presentation.stages;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.StageNameEnum;
import mmd.common.types.Tuple;
import mmd.presentation.controllers.AddMovieController;
import mmd.presentation.controllers.MainScreenController;
import mmd.presentation.controllers.MovieDetailsController;
import mmd.presentation.scenes.SceneManager;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class StageManager
{
    private static Stage mainWindow = null;

    private static HashMap<StageNameEnum, Class<?>> stageControllerList = null;

    private static HashMap<StageNameEnum, ControllerBase> stageControllerMap = null;

    private static HashMap<StageNameEnum, String> stagePathMap = null;

    private static Stack<StageNameEnum> windows = null;

    public static void closeParentStage(final Node element)
    {
	((Stage) element.getScene().getWindow()).close();
    }

    public static Tuple<Object, Class<?>> getStageData(final StageNameEnum stageName)
    {
	if(stageControllerMap.containsKey(stageName))
	{
	    return stageControllerMap.get(stageName).getStageData();
	}
	else
	{
	    throw new NullPointerException();
	}
    }

    public static void init(final Stage stage)
    {

	mainWindow = stage;
	windows = new Stack<StageNameEnum>();
	stageControllerList = new HashMap<>();
	stageControllerMap = new HashMap<>();
	stagePathMap = new HashMap<StageNameEnum, String>();
	registerPaths();
	registerStages();
	addWindowToStack(StageNameEnum.MainWindow);
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

    public static void setStageData(final StageNameEnum stageName, final Tuple<Object, Class<?>> stageData)
    {
	if(stageControllerMap.containsKey(stageName))
	{
	    stageControllerMap.get(stageName).setStageData(stageData);
	}
    }

    public static void showStage(final StageNameEnum stageName)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, null, null);
    }

    public static void showStage(final StageNameEnum stageName, final EventHandler<WindowEvent> function,
	    final Tuple<Object, Class<?>> data)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, function, data);
    }

    public static void showStage(final StageNameEnum stageName, final Modality modality,
	    final EventHandler<WindowEvent> event, final Tuple<Object, Class<?>> data)
    {
	try
	{
	    addWindowToStack(stageName);
	    setStageData(stageName, data);
	    Stage stage = getStage(stagePathMap.get(stageName));
	    stage.initModality(modality);
	    stage.initOwner(mainWindow);
	    if(event != null)
	    {
		stage.setOnHiding(event);
	    }
	    stage.show();
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

    public static void showStage(final StageNameEnum stageName, final Tuple<Object, Class<?>> data)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, null, data);
    }

    private static void addWindowToStack(final StageNameEnum stageName)
    {
	windows.push(stageName);
	try
	{
	    if(stageControllerList.containsKey(stageName))
	    {
		stageControllerMap.put(stageName, (ControllerBase) stageControllerList.get(stageName).newInstance());
	    }
	    else
	    {
		ErrorHandlerUtil
		.handleThrowable(new Throwable("stageControllerList have no entry -> " + stageName.toString()));
	    }
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

    }

    private static Stage getStage(final String name) throws IOException
    {

	Stage result = new Stage();
	result.setScene(new Scene(SceneManager.getParentFromFile(name)));
	return result;
    }

    private static void registerPaths()
    {
	stagePathMap.put(StageNameEnum.AddMovie, "AddMovieStage");
	stagePathMap.put(StageNameEnum.MainWindow, "MainScreenScene");
	stagePathMap.put(StageNameEnum.MovieDetails, "MovieDetailsStage");

    }

    private static void registerStages()
    {
	stageControllerList.put(StageNameEnum.AddMovie, AddMovieController.class);
	stageControllerList.put(StageNameEnum.MainWindow, MainScreenController.class);
	stageControllerList.put(StageNameEnum.MovieDetails, MovieDetailsController.class);
    }
}
