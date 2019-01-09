package mmd.presentation.controllers;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
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
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.MovieDM;
import mmd.common.types.GenericData;
import mmd.presentation.stages.StageManager;
import mmd.util.errorhandling.ErrorHandlerUtil;
public class AddMovieController extends ControllerBase
{

    @FXML
    private Button addCategoryButton;

    @FXML
    private TextField categoryField;

    @FXML
    private ListView<String> categoryList;

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
    public void initialize(final URL location, final ResourceBundle resources)
    {
	this.scoreSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10.0, 0.0));
	this.scoreVBox.getChildren().add(this.scoreSpinner);
	this.categoryList.setCellFactory(TextFieldListCell.forListView());
	//	this.thumbnailImageView.setImage(new Image(MagicValues.MovieThumbnailPath+"/"+MagicValues.MovieDefaultThumbnail));
    }

    @Override
    protected void initName()
    {
	this.stageName=SceneNameEnum.AddMovie;

    }

    private MovieDM getDMFromContext() {
	MovieDM dm = new MovieDM().newInstance(new MovieDM());

	if(!this.titleField.getText().isEmpty())
	{
	    dm.setTitle(this.titleField.getText());
	}

	if(!this.descriptionField.getText().isEmpty())
	{
	    dm.setDescription(this.descriptionField.getText());
	}
	if(!this.IMDbIDField.getText().isEmpty())
	{
	    dm.setIMDbID(this.IMDbIDField.getText());
	}
	dm.setScore(10.0f);
	if(this.categoryList.getChildrenUnmodifiable().size()>0)
	{
	    dm.setCategories(new LinkedList<String>());
	}
	if(this.tmpThumbnail!=null)
	{
	    dm.setImgPath(this.tmpThumbnail.getName());
	}
	return dm;
    }

    @FXML
    void addClicked(final MouseEvent event)
    {
	try
	{

	    this.categoryList.getItems().add(this.categoryField.getText());
	}
	catch (Exception e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

    @FXML
    void cancelClicked(final MouseEvent event)
    {

	StageManager.closeParentStage((Node) event.getSource());
    }

    @FXML
    void openClicked(final MouseEvent event)
    {
	File file = StageManager.openFile(FileTypeEnum.Image);
	this.thumbnailTextFlow.getChildren().clear();
	this.thumbnailTextFlow.getChildren().add(new Text(file.getName()));
	this.thumbnailImageView.setImage(new Image(file.toURI().toString()));
	this.tmpThumbnail = file;

    }
    @FXML
    void saveClicked(final MouseEvent event)
    {

	MovieDM dm = this.getDMFromContext();
	StageManager.setStageData(this.getName(), new GenericData(dm, dm.getClass()));
	StageManager.closeParentStage((Node) event.getSource());
    }
}
