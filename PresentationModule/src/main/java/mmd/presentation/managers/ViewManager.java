package mmd.presentation.managers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import mmd.common.enums.FileDialogMode;
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.SceneNameEnum;
import mmd.common.types.GenericData;
import mmd.common.types.Tuple;
import mmd.common.types.WindowData;
import mmd.presentation.PresentationModule;
import mmd.util.errorhandling.ErrorHandlerUtil;

/**
 * Util to handle all windows , there are operations like :
 * <ul>
 * <li>data transfer</li>
 * <li>change scene of a stage</li>
 * <li>show or close window</li>
 * <li>file dialogs</li>
 * </ul>
 */
public class ViewManager
{
	
	private static WindowData mainWindow = null;
	
	private static HashMap<SceneNameEnum, String> scenePathMap = null;
	
	private static Stack<Tuple<SceneNameEnum, WindowData>> windows = null;
	
	/**
	 * Get a window binded data using its stage
	 * 
	 * @param stage Window binded stage
	 * @return Data binded to that stage
	 */
	private static Tuple<SceneNameEnum, WindowData> getWindowDataByStage(final Stage stage)
	{
		Tuple<SceneNameEnum, WindowData> result = new Tuple<SceneNameEnum, WindowData>(null, null);
		
		for (Tuple<SceneNameEnum, WindowData> tuple : windows)
		{
			if (tuple.getSecond().getStage().equals(stage))
			{
				result = tuple;
				break;
			}
		}
		
		return result;
		
	}
	
	/**
	 * Change window scene
	 * 
	 * @param stage     Stage of what scene need to be changed
	 * @param sceneName Window name
	 * @throws Exception
	 */
	public static void changeScene(final Stage stage, final SceneNameEnum sceneName)
	        throws Exception
	{
		if (!scenePathMap.containsKey(sceneName)) { throw new Exception(
		        "Scene path for " + sceneName.toString() + " do not exist"); }
		Tuple<SceneNameEnum, WindowData> window = getWindowDataByStage(stage);
		window.setFirst(sceneName);
		FXMLLoader loader = getFXMLLoaderFromFile(scenePathMap.get(sceneName));
		
		stage.setScene(new Scene(loader.load()));
		stage.setUserData(loader);
		
	}
	
	/**
	 * Close a window using one of its child node
	 * 
	 * @param element Child of the window to be closed
	 */
	public static void closeParentStage(final Node element)
	{
		Stage stage = ((Stage) element.getScene().getWindow());
		FXMLLoader loader = (FXMLLoader) stage.getUserData();
		stage.close();
		windows.remove(getWindow(((ControllerBase) loader.getController()).getName()));
		
	}
	
	/**
	 * Check if a window exist in window stack
	 * 
	 * @param stageName Window Name
	 * @return boolean value if true window exist false elsewhere
	 */
	public static boolean existWindow(final SceneNameEnum stageName)
	{
		
		for (Tuple<SceneNameEnum, WindowData> tuple : windows)
		{
			if (tuple.getFirst().equals(stageName)) { return true; }
		}
		return false;
	}
	
	/**
	 * Get FXML Loader From a file
	 * 
	 * @param fileName Path to the file
	 * @return {@link FXMLLoader}
	 * @throws IOException Open file error
	 */
	public static FXMLLoader getFXMLLoaderFromFile(final String fileName) throws IOException
	{
		String scenePath = "views/".concat(fileName).concat(".fxml");
		FXMLLoader result = new FXMLLoader(PresentationModule.class.getResource(scenePath));
		return result;
	}
	
	/**
	 * Get main window
	 * 
	 * @return {@link WindowData}
	 */
	public static WindowData getMainWindow()
	{
		return mainWindow;
	}
	
