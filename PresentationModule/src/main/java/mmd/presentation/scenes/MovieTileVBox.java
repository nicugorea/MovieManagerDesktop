package mmd.presentation.scenes;

import java.io.IOException;
import java.io.InputStream;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MovieTileVBox extends VBox
{
    public MovieTileVBox()
    {
	super();
	this.createMainVBox();
	super.getChildren().add(this.createPrimaryRegion());
	super.getChildren().add(this.createActionBar());

    }

    private final int CONTENT_MARGIN = 6;

    private final int TILE_HEIGHT = 300;

    private final int TILE_RADIUS = 4;

    private final int TILE_WIDTH = 240;

    private HBox createActionBar()
    {
	HBox result = new HBox();
	return result;
    }

    private DropShadow createDropShadow()
    {
	DropShadow ds = new DropShadow();
	ds.setOffsetX(3.0);
	ds.setOffsetY(3.0);
	ds.setColor(Color.GRAY);
	return ds;
    }

    private void createMainVBox()
    {
	super.setMaxSize(super.getPrefHeight(), super.getPrefWidth());
	super.setMinSize(super.getPrefHeight(), super.getPrefWidth());
	super.setPrefSize(this.TILE_WIDTH, this.TILE_HEIGHT);
	super.setAlignment(Pos.TOP_CENTER);
	super.setBackground(Background.EMPTY);
	String styles = String.format("-fx-background-color: #fff;"
	        + "-fx-background-radius: %d ;", this.TILE_RADIUS);
	super.setStyle(styles);
	super.setEffect(this.createDropShadow());
    }

    private VBox createPrimaryBottomRegion()
    {
	VBox element = new VBox();
	element.setAlignment(Pos.TOP_LEFT);
	Text description = new Text();
	description.setText(
	        "Suspendisse eget purus vel eros pellentesque egestas. In ac feugiat purus.");

	description.setWrappingWidth(this.TILE_WIDTH - 2 * this.CONTENT_MARGIN);
	description.setFont(Font.font("Roboto", FontWeight.NORMAL, 14));
	description.setFill(Paint.valueOf("#0000008a"));
	element.getChildren().add(description);

	return element;

    }

    private VBox createPrimaryRegion()
    {
	VBox element = new VBox();
	element.setAlignment(Pos.TOP_LEFT);
	element.getChildren().add(this.createThumbnail());
	element.getChildren().add(this.createPrimaryTopRegion());
	element.getChildren().add(this.createPrimaryBottomRegion());

	// from second element
	for (int i = 1; i < element.getChildren().size(); i++)
	{
	    VBox.setMargin(element.getChildren().get(i), new Insets(this.CONTENT_MARGIN));
	}

	return element;

    }

    private VBox createPrimaryTopRegion()
    {
	VBox element = new VBox();
	element.setAlignment(Pos.TOP_CENTER);

	Text title = new Text();
	title.setText("Movie Title");
	title.setWrappingWidth(this.TILE_WIDTH - 2 * this.CONTENT_MARGIN);
	title.setFont(Font.font("Seagoe UI Light", FontWeight.MEDIUM, 20));
	title.setFill(Paint.valueOf("#000000"));

	Text categories = new Text();
	categories.setText("Holiday, Fighting, Comedy");
	categories.setWrappingWidth(this.TILE_WIDTH - 2 * this.CONTENT_MARGIN);
	categories.setFont(Font.font("Seagoe UI Light", FontWeight.MEDIUM, 14));
	categories.setFill(Paint.valueOf("#0000008a"));

	element.getChildren().add(title);
	element.getChildren().add(categories);
	return element;

    }

    private VBox createThumbnail()
    {
	VBox thumbnail = new VBox();

	String styles = String.format(
	        "-fx-border-radius: %d %d 0 0;"
	                + "-fx-background-radius: 6 6 0 0 ;",
	        this.CONTENT_MARGIN, this.CONTENT_MARGIN);

	// create a input stream
	InputStream input = null;
	try
	{
	    input = this.getClass().getResource("bg.jpg").openStream();
	}
	catch (IOException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	Image image = new Image(input);

	VBox bp = new VBox();

	bp.setPrefSize(this.TILE_WIDTH, this.TILE_HEIGHT / 2);
	bp.setMinSize(bp.getPrefWidth(), bp.getPrefHeight());
	bp.setMaxSize(bp.getPrefWidth(), bp.getPrefHeight());

	BackgroundImage bi = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
	        BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

	bp.setStyle("-fx-background-image: url(\"" + this.getClass().getResource("bg.jpg").toExternalForm() + "\");" +
	        "-fx-shape:\""
	        + this.getPathForRoundedRect(this.TILE_WIDTH, this.TILE_HEIGHT, this.TILE_RADIUS, this.TILE_RADIUS, 0,
	                0)
	        + "\";"
	        +
	        "-fx-border-radius: 20;" +
	        "-fx-border-width:5;" +
	        "-fx-background-size:cover");
	bp.setBackground(new Background(bi));
	thumbnail.getChildren().add(bp);
	return thumbnail;

    }

    private String getPathForRoundedRect(final float width, final float height, final float tl, final float tr,
            final float br, final float bl)
    {
	return "M 0 " + tl
	        + " A " + tl + " " + tl + " 0 0 1 " + tl + " 0"
	        + " L " + (width - tr) + " 0"
	        + " A " + tr + " " + tr + " 0 0 1 " + width + " " + tr
	        + " L " + width + " " + (height - br)
	        + " A " + br + " " + br + " 0 0 1 " + (width - br) + " " + height
	        + " L " + bl + " " + height
	        + " A " + bl + " " + bl + " 0 0 1 0 " + (height - bl)
	        + " Z";
    }

}