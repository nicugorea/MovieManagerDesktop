package mmd.common.definitions;

import java.util.ArrayList;
import java.util.List;

import mmd.common.types.Property;

/**
 * Interface that define a data model
 * 
 * @param <T>
 */
public interface DMDefinition<T>
{

	/**
	 * Method to get the name of this Data Model
	 * 
	 * @return {@link String} name of this Data Model
	 */
    String getName();

    /**
	 * Method to get properties list from a Data Model
	 * @return a {@link List} of {@link Property} 
	 */
    ArrayList<Property> getProperties();

    /**
     * Method to get a new instance of this object having default values
     * 
     * @param object An instance of this object with incomplete fields
     * @return An instance of this Data Model
     */
    T newInstance(Object object);

}

