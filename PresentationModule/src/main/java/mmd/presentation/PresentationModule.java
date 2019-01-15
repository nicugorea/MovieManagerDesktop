package mmd.presentation;

import java.awt.Dialog.ModalityType;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mmd.common.enums.SceneNameEnum;
import javafx.stage.Modality;
import mmd.persistence.PersistenceModule;
import mmd.presentation.managers.ViewManager;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.logging.LogLevel;
import mmd.util.logging.LoggingUtil;

/**
 * 
 * <h1>Presentation module main class</h1>
 * <p>
 * Here are create all elements that are needed for this module
 * </p>
 */
public class PresentationModule extends Application {

	/**
	 * Main method that is the main entry point of the application
	 */
	public static void main(final String args[]) {
		LoggingUtil.getLogger().log(LogLevel.DEBUG, "Begin Presentation Launch");
		launch();
		LoggingUtil.getLogger().log(LogLevel.DEBUG, "Finish Presentation Launch");
	}

	
	@Override
	public void start(final Stage primaryStage) throws Exception {
		try {
			PersistenceModule.init();
			ViewManager.init();
			SceneNameEnum mainStage = SceneNameEnum.Login;

			LoggingUtil.getLogger().log(LogLevel.DEBUG, "Before show stage");
			ViewManager.showStage(mainStage, Modality.WINDOW_MODAL,new EventHandler<WindowEvent>() {

				/**
				 * Method called after main window is closed
				 */
				@Override
				public void handle(final WindowEvent event) {
					// TODO: After main Window is closed
				}
			}, null);

			LoggingUtil.getLogger().log(LogLevel.DEBUG, "Ater show stage");
			ViewManager.setMainWindow(ViewManager.getStageData(mainStage).getStage());
			
		} catch (Throwable e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
	}

}
