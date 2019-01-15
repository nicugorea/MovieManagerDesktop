package mmd.presentation.controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TreeView;
import javafx.scene.layout.TilePane;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;
import mmd.common.bases.ControllerBase;
import mmd.common.enums.FileDialogMode;
import mmd.common.enums.FileTypeEnum;
import mmd.common.enums.SceneNameEnum;
import mmd.common.models.CategoryDM;
import mmd.common.models.MovieDM;
import mmd.common.models.UserDM;
import mmd.common.types.GenericData;
import mmd.common.types.WindowData;
import mmd.persistence.io.ImportExportData;
import mmd.persistence.io.PropertyIO;
import mmd.presentation.controls.MovieTileVBox;
import mmd.presentation.managers.ViewManager;
import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;
import mmd.util.sorting.SortingUtil;
import mmd.util.filtering.FilteringUtil;

/**
 * Controller for Main Window, there are handled the following:
 * <ul>
 * <li>Display movies information</li>
 * <li>Add a movie</li>
 * <li>Add a category</li>
 * <li>Delete a category</li>
 * <li>See detailed information about a movie</li>
 * <li>Generate reports</li>
 * <li>New, Import, Export datasets for users, movies, categories</li>
 * </ul>
 */
public class MainScreenController extends ControllerBase {
	@FXML
	private TreeView<String> categoryTreeView;

	@FXML
	private TilePane mainTilePane;

	@FXML
	private Button deleteCategoryButton;

	@FXML
	private TextField categoryField;

	@FXML
	private ImageView logoImageView;

