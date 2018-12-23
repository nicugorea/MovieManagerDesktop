package mmd.presentation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mmd.util.logging.LogLevel;
import mmd.util.logging.LoggingUtil;

public class PresentationModule extends Application
{

    public static void main(final String args[])
    {
	launch();
	LoggingUtil.getLogger().log(LogLevel.CONFIG, "launch()");
    }

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
	Label label = new Label();
	VBox parent = new VBox();
	parent.getChildren().add(label);
	LoggingUtil.getLogger().log(LogLevel.DEBUG, "start");
	label.setText("dasd");
	LoggingUtil.getLogger().log(LogLevel.FINEST, "dasd");
	Scene scene = new Scene(parent);
	primaryStage.setScene(scene);
	primaryStage.show();

    }

}
