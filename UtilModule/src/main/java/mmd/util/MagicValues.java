package mmd.util;

public class MagicValues
{
    public final static String AppDataPath = System.getenv("APPDATA") + "/.mmd/";

    public final static String MoviesDataPath = AppDataPath + "movies/";

    public static final String MovieDefaultThumbnail = "movieThumbnail.png";

    public final static String MovieDMName = "Movie";

    public final static String MovieDMPath = MoviesDataPath + MovieDMName + ".xml";

    public static final String MoviesTagName = "Movies";

    public static final String MovieThumbnailPath = MoviesDataPath + "thumbnails/";

    public static String getMovieThumbnailPath(final String name)
    {
	return MagicValues.class.getClassLoader().getResource("mmd/presentation/img/movies/").toExternalForm();
    }
}
