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
import mmd.common.models.MovieDM;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.scenes.MovieTileVBox;
import mmd.presentation.scenes.SceneManager;

public class MainScreenController implements Initializable
{
    @FXML
    private TreeView<String> categoryTreeView;

    @FXML
    private TilePane mainTilePane;

    @Override
    public void initialize(final URL location, final ResourceBundle resources)
    {

	this.mainTilePane.setAlignment(Pos.CENTER);
	this.mainTilePane.setPrefColumns(100);

	this.refreshMovieList();

	TreeItem<String> root = new TreeItem<String>("All");

	this.categoryTreeView.setRoot(root);

	root.getChildren().add(new TreeItem("Anime"));
	root.getChildren().add(new TreeItem("SF"));
	root.getChildren().add(new TreeItem("Fighting"));

    }

    @FXML
    private void onLogoutMenuItemAction(final ActionEvent e)
    {
	SceneManager.changeScene(SceneNameEnum.LoginScreen);
    }

    private void refreshMovieList() {
	/*
	 * TODO: Tile clone and change only parameters
	 * like @title, @description, @image, @categories , etc
	 * 
	 */
	List<MovieDM> dms = PropertyIO.getDMDefinitionFromFile(System.getProperty("user.dir") + "/movies.xml", MovieDM.class);
	for (int i = 0; i < dms.size(); i++)
	{
	    this.mainTilePane.getChildren().add(new MovieTileVBox(dms.get(i)));
	}

    }

}
