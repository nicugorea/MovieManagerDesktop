package mmd.persistence;

import java.io.File;
import java.net.URL;

import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

/**
 * 
 * <h1>Persistence module main class</h1>
 * <p>
 * Here are create all elements that are needed for this module
 * </p>
 */
public class PersistenceModule {

	/**
	 * Method to initialize this module
	 */
	public static void init() {
		createPaths();
	}

	/**
	 * Create a folder if it's not created already
	 * 
	 * @param path
	 */
	private static void createPath(String path) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	/**
	 * Create if there is no file the default movie image
	 */
	private static void createMovieDMThumbnailFile() {
		File file = new File(MagicValues.MovieThumbnailPath + "/" + MagicValues.MovieDefaultThumbnail);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			URL defaultThumbnail = PersistenceModule.class.getClassLoader()
					.getResource("mmd/presentation/img/movies/" + MagicValues.MovieDefaultThumbnail);
			IOUtil.copyFile(new File(defaultThumbnail.getFile()).getAbsolutePath(),
					MagicValues.MovieThumbnailPath + "/" + MagicValues.MovieDefaultThumbnail);
		}
	}

	/**
	 * Create all necessary files and folders
	 */
	private static void createPaths() {
		try {
			createDMFile(MagicValues.MovieDMPath, MagicValues.MoviesTagName);
			createDMFile(MagicValues.CategoryDMPath, MagicValues.CategoriesTagName);
			createDMFile(MagicValues.UserDMPath, MagicValues.UsersTagName);
			createMovieDMThumbnailFile();
			createPath(MagicValues.MovieDMBackupPath);
			createPath(MagicValues.UserDMBackupPath);
			createPath(MagicValues.CategoryDMBackupPath);
			createPath(MagicValues.ReportsPath);

		} catch (Exception e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
	}

	/**
	 * Create if there is no file the XML File that contains a Data model
	 * 
	 * @param path Path to file
	 * @param tag  XML Root tag
	 */
	private static void createDMFile(String path, String tag) {
		File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(tag), file.getAbsolutePath());
		}
	}

}