	/**
	 * Get a window binded data
	 * 
	 * @param stageName Window Name
	 * @return Data binded to that window
	 */
	public static WindowData getStageData(final SceneNameEnum stageName)
	{
		WindowData result = null;
		try
		{
			if (existWindow(stageName))
			{
				result = getWindow(stageName).getSecond();
			}
			else
			{
				throw new NullPointerException(
				        "Stagename " + stageName.toString() + " not found in windows stack");
			}
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		return result;
		
	}
	
	/**
	 * Get a window
	 * 
	 * @param stageName Window Name
	 * @return {@link Tuple} of {@link SceneNameEnum} and {@link WindowData}
	 */
	public static Tuple<SceneNameEnum, WindowData> getWindow(final SceneNameEnum stageName)
	{
		Tuple<SceneNameEnum, WindowData> result = null;
		if (existWindow(stageName))
		{
			for (Tuple<SceneNameEnum, WindowData> tuple : windows)
			{
				if (tuple.getFirst().equals(stageName))
				{
					result = tuple;
					break;
				}
			}
		}
		else
		{
			throw new NullPointerException(
			        "Stagename " + stageName.toString() + " not found in windows stack");
		}
		return result;
	}
	
	public static void init()
	{
		mainWindow = new WindowData(null, null);
		windows = new Stack<Tuple<SceneNameEnum, WindowData>>();
		scenePathMap = new HashMap<SceneNameEnum, String>();
		registerPaths();
	}
	
	/**
	 * Open a file chooser pop-up
	 * 
	 * @param fileType Type of file to chose
	 * @param mode     File Dialog Mode
	 * @return File or null if didn't chose
	 */
	public static File fileDialog(final FileTypeEnum fileType, FileDialogMode mode)
	{
		File result = null;
		try
		{
			FileChooser fc = new FileChooser();
			
			if (!fileType.equals(FileTypeEnum.All))
			{
				ExtensionFilter extensionFilter = null;
				switch (fileType)
				{
					case Image:
						extensionFilter = new ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg",
						        "*.bmp");
						fc.getExtensionFilters().add(extensionFilter);
						break;
					case XML:
						extensionFilter = new ExtensionFilter("XML Data", "*.xml");
						fc.getExtensionFilters().add(extensionFilter);
						break;
					default:
						break;
				}
				
				switch (mode)
				{
					case Open:
						result = fc.showOpenDialog(mainWindow.getStage());
						break;
					
					case Save:
						result = fc.showSaveDialog(mainWindow.getStage());
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
		return result;
		
	}
	
	/**
	 * Set current Main Window
	 * 
	 * @param stage
	 */
	public static void setMainWindow(final Stage stage)
	{
		mainWindow.setStage(stage);
	}
	
	/**
	 * Set a window binded data
	 * 
	 * @param stageName Window Name
	 * @param data      Data to bind
	 */
	public static void setWindowData(final SceneNameEnum stageName, final GenericData stageData)
	{
		try
		{
			if (existWindow(stageName))
			{
				windows.peek().getSecond().setData(stageData);
			}
			else
			{
				throw new NullPointerException(
				        "Stagename " + stageName.toString() + " not found in windows stack");
			}
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		
	}
	
	/**
	 * Set a window binded stage
	 * 
	 * @param stageName Window Name
	 * @param stage     Stage to bind
	 */
	public static void setWindowStage(final SceneNameEnum stageName, final Stage stage)
	{
		try
		{
			if (existWindow(stageName))
			{
				windows.peek().getSecond().setStage(stage);
			}
			else
			{
				throw new NullPointerException(
				        "Stagename " + stageName.toString() + " not found in windows stack");
			}
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		
	}
	
	/**
	 * Show a window pop-up
	 * 
	 * @param stageName Scene Name
	 */
	public static void showStage(final SceneNameEnum stageName)
	{
		showStage(stageName, Modality.APPLICATION_MODAL, null, null);
	}
	
	/**
	 * Show a window pop-up
	 * 
	 * @param stageName Scene Name
	 * @param function  Event to handle on closing
	 * @param data      Data to bind to this window
	 */
	public static void showStage(final SceneNameEnum stageName,
	        final EventHandler<WindowEvent> function, final GenericData data)
	{
		showStage(stageName, Modality.APPLICATION_MODAL, function, data);
	}
	
	/**
	 * Show a window pop-up
	 * 
	 * @param stageName Scene Name
	 * @param data      Data to bind to this window
	 */
	public static void showStage(final SceneNameEnum stageName, final GenericData data)
	{
		showStage(stageName, Modality.APPLICATION_MODAL, null, data);
	}
	
	/**
	 * Show a window pop-up
	 * 
	 * @param stageName Scene Name
	 * @param modality  Modality of the window
	 * @param event     Event to handle on closing
	 * @param data      Data to bind to this window
	 */
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
			if (mainWindow != null)
			{
				stage.initOwner(mainWindow.getStage());
			}
			
			if (event != null)
			{
				stage.setOnHiding(event);
			}
			stage.addEventHandler(WindowEvent.WINDOW_HIDING, new EventHandler<WindowEvent>()
			{
				
				@Override
				public void handle(WindowEvent event)
				{
					FXMLLoader loader = (FXMLLoader) stage.getUserData();
					((ControllerBase) loader.getController()).shutdown(event);
				}
				
			});
			
			setWindowStage(stageName, stage);
			
			stage.show();
		}
		catch (Throwable e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	/**
	 * Add a window to Window stack
	 * 
	 * @param stageName Window name
	 */
	private static void addWindowToStack(final SceneNameEnum stageName)
	{
		try
		{
			windows.push(
			        new Tuple<SceneNameEnum, WindowData>(stageName, new WindowData(null, null)));
		}
		catch (Throwable e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
		
	}
	
	/**
	 * Register to scenePathMap {@link HashMap} fxml file names
	 */
	private static void registerPaths()
	{
		scenePathMap.put(SceneNameEnum.AddMovie, "AddMovieStage");
		scenePathMap.put(SceneNameEnum.MainScreen, "MainScreenScene");
		scenePathMap.put(SceneNameEnum.MovieDetails, "MovieDetailsStage");
		scenePathMap.put(SceneNameEnum.Login, "LoginScreenScene");
		scenePathMap.put(SceneNameEnum.Register, "RegisterScreenScene");
	}
}
