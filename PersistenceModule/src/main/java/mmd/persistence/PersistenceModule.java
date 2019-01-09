package mmd.persistence;

import java.io.File;
import java.net.URL;

import mmd.util.MagicValues;
import mmd.util.errorhandling.ErrorHandlerUtil;
import mmd.util.io.IOUtil;
public class PersistenceModule
{
    public static void init() {
	createPaths();
    }


    private static void createMovieDMPath() {
	File MovieDMPath=new File(MagicValues.MovieDMPath);
	if(!MovieDMPath.exists()) {
	    MovieDMPath.getParentFile().mkdirs();
	    IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.MoviesTagName), MovieDMPath.getAbsolutePath());
	}
    }

    private static void createMovieDMThumbnailPath() {
	File MovieThumbnailPath=new File(MagicValues.MovieThumbnailPath+"/"+MagicValues.MovieDefaultThumbnail);
	if(!MovieThumbnailPath.exists()) {
	    MovieThumbnailPath.getParentFile().mkdirs();
	    URL defaultThumbnail =PersistenceModule.class.getClassLoader().getResource("mmd/presentation/img/movies/"+MagicValues.MovieDefaultThumbnail);
	    IOUtil.copyFile(new File(defaultThumbnail.getFile()).getAbsolutePath(),MagicValues.MovieThumbnailPath+"/"+MagicValues.MovieDefaultThumbnail );
	}
    }

    private static void createPaths()
    {
	try {
	    createMovieDMPath();
	    createUserDMPath();
	    createMovieDMThumbnailPath();


	}
	catch (Exception e) {
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }

    private static void createUserDMPath() {
	File UserDMPath=new File(MagicValues.UserDMPath);
	if(!UserDMPath.exists()) {
	    UserDMPath.getParentFile().mkdirs();
	    IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.UsersTagName), UserDMPath.getAbsolutePath());
	}
    }
}
