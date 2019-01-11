package mmd.persistence;

import java.io.File;
import java.net.URL;

import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;

public class PersistenceModule {
	public static void init() {
		createPaths();
	}

	private static void createMovieDMFile() {
		File file = new File(MagicValues.MovieDMPath);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.MoviesTagName),
					file.getAbsolutePath());
		}
	}

	private static void createPath(String path) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

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

	private static void createPaths() {
		try {
			createMovieDMFile();
			createUserDMFile();
			createMovieDMThumbnailFile();
			createPath(MagicValues.MovieDMBackupPath);
			createPath(MagicValues.UserDMBackupPath);

		} catch (Exception e) {
			ErrorHandlerUtil.handleThrowable(e);
		}
	}

	private static void createUserDMFile() {
		File file = new File(MagicValues.UserDMPath);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.UsersTagName),
					file.getAbsolutePath());
		}
	}
}
