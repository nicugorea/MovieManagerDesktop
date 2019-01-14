package mmd.presentation.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.WindowEvent;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.CategoryDM;
import mmd.common.models.MovieDM;
import mmd.common.types.GenericData;
import mmd.common.types.WindowData;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.managers.ViewManager;
import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

/**
 * Controller for Movie Details Window, there are handled the following:
 * <ul>
 * <li>Display movie information</li>
 * <li>Delete it</li>
 * </ul>
 */
public class MovieDetailsController extends ControllerBase
{
	
	@FXML
	private TextFlow categoriesText;
	
	@FXML
	private TextFlow descriptionText;
	
	private MovieDM dm = null;
	
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
		try
		{
			
			this.dm = (MovieDM) ViewManager.getStageData(SceneNameEnum.MovieDetails).getData()
			        .getDataValue();
			this.titleText.setText(this.dm.getTitle());
			this.descriptionText.getChildren().add(new Text(this.dm.getDescription()));
			this.scoreText.setText(Float.toString(this.dm.getScore()));
			this.thumbnailImageView.setImage(new Image(
			        IOUtil.getStringURLOfPath(IOUtil.getImagePath(this.dm.getImgPath()))));
			
			this.categoriesText.getChildren().add(new Text(this.dm.getCategoriesString()));
		}
		
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	@Override
	public void shutdown(final Event event)
	{
		super.shutdown(event);
		ViewManager.setWindowData(this.getName(), null);
		ViewManager.closeParentStage((Node) event.getSource());
	}
	
	@Override
	protected void initName()
	{
		this.stageName = SceneNameEnum.MovieDetails;
		
	}
	
	/**
	 * Delete button clicked event
	 * 
	 * @param event {@link MouseEvent}
	 */
	@FXML
	void deleteClicked(final MouseEvent event)
	{
		try
		{
			GenericData data = ViewManager.getStageData(SceneNameEnum.MainScreen).getData();
			if (!dm.getImgPath().equals(MagicValues.MovieDefaultThumbnail))
			{
				File file = new File(MagicValues.MovieThumbnailPath + dm.getImgPath());
				if (file.exists()) file.delete();
			}
			((List<MovieDM>) data.getDataValue()).remove(this.dm);
			PropertyIO.removeDMDefinitionFromFile(this.dm, MovieDM.class.getDeclaredField("IMDbID"),
			        MagicValues.MovieDMPath);
			
			this.shutdown(event);
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	/**
	 * Edit button clicked event
	 * 
	 * @param event {@link MouseEvent}
	 */
	@FXML
	void editClicked(final MouseEvent event)
	{
		try
		{
			WindowData wData = ViewManager.getWindow(this.getName()).getSecond();
			ViewManager.changeScene(wData.getStage(), SceneNameEnum.AddMovie);
			AddMovieController controller = ((FXMLLoader) wData.getStage().getUserData()).getController();
			controller.loadData(wData.getData());
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	/**
	 * OK button clicked event
	 * 
	 * @param event {@link MouseEvent}
	 */
	@FXML
	void okClicked(final MouseEvent event)
	{
		this.shutdown(event);
	}
	
}
