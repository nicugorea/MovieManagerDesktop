package mmd.presentation.stages;

import java.io.File;
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
import mmd.common.enums.StageNameEnum;
import mmd.common.types.GenericData;
import mmd.common.types.Tuple;
import mmd.presentation.scenes.SceneManager;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class StageManager
{
    private static Stage mainWindow = null;

    private static HashMap<StageNameEnum, String> stagePathMap = null;

    private static Stack<Tuple<StageNameEnum, GenericData>> windows = null;

    public static void closeParentStage(final Node element)
    {
	Stage stage = ((Stage) element.getScene().getWindow());
	FXMLLoader loader = (FXMLLoader) stage.getUserData();
	stage.close();
	windows.remove(getWindow(
		((ControllerBase) loader.getController()).getName()));

    }

    public static boolean existWindow(final StageNameEnum stageName)
    {

	for (Tuple<StageNameEnum, GenericData> tuple : windows)
	{
	    if(tuple.getFirst().equals(stageName))
	    {
		return true;
	    }
	}
	return false;
    }

    public static GenericData getStageData(final StageNameEnum stageName)
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

    public static Tuple<StageNameEnum, GenericData> getWindow(final StageNameEnum stageName)
    {
	Tuple<StageNameEnum, GenericData> result = null;
	if(existWindow(stageName))
	{
	    for (Tuple<StageNameEnum, GenericData> tuple : windows)
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

    public static void init(final Stage stage)
    {
	mainWindow = stage;
	windows = new Stack<Tuple<StageNameEnum, GenericData>>();
	stagePathMap = new HashMap<StageNameEnum, String>();
	registerPaths();
	addWindowToStack(StageNameEnum.MainWindow, null);
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

    public static void setStageData(final StageNameEnum stageName, final GenericData stageData)
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

    public static void showStage(final StageNameEnum stageName)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, null, null);
    }

    public static void showStage(final StageNameEnum stageName, final EventHandler<WindowEvent> function,
	    final GenericData data)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, function, data);
    }

    public static void showStage(final StageNameEnum stageName, final GenericData data)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, null, data);
    }

    public static void showStage(final StageNameEnum stageName, final Modality modality,
	    final EventHandler<WindowEvent> event, final GenericData data)
    {
	try
	{
	    FXMLLoader loader = SceneManager.getFXMLLoaderFromFile(stagePathMap.get(stageName));
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

	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

    private static void addWindowToStack(final StageNameEnum stageName, final GenericData data)
    {
	try
	{
	    windows.push(new Tuple<StageNameEnum, GenericData>(stageName, data));
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}

    }

    private static void registerPaths()
    {
	stagePathMap.put(StageNameEnum.AddMovie, "AddMovieStage");
	stagePathMap.put(StageNameEnum.MainWindow, "MainScreenScene");
	stagePathMap.put(StageNameEnum.MovieDetails, "MovieDetailsStage");

    }
}
