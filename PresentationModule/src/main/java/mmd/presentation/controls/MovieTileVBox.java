package mmd.presentation.controls;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import mmd.common.models.MovieDM;
import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

/**
 * A tile control extending a {@link VBox} used for displaying Movie Data
 */
public class MovieTileVBox extends VBox {
	/**
	 * Constructor with no arguments
	 */
	public MovieTileVBox() {
		this(new MovieDM());
		this.dm.setTitle("Movie Title");
		this.dm.addCategory("Category");
		this.dm.setDescription("Movie description here.");
		this.dm.setImgPath(MagicValues.MovieDefaultThumbnail);
	}

	/**
	 * Constructor based on a Movie Data Model
	 * 
	 * @param dm {@link MovieDM} to extract information
	 */
	public MovieTileVBox(final MovieDM dm) {

		super();
		try {
			super.getStyleClass().add("movieTile");
			this.dm = dm;
			this.createMainVBox();
			super.getChildren().add(this.createPrimaryRegion());
			super.getChildren().add(this.createActionBar());
		} catch (Exception e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
	}

	private final int CONTENT_MARGIN = 6;
	private MovieDM dm;

	private final int SHADOW_OFFSET = 3;

	private final int TILE_HEIGHT = 300;

	private final int TILE_RADIUS = 8;

	private final int TILE_WIDTH = 240;

	private final int DESCRIPTION_LENGTH = 50;

	/**
	 * Get Movie Data Model of this Tile
	 * 
	 * @return {@link MovieDM}
	 */
	public MovieDM getMovieDM() {
		return this.dm;
	}

	/**
	 * Create action bar
	 * 
	 * @return {@link HBox}
	 */
	private HBox createActionBar() {
		HBox result = new HBox();
		return result;
	}

	/**
	 * Create shadow component
	 * 
	 * @return {@link DropShadow}
	 */
	private DropShadow createDropShadow() {
		DropShadow ds = new DropShadow();
		ds.setOffsetX(this.SHADOW_OFFSET);
		ds.setOffsetY(this.SHADOW_OFFSET);
		ds.setColor(Color.GRAY);
		return ds;
	}

	/**
	 * Create main rectangle to stack all elements
	 */
	private void createMainVBox() {
		super.setMaxSize(super.getPrefHeight(), super.getPrefWidth());
		super.setMinSize(super.getPrefHeight(), super.getPrefWidth());
		super.setPrefSize(this.TILE_WIDTH, this.TILE_HEIGHT);
		super.setAlignment(Pos.TOP_CENTER);
		super.setBackground(Background.EMPTY);
		String styles = String.format("-fx-background-color: #fff;" + "-fx-background-radius: %d ;", this.TILE_RADIUS);
		super.setStyle(styles);
		super.setEffect(this.createDropShadow());
	}

	/**
	 * Create primary bottom part component containing Description
	 * 
	 * @return {@link VBox}
	 */
	private VBox createPrimaryBottomRegion() {
		VBox element = new VBox();
		element.setAlignment(Pos.TOP_LEFT);
		Text description = new Text();
		description.getStyleClass().add("movieDescription");
		if (dm.getDescription().length() >= DESCRIPTION_LENGTH) {
			description.setText(dm.getDescription().substring(0, DESCRIPTION_LENGTH) + "...");
		} else {
			description.setText(this.dm.getDescription());
		}

		description.setWrappingWidth(this.TILE_WIDTH - 2 * this.CONTENT_MARGIN);
		element.getChildren().add(description);

		return element;

	}

	/**
	 * Create primary component containing all information
	 * 
	 * @return {@link VBox}
	 */
	private VBox createPrimaryRegion() {
		VBox element = new VBox();
		element.setAlignment(Pos.TOP_LEFT);
		element.getChildren().add(this.createThumbnail());
		element.getChildren().add(this.createPrimaryTopRegion());
		element.getChildren().add(this.createPrimaryBottomRegion());

		// from second element
		for (int i = 1; i < element.getChildren().size(); i++) {
			VBox.setMargin(element.getChildren().get(i), new Insets(this.CONTENT_MARGIN));
		}

		return element;

	}

	/**
	 * Create primary bottom part component containing Title and Categories
	 * 
	 * @return {@link VBox}
	 */
	private VBox createPrimaryTopRegion() {
		VBox element = new VBox();
		element.setAlignment(Pos.TOP_CENTER);

		Text title = new Text(this.dm.getTitle());
		title.getStyleClass().add("movieTitle");
		title.setWrappingWidth(this.TILE_WIDTH - 2 * this.CONTENT_MARGIN);

		Text categories = new Text();
		categories.getStyleClass().add("movieCategories");
		categories.setText(this.dm.getCategoriesString());
		categories.setWrappingWidth(this.TILE_WIDTH - 2 * this.CONTENT_MARGIN);

		Text score = new Text();
		score.getStyleClass().add("movieScore");
		score.setText("Score: " + Float.toString(this.dm.getScore()));
		score.setWrappingWidth(this.TILE_WIDTH - 2 * this.CONTENT_MARGIN);

		element.getChildren().add(title);
		element.getChildren().add(score);
		element.getChildren().add(categories);
		return element;

	}

	/**
	 * Create movie image component
	 * 
	 * @return {@link VBox}
	 */
	private VBox createThumbnail() {

		VBox thumbnail = new VBox();

		try {

			thumbnail.setPrefWidth(this.TILE_WIDTH);
			thumbnail.setMinWidth(thumbnail.getPrefWidth());
			thumbnail.setMaxWidth(thumbnail.getPrefWidth());
			thumbnail.setAlignment(Pos.CENTER);
			String imgPath = this.dm.getImgPath();
			if (!IOUtil.existFile(IOUtil.getImagePath(imgPath))) {
				imgPath = MagicValues.MovieDefaultThumbnail;
			}

			Image image = new Image(IOUtil.getStringURLOfPath(IOUtil.getImagePath(imgPath)));
			ImageView imageView = new ImageView(image);
			imageView.setPreserveRatio(true);
			imageView.setFitWidth(this.TILE_WIDTH);
			imageView.setFitHeight(this.TILE_HEIGHT / 2);

			String styles = "-fx-shape:\"" + this.getPathForRoundedRect(this.TILE_WIDTH, this.TILE_HEIGHT,
					this.TILE_RADIUS, this.TILE_RADIUS, 0, 0) + "\";";
			imageView.setStyle(styles);
			thumbnail.getChildren().add(imageView);

			thumbnail.setStyle(styles);

		} catch (Throwable e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
		return thumbnail;

	}

	/**
	 * Create rounded rectangle path
	 * 
	 * @param width  Rectangle width
	 * @param height Rectangle height
	 * @param tl     Rectangle top left corner
	 * @param tr     Rectangle top right corner
	 * @param br     Rectangle bottom right corner
	 * @param bl     Rectangle bottom left corner
	 * @return String defining a Geometrical Path
	 */
	private String getPathForRoundedRect(final float width, final float height, final float tl, final float tr,
			final float br, final float bl) {
		return "M 0 " + tl + " A " + tl + " " + tl + " 0 0 1 " + tl + " 0" + " L " + (width - tr) + " 0" + " A " + tr
				+ " " + tr + " 0 0 1 " + width + " " + tr + " L " + width + " " + (height - br) + " A " + br + " " + br
				+ " 0 0 1 " + (width - br) + " " + height + " L " + bl + " " + height + " A " + bl + " " + bl
				+ " 0 0 1 0 " + (height - bl) + " Z";
	}

}
