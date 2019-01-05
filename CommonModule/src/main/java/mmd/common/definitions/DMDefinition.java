package mmd.common.definitions;

import java.util.ArrayList;

import mmd.common.models.Property;

public interface DMDefinition<T>
{

    String getName();

    ArrayList<Property> getProperties();

    T newInstance();



}

