package mmd.presentation.scenes;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class MovieTileVBox extends VBox
{
    public MovieTileVBox()
    {
	super();
	super.setMaxSize(150, 200);
	super.setMinSize(150, 200);
	super.setPrefSize(150, 200);
	super.setBackground(Background.EMPTY);
	String bgStyle = "-fx-background-color: #303030";
	super.setStyle(bgStyle);
	DropShadow ds = this.createDropShadow();
	super.setEffect(ds);
	super.setAlignment(Pos.CENTER);
	VBox mainVBox = this.createMainVBox();
	super.getChildren().add(mainVBox);
    }

    private DropShadow createDropShadow()
    {
	DropShadow ds = new DropShadow();
	ds.setOffsetX(5.0);
	ds.setOffsetX(5.0);
	ds.setColor(Color.GRAY);
	return ds;
    }

    private VBox createMainVBox()
    {
	VBox main = new VBox();
	main.setBackground(Background.EMPTY);
	main.setMaxSize(120, 170);
	main.setMinSize(120, 170);
	main.setPrefSize(120, 170);
	String bgStyle = "-fx-background-color: #fff";
	main.setStyle(bgStyle);
	return main;
    }
}
