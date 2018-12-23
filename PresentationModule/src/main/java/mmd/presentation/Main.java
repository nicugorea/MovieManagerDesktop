package mmd.presentation;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mmd.common.interfaces.Dsad;

public class Main extends Application
{

    public static void main(final String args[])
    {
	launch();
    }

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
	Label label = new Label();
	VBox parent = new VBox();
	parent.getChildren().add(label);
	label.setText(Dsad.s());
	Scene scene = new Scene(parent);
	primaryStage.setScene(scene);
	primaryStage.show();

    }

}
