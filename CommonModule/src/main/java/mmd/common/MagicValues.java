package mmd.common;

public class MagicValues
{
    public final static String AppDataPath = System.getenv("APPDATA") + "/.mmd";

    public final static String MovieDataPath = AppDataPath + "/movies";

    public final static String MovieDMName = "Movie";

    public final static String MovieDMPath = MovieDataPath + "/" + MovieDMName + ".xml";

    public static final String MoviesTagName = "Movies";

    public static String getMovieThumbnailPath(final String name)
    {
	return MagicValues.class.getClassLoader().getResource("mmd/presentation/img/movies/").toExternalForm();
    }
}
