package mmd.common.bases;

import javafx.event.Event;
import javafx.fxml.Initializable;
import mmd.common.enums.SceneNameEnum;
import mmd.common.types.GenericData;

/**
 * 
 * Abstract class that has basic methods and fields of a Controllers
 */
public abstract class ControllerBase implements Initializable
{
	/**
	 * <h1>Default constructor</h1>
	 * <p>
	 * Set the name of controller
	 * </p>
	 */
	public ControllerBase()
	{
		this.initName();
	}
	
	protected GenericData stageData;
	protected SceneNameEnum stageName;
	
	/**
	 * Method to get the name of controller
	 * 
	 * @return {@link SceneNameEnum} name of this controller
	 */
	public SceneNameEnum getName()
	{
		return this.stageName;
	}
	
	/**
	 * Method to get this controller saved data
	 * 
	 * @return {@link GenericData} data of this controller
	 */
	public GenericData getStageData()
	{
		return this.stageData;
	}
	

	/**
	 * Method to set this controller saved data
	 * 
	 * @return {@link GenericData} data of this controller
	 */
	public void setStageData(final GenericData stageData)
	{
		this.stageData = stageData;
	}
	
	/**
	 * Method to close the stage and clear everything from Controller
	 * 
	 * @param event Event that fired shutdown
	 */
	public void shutdown(final Event event)
	{
	}
	
	/**
	 * Method to set controller name
	 */
	protected abstract void initName();
}
