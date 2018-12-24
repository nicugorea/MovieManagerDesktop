package mmd.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;
import javafx.scene.layout.TilePane;
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
//	this.mainTilePane.setHgap(5);
//	this.mainTilePane.setVgap(5);
//
//	for (int i = 0; i < 1020; i++)
//	{
//	    MovieTile tile = new MovieTile();
//	    tile.setText(Integer.toString(i));
//
//	    this.mainTilePane.getChildren().add(tile);
//	}
//	TreeItem<String> root = new TreeItem<String>("All");
//	this.categoryTreeView.setRoot(root);
//	root.getChildren().add(new TreeItem("Anime"));
//	root.getChildren().add(new TreeItem("SF"));
//	root.getChildren().add(new TreeItem("Fighting"));
    }

    @FXML
    private void onLogoutMenuItemAction(final ActionEvent e)
    {
	SceneManager.changeScene(SceneName.LoginScreen);
    }

}