	@Override
	public void initialize(final URL location, final ResourceBundle resources) {
		try {

			List<MovieDM> movies = PropertyIO.getDMDefinitionsFromFile(MagicValues.MovieDMPath, MovieDM.class);
			List<CategoryDM> categories = PropertyIO.getDMDefinitionsFromFile(MagicValues.CategoryDMPath,
					CategoryDM.class);

			TreeItem<String> root = new TreeItem<String>("All");
			root.setExpanded(true);
			this.categoryTreeView.setRoot(root);

			ViewManager.setWindowData(this.getName(), new GenericData(movies, movies.getClass()));
			this.mainTilePane.setAlignment(Pos.CENTER);
			this.mainTilePane.setPrefColumns(100);

			logoImageView.setImage(MagicValues.ApplicationLogo);
			this.refreshMovieList();
			this.refreshCategoryList(categories);

			setBindingsAndEvents();
		} catch (Exception e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
	}

	@Override
	protected void initName() {
		this.stageName = SceneNameEnum.MainScreen;
	}

	/**
	 * Add a new Movie to the current list from AddMovie Window
	 */
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

	/**
	 * Create all bindings and events
	 */
	private void setBindingsAndEvents() {

		deleteCategoryButton.disableProperty()
				.bind(categoryTreeView.getSelectionModel().selectedItemProperty().isNull());

		this.categoryTreeView.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {

					// lost focus when clicked on deleteCategory and selected item was null
					if (!deleteCategoryButton.isFocused())
						MainScreenController.this.categoryTreeView.getSelectionModel().clearSelection();
				}

			}
		});

		this.categoryTreeView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				try {

					if (event.getClickCount() >= 2) {
						if (categoryTreeView.getSelectionModel().getSelectedItem() != null) {
							List<MovieDM> movies;
							movies = PropertyIO.getDMDefinitionsFromFile(MagicValues.MovieDMPath, MovieDM.class);
							if (!categoryTreeView.getSelectionModel().getSelectedItem().getValue().equals("All")) {
								movies = FilteringUtil.filter(movies, MovieDM.class.getDeclaredField("Categories"),
										categoryTreeView.getSelectionModel().getSelectedItem().getValue());
							}
							ViewManager.setWindowData(SceneNameEnum.MainScreen,
									new GenericData(movies, movies.getClass()));
							MainScreenController.this.refreshMovieList();
						}
					}
				} catch (Exception e) {
					ErrorHandlerUtil.handleThrowable(e);
				}
			}
		});
	}

	/**
	 * Delete Category button clicked event
	 * 
	 * @param event {@link MouseEvent}
	 */
	@FXML
	private void onDeleteCategoryClicked(final MouseEvent e) {
		try {
			TreeItem<String> category = categoryTreeView.getSelectionModel().getSelectedItem();
			if (!category.getValue().equals("All")) {

				CategoryDM dm = new CategoryDM();
				dm.setCategoryName(category.getValue());
				PropertyIO.removeDMDefinitionFromFile(dm, CategoryDM.class.getDeclaredField("CategoryName"),
						MagicValues.CategoryDMPath);
				categoryTreeView.getRoot().getChildren().remove(category);
			}
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	/**
	 * Logout menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
	@FXML
	private void onLogoutAccountMenuItemAction(final ActionEvent e) {
		try {
			ViewManager.changeScene(ViewManager.getMainWindow().getStage(), SceneNameEnum.Login);
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	/**
	 * Add Category button clicked event
	 * 
	 * @param event {@link MouseEvent}
	 */
	@FXML
	private void onAddCategoryClicked(final MouseEvent e) {
		try {
			if (!categoryField.getText().isEmpty()) {
				if (!categoryTreeView.getRoot().getChildren().contains(categoryField.getText())) {
					categoryTreeView.getRoot().getChildren().add(new TreeItem<String>(categoryField.getText()));
					CategoryDM dm = new CategoryDM();
					dm.setCategoryName(categoryField.getText());
					PropertyIO.addDMDefinitionToFile(dm, MagicValues.CategoryDMPath, MagicValues.CategoriesTagName);
					categoryField.setText("");
				}
			}

		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	/**
	 * Generate Text Report menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
	@FXML
	private void onGenerateTextReport(final ActionEvent e) {
		try {

			List<CategoryDM> categories = PropertyIO.getDMDefinitionsFromFile(MagicValues.CategoryDMPath,
					CategoryDM.class);
			List<MovieDM> movies = PropertyIO.getDMDefinitionsFromFile(MagicValues.MovieDMPath, MovieDM.class);
			List<UserDM> users = PropertyIO.getDMDefinitionsFromFile(MagicValues.UserDMPath, UserDM.class);

			File file = new File(MagicValues.ReportTxtPath);
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);

			pw.println("\tUsers:");
			for (UserDM userDM : users) {
				pw.println("Username: " + userDM.getUsername());
				pw.println("Password: " + userDM.getPassword());
				pw.println();
			}

			pw.println("\tCategories:");
			for (CategoryDM categoryDM : categories) {
				pw.println("Category name: " + categoryDM.getCategoryName());
				pw.println();
			}

			pw.println("\tMovies:");
			for (MovieDM movieDM : movies) {
				pw.println("IMDbID: " + movieDM.getIMDbID());
				pw.println("Title: " + movieDM.getTitle());
				pw.println("Description: " + movieDM.getDescription());
				pw.println("Score: " + movieDM.getScore());
				pw.println("Categories: " + movieDM.getCategoriesString());
				pw.println();
			}

			pw.close();
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}

	}

	/**
	 * New Movie Dataset menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
	@FXML
	private void onNewMovieDataset(final ActionEvent e) {
		try {
			ImportExportData.newMovies();
			ViewManager.setWindowData(SceneNameEnum.MainScreen, new GenericData(
					PropertyIO.getDMDefinitionsFromFile(MagicValues.MovieDMPath, MovieDM.class), List.class));
			refreshMovieList();
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	/**
	 * Import Movie Dataset menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
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

	/**
	 * Export Movie Dataset menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
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

	/**
	 * New User Dataset menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
	@FXML
	private void onNewUserDataset(final ActionEvent e) {
		try {
			ImportExportData.newUsers();
			ViewManager.changeScene(ViewManager.getMainWindow().getStage(), SceneNameEnum.Login);
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	/**
	 * Import Movie User menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
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

	/**
	 * Export User Dataset menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
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

	/**
	 * New Category Dataset menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
	@FXML
	private void onNewCategoryDataset(final ActionEvent e) {
		try {
			ImportExportData.newCategories();
			List<CategoryDM> categories = PropertyIO.getDMDefinitionsFromFile(MagicValues.CategoryDMPath,
					CategoryDM.class);
			refreshCategoryList(categories);
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	/**
	 * Import Category Dataset menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
	@FXML
	private void onImportCategoryDataset(final ActionEvent e) {
		try {
			File file = ViewManager.fileDialog(FileTypeEnum.XML, FileDialogMode.Open);
			if (file != null) {

				ImportExportData.importCategories(file.getAbsolutePath());

				List<CategoryDM> categories = PropertyIO.getDMDefinitionsFromFile(MagicValues.CategoryDMPath,
						CategoryDM.class);
				refreshCategoryList(categories);
			}
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	/**
	 * Export Category Dataset menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
	@FXML
	private void onExportCategoryDataset(final ActionEvent e) {
		try {
			File file = ViewManager.fileDialog(FileTypeEnum.XML, FileDialogMode.Save);
			if (file != null)
				ImportExportData.exportCategories(file.getAbsolutePath());
		} catch (Exception ex) {
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}

	/**
	 * New Movie menu item clicked event
	 * 
	 * @param event {@link ActionEvent}
	 */
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

	/**
	 * Refresh Category TreeView
	 * 
	 * @param list List of CategoryDM
	 */
	private void refreshCategoryList(List<CategoryDM> list) {
		categoryTreeView.getRoot().getChildren().clear();
		for (CategoryDM categoryDM : list) {
			categoryTreeView.getRoot().getChildren().add(new TreeItem<String>(categoryDM.getCategoryName()));
		}

	}

	/**
	 * Refresh Movie List using {@link ViewManager} stored data of this window
	 */
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
