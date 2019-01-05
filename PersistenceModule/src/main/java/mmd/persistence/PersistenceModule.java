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

    private static void createPaths()
    {
	try {

	    File MovieDMPath=new File(MagicValues.MovieDMPath);
	    if(!MovieDMPath.exists()) {
		MovieDMPath.getParentFile().mkdirs();
		IOUtil.saveDOMDocumentToXMLFile(IOUtil.createEmptyDOMDocumetWithParentTag(MagicValues.MoviesTagName), MovieDMPath.getAbsolutePath());
	    }
	    File MovieThumbnailPath=new File(MagicValues.MovieThumbnailPath+"/"+MagicValues.MovieDefaultThumbnail);
	    if(!MovieThumbnailPath.exists()) {
		MovieThumbnailPath.getParentFile().mkdirs();
		URL defaultThumbnail =PersistenceModule.class.getClassLoader().getResource("mmd/presentation/img/movies/"+MagicValues.MovieDefaultThumbnail);
		IOUtil.copyFile(new File(defaultThumbnail.getFile()).getAbsolutePath(),MagicValues.MovieThumbnailPath+"/"+MagicValues.MovieDefaultThumbnail );
	    }
	}
	catch (Exception e) {
	    ErrorHandlerUtil.handleThrowable(e);
	}
    }
}
