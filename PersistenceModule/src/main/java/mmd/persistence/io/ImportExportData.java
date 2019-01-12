package mmd.persistence.io;

import java.text.SimpleDateFormat;
import java.util.Date;

import mmd.util.MagicValues;
import mmd.util.UtilModule;
import mmd.util.io.IOUtil;

public class ImportExportData {

	public static void exportMovies(String path) {
		IOUtil.copyFile(MagicValues.MovieDMPath, path);
	}

	public static void exportUsers(String path) {
		IOUtil.copyFile(MagicValues.UserDMPath, path);
	}

	public static void exportCategories(String path) {
		IOUtil.copyFile(MagicValues.CategoryDMPath, path);
	}

	public static void importMovies(String path) {
		makeBackup(MagicValues.MovieDMPath,MagicValues.MovieDMBackupPath + MagicValues.MovieDMName);
		IOUtil.copyFile(path, MagicValues.MovieDMPath);

	}

	public static void importUsers(String path) {
		makeBackup(MagicValues.UserDMPath,MagicValues.UserDMBackupPath + MagicValues.UserDMName);
		IOUtil.copyFile(path, MagicValues.UserDMPath);
	}
	
	public static void importCategories(String path) {
		makeBackup(MagicValues.CategoryDMPath,MagicValues.CategoryDMBackupPath + MagicValues.CategoryDMName);
		IOUtil.copyFile(path, MagicValues.CategoryDMPath);
	}

	public static void newUsers() {
		makeBackup(MagicValues.UserDMPath,MagicValues.UserDMBackupPath + MagicValues.UserDMName);
		IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.UsersTagName),
				MagicValues.UserDMPath);
	}

	public static void newMovies() {
		makeBackup(MagicValues.MovieDMPath,MagicValues.MovieDMBackupPath + MagicValues.MovieDMName);
		IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.MoviesTagName),
				MagicValues.MovieDMPath);
	}

	public static void newCategories() {
		makeBackup(MagicValues.CategoryDMPath,MagicValues.CategoryDMBackupPath + MagicValues.CategoryDMName);
		IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.CategoriesTagName),
				MagicValues.CategoryDMPath);
	}
	
	private static void makeBackup(String from, String nameBase) {
		IOUtil.copyFile(from, nameBase + "_backup_"
				+ UtilModule.getDateNowString() + ".xml");
	}

}
