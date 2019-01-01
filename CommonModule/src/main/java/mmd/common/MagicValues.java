package mmd.common;

public class MagicValues
{
    public static String getMovieThumbnailPath(final String name) {return MagicValues.class.getClassLoader().getResource("mmd/presentation/img/movies/").toExternalForm();}

    public static void init() {

    }

}
