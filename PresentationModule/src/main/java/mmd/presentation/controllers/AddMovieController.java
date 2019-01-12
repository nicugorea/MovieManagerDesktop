package mmd.presentation.controllers;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.FileDialogMode;
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.CategoryDM;
import mmd.common.models.MovieDM;
import mmd.common.types.GenericData;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.managers.ViewManager;
import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

public class AddMovieController extends ControllerBase
{
	
	@FXML
	private Button addCategoryButton;
	
	@FXML
	private Button removeCategoryButton;
	
	@FXML
	private ListView<String> allCategoryList;
	
	@FXML
	private ListView<String> selectedCategoryList;
	
	@FXML
	private TextArea descriptionField;
	
	@FXML
	private TextField IMDbIDField;
	
	@FXML
	private Button openButton;
	
	@FXML
	private Slider scoreSlider;
	
	private final Spinner<Double> scoreSpinner = new Spinner<Double>();
	
	@FXML
	private VBox scoreVBox;
	
	@FXML
	private HBox thumbnailHBox;
	
	@FXML
	private ImageView thumbnailImageView;
	
	@FXML
	private TextFlow thumbnailTextFlow;
	
	@FXML
	private TextField titleField;
	
	private File tmpThumbnail = null;
	
	@Override
	public void initialize(final URL location,
	        final ResourceBundle resources)
	{
		this.scoreSpinner.setValueFactory(
		        new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0,
		                10.0, 0.0));
		this.scoreVBox.getChildren().add(this.scoreSpinner);
		this.allCategoryList
		        .setCellFactory(TextFieldListCell.forListView());
		this.selectedCategoryList
		        .setCellFactory(TextFieldListCell.forListView());
		List<CategoryDM> categories = PropertyIO.getDMDefinitionsFromFile(MagicValues.CategoryDMPath, CategoryDM.class);
		for (CategoryDM categoryDM : categories)
		{
			allCategoryList.getItems().add(categoryDM.getCategoryName());
		}
		
// this.thumbnailImageView.setImage(new
		// Image(MagicValues.MovieThumbnailPath+"/"+MagicValues.MovieDefaultThumbnail));
		setBindingsAndEvents();
	}
	
	private void setBindingsAndEvents()
	{
		addCategoryButton.disableProperty().bind(allCategoryList
		        .getSelectionModel().selectedItemProperty().isNull());
		removeCategoryButton.disableProperty().bind(selectedCategoryList
		        .getSelectionModel().selectedItemProperty().isNull());
		
	}
	
	@Override
	protected void initName()
	{
		this.stageName = SceneNameEnum.AddMovie;
		
	}
	
	private MovieDM getDMFromContext()
	{
		MovieDM dm = new MovieDM().newInstance(new MovieDM());
		
		if (!this.titleField.getText().isEmpty())
		{
			dm.setTitle(this.titleField.getText());
		}
		
		if (!this.descriptionField.getText().isEmpty())
		{
			dm.setDescription(this.descriptionField.getText());
		}
		
		if (!this.IMDbIDField.getText().isEmpty())
		{
			dm.setIMDbID(this.IMDbIDField.getText());
		}
		
		dm.setScore((float) scoreSlider.getValue());
		
		if (this.selectedCategoryList.getItems().size() > 0)
		{
			dm.setCategories(
			        new LinkedList<String>(selectedCategoryList.getItems()));
		}
		
		if (this.tmpThumbnail != null)
		{
			String tName = this.IMDbIDField.getText()
			        + IOUtil.getFileExtension(tmpThumbnail);
			IOUtil.copyFile(tmpThumbnail.getAbsolutePath(),
			        MagicValues.MovieThumbnailPath + tName);
			dm.setImgPath(tName);
		}
		
		return dm;
	}
	
	@FXML
	void addClicked(final MouseEvent event)
	{
		try
		{
			this.selectedCategoryList.getItems()
			        .add(allCategoryList.getSelectionModel().getSelectedItem());

			this.allCategoryList.getItems()
			        .remove(allCategoryList.getSelectionModel().getSelectedItem());
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	@FXML
	void removeClicked(final MouseEvent event)
	{
		try
		{
			this.allCategoryList.getItems()
			        .add(selectedCategoryList.getSelectionModel().getSelectedItem());

			this.selectedCategoryList.getItems()
			        .remove(selectedCategoryList.getSelectionModel().getSelectedItem());
		}
		catch (Exception e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	@FXML
	void cancelClicked(final MouseEvent event)
	{
		
		ViewManager.closeParentStage((Node) event.getSource());
	}
	
	@FXML
	void openClicked(final MouseEvent event)
	{
		File file = ViewManager.fileDialog(FileTypeEnum.Image,
		        FileDialogMode.Open);
		this.thumbnailTextFlow.getChildren().clear();
		this.thumbnailTextFlow.getChildren()
		        .add(new Text(file.getName()));
		this.thumbnailImageView
		        .setImage(new Image(file.toURI().toString()));
		this.tmpThumbnail = file;
		
	}
	
	@FXML
	void saveClicked(final MouseEvent event)
	{
		
		MovieDM dm = this.getDMFromContext();
		ViewManager.setWindowData(this.getName(),
		        new GenericData(dm, dm.getClass()));
		ViewManager.closeParentStage((Node) event.getSource());
	}
}
