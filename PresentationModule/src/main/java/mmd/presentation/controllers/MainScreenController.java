package mmd.presentation.controllers;

import java.io.File;
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

public class MainScreenController extends ControllerBase
{
	@FXML
	private TreeView<String> categoryTreeView;
	
	@FXML
	private TilePane mainTilePane;
	
	@FXML
	private Button deleteCategoryButton;
	
	@FXML
	private TextField categoryField;
	
	@Override
	public void initialize(final URL location,
	        final ResourceBundle resources)
	{
		try {
			
		List<MovieDM> movies = PropertyIO.getDMDefinitionsFromFile(
		        MagicValues.MovieDMPath, MovieDM.class);
		List<CategoryDM> categories = PropertyIO
		        .getDMDefinitionsFromFile(MagicValues.CategoryDMPath,
		                CategoryDM.class);
		
		TreeItem<String> root = new TreeItem<String>("All");
		root.setExpanded(true);
		this.categoryTreeView.setRoot(root);
		
		
		ViewManager.setWindowData(this.getName(),
		        new GenericData(movies, movies.getClass()));
		this.mainTilePane.setAlignment(Pos.CENTER);
		this.mainTilePane.setPrefColumns(100);
		
		this.refreshMovieList();
		this.refreshCategoryList(categories);
		
		setBindingsAndEvents();
		}
		catch(Exception e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	@Override
	protected void initName()
	{
		this.stageName = SceneNameEnum.MainScreen;
	}
	
	private void addNewMovieDm()
	{
		try
		{
			WindowData result = ViewManager
			        .getStageData(SceneNameEnum.AddMovie);
			if (result != null)
			{
				MovieDM dm = (MovieDM) result.getData()
				        .getDataValue();
				PropertyIO.addDMDefinitionToFile(dm,
				        MagicValues.MovieDMPath,
				        MagicValues.MoviesTagName);
				((List<MovieDM>) this.stageData.getDataValue())
				        .add(dm);
				this.refreshMovieList();
			}
		}
		catch (Throwable e)
		{
			ErrorHandlerUtil.handleThrowable(e);
		}
	}
	
	private void setBindingsAndEvents() {
		
		deleteCategoryButton.disableProperty().bind(categoryTreeView
		        .getSelectionModel().selectedItemProperty().isNull());
		
		this.categoryTreeView.focusedProperty()
		        .addListener(new ChangeListener<Boolean>()
		        {
			        
			        @Override
			        public void changed(
			                ObservableValue<? extends Boolean> observable,
			                Boolean oldValue, Boolean newValue)
			        {
				        if (!newValue)
				        {
					        
					        // lost focus when clicked on deleteCategory and selected item was null
					        if (!deleteCategoryButton.isFocused())
					            MainScreenController.this.categoryTreeView
					                    .getSelectionModel()
					                    .clearSelection();
				        }
				        
			        }
		        });
		
		this.categoryTreeView.setOnMouseClicked(new EventHandler<MouseEvent>()
		{

			@Override
			public void handle(MouseEvent event)
			{
				try
				{
					
    				if(event.getClickCount()>=2) {
    					if(categoryTreeView.getSelectionModel().getSelectedItem()!=null) {
    						List<MovieDM> movies;
    						if(categoryTreeView.getSelectionModel().getSelectedItem().getValue().equals("All")){
    							movies = PropertyIO.getDMDefinitionsFromFile(
    							        MagicValues.MovieDMPath, MovieDM.class);
    						}else {
    						movies = (List<MovieDM>) ViewManager.getStageData(SceneNameEnum.MainScreen).getData().getDataValue();
    						movies=FilteringUtil.filter(movies, MovieDM.class.getDeclaredField("Categories"), 
    								categoryTreeView.getSelectionModel().getSelectedItem().getValue());
    						}
    						ViewManager.setWindowData(SceneNameEnum.MainScreen,
    								new GenericData(movies, movies.getClass()));	
    						MainScreenController.this.refreshMovieList();
    						}
    				}
				}
				catch (Exception e)
				{
					ErrorHandlerUtil.handleThrowable(e);
				}
			}
		});
	}
	
	@FXML
	private void onDeleteCategoryClicked(final MouseEvent e)
	{
		try
		{
			TreeItem<String> category = categoryTreeView
			        .getSelectionModel().getSelectedItem();
			CategoryDM dm = new CategoryDM();
			dm.setCategoryName(category.getValue());
			PropertyIO.removeDMDefinitionFromFile(dm,
			        CategoryDM.class.getDeclaredField("CategoryName"),
			        MagicValues.CategoryDMPath);
			categoryTreeView.getRoot().getChildren().remove(category);
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@FXML
	private void onLogoutAccountMenuItemAction(final ActionEvent e)
	{
		try
		{
			ViewManager.changeScene(
			        ViewManager.getMainWindow().getStage(),
			        SceneNameEnum.Login);
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@FXML
	private void onAddCategoryClicked(final MouseEvent e)
	{
		try
		{
			if (!categoryField.getText().isEmpty())
			{
				categoryTreeView.getRoot().getChildren()
				        .add(new TreeItem<String>(
				                categoryField.getText()));
				CategoryDM dm = new CategoryDM();
				dm.setCategoryName(categoryField.getText());
				PropertyIO.addDMDefinitionToFile(dm,
				        MagicValues.CategoryDMPath,
				        MagicValues.CategoriesTagName);
			}
			
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	
	@FXML
	private void onNewMovieDataset(final ActionEvent e)
	{
		try
		{
			ImportExportData.newMovies();
			ViewManager.setWindowData(SceneNameEnum.MainScreen,
			        new GenericData(
			                PropertyIO.getDMDefinitionsFromFile(
			                        MagicValues.MovieDMPath,
			                        MovieDM.class),
			                List.class));
			refreshMovieList();
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@FXML
	private void onImportMovieDataset(final ActionEvent e)
	{
		try
		{
			File file = ViewManager.fileDialog(FileTypeEnum.XML,
			        FileDialogMode.Open);
			if (file != null)
			{
				
				ImportExportData.importMovies(file.getAbsolutePath());
				ViewManager.setWindowData(SceneNameEnum.MainScreen,
				        new GenericData(
				                PropertyIO.getDMDefinitionsFromFile(
				                        MagicValues.MovieDMPath,
				                        MovieDM.class),
				                List.class));
				refreshMovieList();
			}
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@FXML
	private void onExportMovieDataset(final ActionEvent e)
	{
		try
		{
			File file = ViewManager.fileDialog(FileTypeEnum.XML,
			        FileDialogMode.Save);
			if (file != null)
			    ImportExportData.exportMovies(file.getAbsolutePath());
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	
	@FXML
	private void onNewUserDataset(final ActionEvent e)
	{
		try
		{
			ImportExportData.newUsers();
			ViewManager.changeScene(
			        ViewManager.getMainWindow().getStage(),
			        SceneNameEnum.Login);
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@FXML
	private void onImportUserDataset(final ActionEvent e)
	{
		try
		{
			File file = ViewManager.fileDialog(FileTypeEnum.XML,
			        FileDialogMode.Open);
			if (file != null)
			    ImportExportData.importUsers(file.getAbsolutePath());
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@FXML
	private void onExportUserDataset(final ActionEvent e)
	{
		try
		{
			File file = ViewManager.fileDialog(FileTypeEnum.XML,
			        FileDialogMode.Save);
			if (file != null)
			    ImportExportData.exportUsers(file.getAbsolutePath());
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	
	@FXML
	private void onNewCategoryDataset(final ActionEvent e)
	{
		try
		{
			ImportExportData.newCategories();
			List<CategoryDM> categories = PropertyIO
			        .getDMDefinitionsFromFile(
			                MagicValues.CategoryDMPath,
			                CategoryDM.class);
			refreshCategoryList(categories);
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@FXML
	private void onImportCategoryDataset(final ActionEvent e)
	{
		try
		{
			File file = ViewManager.fileDialog(FileTypeEnum.XML,
			        FileDialogMode.Open);
			if (file != null)
			{
				
				ImportExportData
				        .importCategories(file.getAbsolutePath());
				
				List<CategoryDM> categories = PropertyIO
				        .getDMDefinitionsFromFile(
				                MagicValues.CategoryDMPath,
				                CategoryDM.class);
				refreshCategoryList(categories);
			}
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	@FXML
	private void onExportCategoryDataset(final ActionEvent e)
	{
		try
		{
			File file = ViewManager.fileDialog(FileTypeEnum.XML,
			        FileDialogMode.Save);
			if (file != null)
			    ImportExportData.exportCategories(file.getAbsolutePath());
		}
		catch (Exception ex)
		{
			ErrorHandlerUtil.handleThrowable(ex);
		}
	}
	
	
	@FXML
	private void onNewMovieMenuAction(final ActionEvent e)
	{
		ViewManager.showStage(SceneNameEnum.AddMovie,
		        new EventHandler<WindowEvent>()
		        {
			        
			        @Override
			        public void handle(final WindowEvent event)
			        {
				        MainScreenController.this.addNewMovieDm();
				        MainScreenController.this.setStageData(
				                ViewManager.getStageData(
				                        MainScreenController.this
				                                .getName())
				                        .getData());
			        }
		        }, null);
		
	}
	
	private void refreshCategoryList(List<CategoryDM> list)
	{
		categoryTreeView.getRoot().getChildren().clear();
		for (CategoryDM categoryDM : list)
		{
			categoryTreeView.getRoot().getChildren()
			        .add(new TreeItem<String>(
			                categoryDM.getCategoryName()));
		}
		
	}
	
	private void refreshMovieList()
	{
		
		this.stageData = ViewManager.getStageData(this.getName())
		        .getData();
		this.mainTilePane.getChildren().clear();
		for (int i = 0; i < ((List<MovieDM>) this.stageData
		        .getDataValue()).size(); i++)
		{
			MovieTileVBox tile = new MovieTileVBox(
			        ((List<MovieDM>) this.stageData.getDataValue())
			                .get(i));
			
			tile.setOnMouseClicked((e) ->
			{
				ViewManager.showStage(SceneNameEnum.MovieDetails,
				        new EventHandler<WindowEvent>()
				        {
					        
					        @Override
					        public void handle(
					                final WindowEvent event)
					        {
						        MainScreenController.this
						                .refreshMovieList();
					        }
				        }, new GenericData(tile.getMovieDM(),
				                MovieDM.class));
			});
			this.mainTilePane.getChildren().add(tile);
			
		}
		
	}
	
}
