package mmd.presentation;

import javafx.application.Application;
import javafx.stage.Stage;
import mmd.common.enums.SceneNameEnum;
import mmd.persistence.PersistenceModule;
import mmd.presentation.scenes.SceneManager;
import mmd.presentation.stages.StageManager;
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
	    SceneManager.init(primaryStage);
	    StageManager.init(primaryStage);
	    SceneManager.changeScene(SceneNameEnum.MainScreen);
	    primaryStage.show();
	}
	catch (Throwable e) {
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

}
