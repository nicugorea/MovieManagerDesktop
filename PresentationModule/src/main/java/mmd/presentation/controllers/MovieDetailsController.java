package mmd.presentation.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.MovieDM;
import mmd.common.types.GenericData;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.stages.StageManager;
import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

public class MovieDetailsController extends ControllerBase
{

    @FXML
    private TextFlow categoriesText;

    @FXML
    private TextFlow descriptionText;

    private MovieDM dm= null;

    @FXML
    private Text scoreText;

    @FXML
    private HBox thumbnailHBox;

    @FXML
    private VBox thumbnailImageview;

    @FXML
    private ImageView thumbnailImageView;

    @FXML
    private Text titleText;

    @Override
    public void initialize(final URL location, final ResourceBundle resources)
    {
	try {

	    this.dm=(MovieDM) StageManager.getStageData(SceneNameEnum.MovieDetails).getDataValue();
	    this.titleText.setText(this.dm.getTitle());
	    this.descriptionText.getChildren().add(new Text(this.dm.getDescription()));
	    this.scoreText.setText(Float.toString(this.dm.getScore()));
	    this.thumbnailImageView.setImage(new Image(IOUtil.getStringURLOfPath(IOUtil.getImagePath(this.dm.getImgPath()))));

	    this.categoriesText.getChildren().add(new Text(
		    this.dm.getCategoriesString()));
	}

	catch (Exception e) {
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

    @Override
    public void shutdown(final Event event)
    {
	super.shutdown(event);
	StageManager.setStageData(this.getName(), null);
	StageManager.closeParentStage((Node) event.getSource());
    }

    @Override
    protected void initName()
    {
	this.stageName = SceneNameEnum.MovieDetails;

    }

    @FXML
    void deleteClicked(final MouseEvent event)
    {
	try
	{

	    GenericData data = StageManager.getStageData(SceneNameEnum.MainScreen);
	    ((List<MovieDM>)data.getDataValue()).remove(this.dm);
	    PropertyIO.removeDMDefinitionToFile(this.dm, MagicValues.MovieDMPath);
	    this.shutdown(event);
	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }


    @FXML
    void okClicked(final MouseEvent event)
    {
	this.shutdown(event);
    }

}
