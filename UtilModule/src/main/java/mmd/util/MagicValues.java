package mmd.util;

import java.net.URL;

import mmd.util.io.IOUtil;

/**
 * 
 * Constants used in all project
 */
public class MagicValues
{
	public final static String AppDataPath = System.getenv("APPDATA") + "/.mmd/";
	
	public final static String MoviesDataPath = AppDataPath + "movies/";
	public final static String MovieDMName = "Movie";
	public final static String MoviesTagName = "Movies";
	public final static String MovieDMPath = MoviesDataPath + "data/" + MovieDMName + ".xml";
	public final static String MovieDMBackupPath = MoviesDataPath + "data/backup/";
	
	public final static String MovieThumbnailPath = MoviesDataPath + "thumbnails/";
	public final static String MovieDefaultThumbnail = "movieThumbnail.png";
	
	public final static String UsersDataPath = AppDataPath + "users/";
	public final static String UserDMName = "User";
	public final static String UsersTagName = "Users";
	public final static String UserDMPath = UsersDataPath + "data/" + UserDMName + ".xml";
	public final static String UserDMBackupPath = UsersDataPath + "data/backup/";
	
	public final static String CategoriesDataPath = AppDataPath + "categories/";
	public final static String CategoryDMName = "Category";
	public final static String CategoriesTagName = "Categories";
	public final static String CategoryDMPath = CategoriesDataPath + "data/" + CategoryDMName
	        + ".xml";
	public final static String CategoryDMBackupPath = CategoriesDataPath + "data/backup/";
	
	public final static String ReportsPath = AppDataPath + "reports/";
	public final static String ReportTxtPath = ReportsPath + "report_"
	        + UtilModule.getDateNowString() + ".txt";

	public final static URL TileStyleFile = IOUtil.getResourcePath("mmd/presentation/css/MovieTile.css");
	
}
