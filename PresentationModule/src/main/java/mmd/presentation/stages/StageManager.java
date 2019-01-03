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
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.StageNameEnum;
import mmd.common.types.Tuple;
import mmd.presentation.scenes.SceneManager;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class StageManager
{
    private static Stage mainWindow = null;


    private static HashMap<StageNameEnum, String> stagePath = null;

    private static Stack<Tuple<StageNameEnum, Stage>> windows = null;

    public static void closeParentStage(final Node element) {
	((Stage)element.getScene().getWindow()).close();
    }

    public static void init(final Stage stage)
    {

	mainWindow = stage;
	windows = new Stack<Tuple<StageNameEnum, Stage>>();
	windows.push(new Tuple<StageNameEnum, Stage>(StageNameEnum.MainWindow, mainWindow));
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
	return fc.showOpenDialog(mainWindow);
    }

    public static void showStage(final StageNameEnum stageName)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, null);
    }

    public static void showStage(final StageNameEnum stageName, final EventHandler<WindowEvent> function)
    {
	showStage(stageName, Modality.APPLICATION_MODAL, function);
    }

    public static void showStage(final StageNameEnum stageName, final Modality modality,
	    final EventHandler<WindowEvent> function)
    {
	try
	{
	    Stage stage = getStage(stagePath.get(stageName));
	    stage.initModality(modality);
	    stage.initOwner(mainWindow);
	    windows.push(new Tuple<StageNameEnum, Stage>(stageName, stage));
	    if(function != null)
	    {
		stage.setOnCloseRequest(function);
	    }
	    stage.show();

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
	stagePath.put(StageNameEnum.AddMovie, "AddMovieStage");

    }
}
