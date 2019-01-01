package mmd.presentation.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import mmd.common.enums.FileTypeEnum;
import mmd.presentation.scenes.SceneManager;

public class AddMovieController implements Initializable {

    @FXML
    private Button addCategoryButton;

    @FXML
    private TextField categoryField;

    @FXML
    private ListView<?> categoryList;

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

    private File tmpThumbnail =null;

    @Override
    public void initialize(final URL location, final ResourceBundle resources)
    {
	this.scoreSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 10.0, 0.0));
	this.scoreVBox.getChildren().add(this.scoreSpinner);
	this.titleField.setText("dasdasd");


    }

    @FXML
    void openClicked(final MouseEvent event) {
	File file = SceneManager.openFile(FileTypeEnum.Image);
	this.thumbnailTextFlow.getChildren().clear();
	this.thumbnailTextFlow.getChildren().add(new Text(file.getName()));
	this.thumbnailImageView.setImage(new Image(file.toURI().toString()));
	this.tmpThumbnail=file;
    }

}
