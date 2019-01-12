package mmd.presentation;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mmd.common.enums.SceneNameEnum;
import mmd.persistence.PersistenceModule;
import mmd.presentation.managers.ViewManager;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.logging.LogLevel;
import mmd.util.logging.LoggingUtil;

public class PresentationModule extends Application
{

    public static void main(final String args[])
    {
	LoggingUtil.getLogger().log(LogLevel.DEBUG, "Begin Presentation Launch");
	launch();
	LoggingUtil.getLogger().log(LogLevel.DEBUG, "Finish Presentation Launch");
    }

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
	try {
	    PersistenceModule.init();
	    ViewManager.init();
	    SceneNameEnum mainStage = SceneNameEnum.MainScreen;
	    ViewManager.showStage(mainStage,new EventHandler<WindowEvent>()
	    {

		@Override
		public void handle(final WindowEvent event)
		{
		    // TODO: After main Window is closed
		}
	    },null);
	    ViewManager.setMainWindow(ViewManager.getStageData(mainStage).getStage());
	}
	catch (Throwable e) {
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

}
