package mmd.common.bases;

import mmd.common.definitions.DMDefinition;
import mmd.util.errorhandling.ErrorHandlerUtil;

public class DMBase<T> implements DMDefinition<T>
{

    @Override
    public String getName()
    {
	try
	{
	    return "";
	}
	catch (Throwable e)
	{
	    ErrorHandlerUtil.handleThrowable(e);
	}
	return null;
    }

}
