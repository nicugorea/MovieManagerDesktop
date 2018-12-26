package mmd.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import mmd.presentation.scenes.MovieTileVBox;
import mmd.presentation.scenes.SceneManager;
import mmd.presentation.scenes.SceneName;

public class MainScreenController implements Initializable
{
    @FXML
    private TreeView<String> categoryTreeView;

    @FXML
    private TilePane mainTilePane;

    @Override
    public void initialize(final URL location, final ResourceBundle resources)
    {

	this.mainTilePane.setPrefColumns(100);

	for (int i = 0; i < 23; i++)
	{
	    VBox tile = new MovieTileVBox();
	    this.mainTilePane.getChildren().add(tile);
	}

	TreeItem<String> root = new TreeItem<String>("All");

	this.categoryTreeView.setRoot(root);

	root.getChildren().add(new TreeItem("Anime"));
	root.getChildren().add(new TreeItem("SF"));
	root.getChildren().add(new TreeItem("Fighting"));

    }

    @FXML
    private void onLogoutMenuItemAction(final ActionEvent e)
    {
	SceneManager.changeScene(SceneName.LoginScreen);
    }

}
