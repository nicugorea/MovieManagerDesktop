package mmd.presentation;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import mmd.common.models.MovieDM;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.scenes.SceneManager;
import mmd.presentation.scenes.SceneName;
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

	    SceneManager.init(primaryStage);
	    SceneManager.changeScene(SceneName.MainScreen);
	    List<MovieDM> dms = PropertyIO.getDMDefinitionFromFile(System.getProperty("user.dir") + "/movies.xml", MovieDM.class);

	    for (MovieDM movieDM : dms)
	    {
		System.out.println(movieDM.getTitle());
		System.out.println(movieDM.getDescription());
		System.out.println(movieDM.getIMDbID());
		System.out.println(movieDM.getScore());
		System.out.println(movieDM.getImgPath());
		for (String category : movieDM.getCategories())
		{

		    System.out.println("\t"+category);
		}
	    }


	}
	catch (Throwable e) {
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

}
