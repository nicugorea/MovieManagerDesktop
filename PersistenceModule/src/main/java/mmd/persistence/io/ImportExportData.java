package mmd.persistence.io;

import java.text.SimpleDateFormat;
import java.util.Date;

import mmd.util.MagicValues;
import mmd.util.UtilModule;
import mmd.util.io.IOUtil;

/**
 * Util to import, export, backup and create a new Data File 
 */
public class ImportExportData {

	/**
	 * Export a XML File containing a list of Movie data model
	 * @param path Path of exported file
	 */
	public static void exportMovies(String path) {
		IOUtil.copyFile(MagicValues.MovieDMPath, path);
	}

	/**
	 * Export a XML File containing a list of User data model
	 * @param path Path of exported file
	 */
	public static void exportUsers(String path) {
		IOUtil.copyFile(MagicValues.UserDMPath, path);
	}

	/**
	 * Export a XML File containing a list of Category data model
	 * @param path Path of exported file
	 */
	public static void exportCategories(String path) {
		IOUtil.copyFile(MagicValues.CategoryDMPath, path);
	}
	
	/**
	 * Import a XML File containing a list of Movie data model and create a backup file of current XML File in backup folder
	 * @param path Path from where to import the XML File
	 */
	public static void importMovies(String path) {
		makeBackup(MagicValues.MovieDMPath,MagicValues.MovieDMBackupPath + MagicValues.MovieDMName);
		IOUtil.copyFile(path, MagicValues.MovieDMPath);

	}
	
	/**
	 * Import a XML File containing a list of User data model and create a backup file of current XML File in backup folder
	 * @param path Path from where to import the XML File
	 */
	public static void importUsers(String path) {
		makeBackup(MagicValues.UserDMPath,MagicValues.UserDMBackupPath + MagicValues.UserDMName);
		IOUtil.copyFile(path, MagicValues.UserDMPath);
	}
	
	/**
	 * Import a XML File containing a list of Category data model and create a backup file of current XML File in backup folder
	 * @param path Path from where to import the XML File
	 */
	public static void importCategories(String path) {
		makeBackup(MagicValues.CategoryDMPath,MagicValues.CategoryDMBackupPath + MagicValues.CategoryDMName);
		IOUtil.copyFile(path, MagicValues.CategoryDMPath);
	}

	/**
	 * Create a new XML File containing a list of User data model and create a backup file of current XML File in backup folder
	 */
	public static void newUsers() {
		makeBackup(MagicValues.UserDMPath,MagicValues.UserDMBackupPath + MagicValues.UserDMName);
		IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.UsersTagName),
				MagicValues.UserDMPath);
	}

	/**
	 * Create a new XML File containing a list of Movie data model and create a backup file of current XML File in backup folder
	 */
	public static void newMovies() {
		makeBackup(MagicValues.MovieDMPath,MagicValues.MovieDMBackupPath + MagicValues.MovieDMName);
		IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.MoviesTagName),
				MagicValues.MovieDMPath);
	}

	/**
	 * Create a new XML File containing a list of Category data model and create a backup file of current XML File in backup folder
	 */
	public static void newCategories() {
		makeBackup(MagicValues.CategoryDMPath,MagicValues.CategoryDMBackupPath + MagicValues.CategoryDMName);
		IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.CategoriesTagName),
				MagicValues.CategoryDMPath);
	}
	
	/**
	 * Create a backup file of a file 
	 * @param from File path
	 * @param nameBase new file path with name without extension
	 */
	private static void makeBackup(String from, String nameBase) {
		IOUtil.copyFile(from, nameBase + "_backup_"
				+ UtilModule.getDateNowString() + ".xml");
	}

}
