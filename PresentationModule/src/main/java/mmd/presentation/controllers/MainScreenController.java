package mmd.presentation.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.TilePane;
import mmd.common.enums.SceneNameEnum;
import mmd.common.enums.StageNameEnum;
import mmd.common.models.MovieDM;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.scenes.MovieTileVBox;
import mmd.presentation.scenes.SceneManager;
import mmd.presentation.stages.StageManager;

public class MainScreenController implements Initializable
{
    @FXML
    private TreeView<String> categoryTreeView;

    @FXML
    private TilePane mainTilePane;
    private List<MovieDM> movies=null;


    @Override
    public void initialize(final URL location, final ResourceBundle resources)
    {
	this.movies = PropertyIO.getDMDefinitionFromFile(System.getProperty("user.dir") + "/movies.xml", MovieDM.class);
	this.mainTilePane.setAlignment(Pos.CENTER);
	this.mainTilePane.setPrefColumns(100);

	this.refreshMovieList();

	TreeItem<String> root = new TreeItem<String>("All");

	this.categoryTreeView.setRoot(root);

	root.getChildren().add(new TreeItem("Anime"));
	root.getChildren().add(new TreeItem("SF"));
	root.getChildren().add(new TreeItem("Fighting"));

    }

    private void addNewMovieDm() {
	//	PropertyIO.saveDMDefinitionToFile(dm, MagicValues.MovieDMPath, MagicValues.MoviesTagName);
	this.refreshMovieList();
    }

    @FXML
    private void onLogoutAccountMenuItemAction(final ActionEvent e)
    {
	SceneManager.changeScene(SceneNameEnum.LoginScreen);
    }

    @FXML
    private void onNewMovieMenuAction(final ActionEvent e)
    {
	StageManager.showStage(StageNameEnum.AddMovie, (WindowEvent)->{
	    this.addNewMovieDm();
	});


    }

    private void refreshMovieList() {

	this.mainTilePane.getChildren().clear();
	for (int i = 0; i < this.movies.size(); i++)
	{
	    this.mainTilePane.getChildren().add(new MovieTileVBox(this.movies.get(i)));
	}

    }

}
