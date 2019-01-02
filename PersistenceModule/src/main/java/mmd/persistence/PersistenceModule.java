package mmd.persistence;

import java.io.File;

import mmd.common.MagicValues;

public class PersistenceModule
{
    public static void init() {
	createPaths();
    }

    private static void createPaths()
    {
	File file=new File(MagicValues.MovieDMPath);
	if(!file.exists()) {
	    file.getParentFile().mkdirs();
	}

    }
}
