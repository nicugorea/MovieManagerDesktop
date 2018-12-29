package mmd.presentation;

import java.util.LinkedList;

import javafx.application.Application;
import javafx.stage.Stage;
import mmd.common.models.MovieDM;
import mmd.common.models.Property;
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
	    MovieDM dm = new MovieDM();

	    dm.setTitle("Movie title");
	    dm.setDescription("A description right here");
	    dm.setIMDbID("adasdasd-a-d12-e1d");
	    dm.setImgPath("");
	    dm.setScore(9.5f);
	    LinkedList<String> list = new LinkedList<String>();
	    list.add("SF");
	    list.add("Fighting");
	    list.add("Anime");
	    dm.setCategories(list);

	    PropertyIO.saveDMDefinition(dm, System.getProperty("user.dir") + "/movies.xml", "Movies");

	    MovieDM newd=new MovieDM();
	    System.out.println(newd.getTitle());
	    System.out.println(newd.getDescription());
	    System.out.println(newd.getIMDbID());
	    System.out.println(newd.getImgPath());
	    System.out.println(newd.getScore());

	    newd= Property.getObjectFromProperties(dm.getProperties());
	    System.out.println(newd.getTitle());
	    System.out.println(newd.getDescription());
	    System.out.println(newd.getIMDbID());
	    System.out.println(newd.getImgPath());
	    System.out.println(newd.getScore());
	    for (String string : newd.getCategories())
	    {
		System.out.println(string);
	    }

	}
	catch (Throwable e) {
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

}
