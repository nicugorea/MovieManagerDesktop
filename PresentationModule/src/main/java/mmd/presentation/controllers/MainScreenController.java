package mmd.presentation.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.TilePane;
import javafx.stage.WindowEvent;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.SceneNameEnum;
import mmd.common.enums.StageNameEnum;
import mmd.common.models.MovieDM;
import mmd.common.types.Tuple;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.scenes.MovieTileVBox;
import mmd.presentation.scenes.SceneManager;
import mmd.presentation.stages.StageManager;
import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class MainScreenController extends ControllerBase {
	@FXML
	private TreeView<String> categoryTreeView;

	@FXML
	private TilePane mainTilePane;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		List<MovieDM> data = PropertyIO.getDMDefinitionFromFile(MagicValues.MovieDMPath, MovieDM.class);
		StageManager.setStageData(getName(),new Tuple<Object, Class<?>>(data, data.getClass()));
		this.mainTilePane.setAlignment(Pos.CENTER);
		this.mainTilePane.setPrefColumns(100);

		this.refreshMovieList();

		TreeItem<String> root = new TreeItem<String>("All");

		this.categoryTreeView.setRoot(root);
//
//		root.getChildren().add(new TreeItem("Anime"));
//		root.getChildren().add(new TreeItem("SF"));
//		root.getChildren().add(new TreeItem("Fighting"));

	}

	@Override
	protected void initName() {
		this.stageName = StageNameEnum.MainWindow;
	}

	private void addNewMovieDm() {
		try {
			Tuple<Object, Class<?>> result = StageManager.getStageData(StageNameEnum.AddMovie);
			if (result != null) {

				MovieDM dm = (MovieDM) result.getFirst();
				PropertyIO.saveDMDefinitionToFile(dm, MagicValues.MovieDMPath, MagicValues.MoviesTagName);
				((List<MovieDM>) this.stageData.getFirst()).add(dm);
				this.refreshMovieList();
			}
		} catch (Throwable e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
	}

	@FXML
	private void onLogoutAccountMenuItemAction(final ActionEvent e) {
		SceneManager.changeScene(SceneNameEnum.LoginScreen);
	}

	@FXML
	private void onNewMovieMenuAction(final ActionEvent e) {
		StageManager.showStage(StageNameEnum.AddMovie, new EventHandler<WindowEvent>() {

			@Override
			public void handle(final WindowEvent event) {
				MainScreenController.this.addNewMovieDm();
				MainScreenController.this.setStageData(StageManager.getStageData(getName()));
			}
		}, null);

	}

	private void refreshMovieList() {

		this.stageData=StageManager.getStageData(getName());
		this.mainTilePane.getChildren().clear();
		for (int i = 0; i < ((List<MovieDM>) this.stageData.getFirst()).size(); i++) {
			this.mainTilePane.getChildren().add(new MovieTileVBox(((List<MovieDM>) this.stageData.getFirst()).get(i)));
		}

	}

}
