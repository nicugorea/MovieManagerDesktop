package mmd.presentation.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.TilePane;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.FileDialogMode;
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.MovieDM;
import mmd.common.types.GenericData;
import mmd.common.types.WindowData;
import mmd.persistence.io.ImportExportData;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.controls.MovieTileVBox;
import mmd.presentation.managers.ViewManager;
import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

public class MainScreenController extends ControllerBase {
	@FXML
	private TreeView<String> categoryTreeView;

	@FXML
	private TilePane mainTilePane;

	@FXML
	private TextField categoryField;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		List<MovieDM> data = PropertyIO.getDMDefinitionsFromFile(MagicValues.MovieDMPath, MovieDM.class);
		ViewManager.setWindowData(this.getName(), new GenericData(data, data.getClass()));
		this.mainTilePane.setAlignment(Pos.CENTER);
		this.mainTilePane.setPrefColumns(100);

		this.refreshMovieList();

		TreeItem<String> root = new TreeItem<String>("All");

		this.categoryTreeView.setRoot(root);

	}

	@Override
	protected void initName() {
		this.stageName = SceneNameEnum.MainScreen;
	}

	private void addNewMovieDm() {
		try {
			WindowData result = ViewManager.getStageData(SceneNameEnum.AddMovie);
			if (result != null) {
				MovieDM dm = (MovieDM) result.getData().getDataValue();
				PropertyIO.addDMDefinitionToFile(dm, MagicValues.MovieDMPath, MagicValues.MoviesTagName);
				((List<MovieDM>) this.stageData.getDataValue()).add(dm);
				this.refreshMovieList();
			}
		} catch (Throwable e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
	}

	@FXML
	private void onLogoutAccountMenuItemAction(final ActionEvent e) {
		try {
			ViewManager.changeScene(ViewManager.getMainWindow().getStage(), SceneNameEnum.Login);
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	@FXML
	private void onNewMovieDataset(final ActionEvent e) {
		try {

		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	@FXML
	private void onAddCategoryClicked(final MouseEvent e) {
		try {
			if (!categoryField.getText().isEmpty()) {
				categoryTreeView.getRoot().getChildren().add(new TreeItem<String>(categoryField.getText()));
			}

		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	@FXML
	private void onImportMovieDataset(final ActionEvent e) {
		try {
			File file = ViewManager.fileDialog(FileTypeEnum.XML, FileDialogMode.Open);
			if (file != null) {

				ImportExportData.importMovies(file.getAbsolutePath());
				ViewManager.setWindowData(SceneNameEnum.MainScreen, new GenericData(
						PropertyIO.getDMDefinitionsFromFile(MagicValues.MovieDMPath, MovieDM.class), List.class));
				refreshMovieList();
			}
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	@FXML
	private void onExportMovieDataset(final ActionEvent e) {
		try {
			File file = ViewManager.fileDialog(FileTypeEnum.XML, FileDialogMode.Save);
			if (file != null)
				ImportExportData.exportMovies(file.getAbsolutePath());
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	@FXML
	private void onNewUserDataset(final ActionEvent e) {
		try {
			ImportExportData.newUsers();
			ViewManager.changeScene(ViewManager.getMainWindow().getStage(), SceneNameEnum.Login);
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	@FXML
	private void onImportUserDataset(final ActionEvent e) {
		try {
			File file = ViewManager.fileDialog(FileTypeEnum.XML, FileDialogMode.Open);
			if (file != null)
				ImportExportData.importUsers(file.getAbsolutePath());
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	@FXML
	private void onExportUserDataset(final ActionEvent e) {
		try {
			File file = ViewManager.fileDialog(FileTypeEnum.XML, FileDialogMode.Save);
			if (file != null)
				ImportExportData.exportUsers(file.getAbsolutePath());
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	@FXML
	private void onNewMovieMenuAction(final ActionEvent e) {
		ViewManager.showStage(SceneNameEnum.AddMovie, new EventHandler<WindowEvent>() {

			@Override
			public void handle(final WindowEvent event) {
				MainScreenController.this.addNewMovieDm();
				MainScreenController.this
						.setStageData(ViewManager.getStageData(MainScreenController.this.getName()).getData());
			}
		}, null);

	}

	private void refreshMovieList() {

		this.stageData = ViewManager.getStageData(this.getName()).getData();
		this.mainTilePane.getChildren().clear();
		for (int i = 0; i < ((List<MovieDM>) this.stageData.getDataValue()).size(); i++) {
			MovieTileVBox tile = new MovieTileVBox(((List<MovieDM>) this.stageData.getDataValue()).get(i));

			tile.setOnMouseClicked((e) -> {
				ViewManager.showStage(SceneNameEnum.MovieDetails, new EventHandler<WindowEvent>() {

					@Override
					public void handle(final WindowEvent event) {
						MainScreenController.this.refreshMovieList();
					}
				}, new GenericData(tile.getMovieDM(), MovieDM.class));
			});
			this.mainTilePane.getChildren().add(tile);

		}

	}

}
