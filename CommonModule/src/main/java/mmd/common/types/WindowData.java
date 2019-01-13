package mmd.common.types;

import javafx.stage.Stage;

/**
 * Class that store Data used for Window data transfer
 *
 */
public class WindowData
{
	private GenericData data;
	private Stage stage;
	
	/**
	 * Default constructor
	 * 
	 * @param stage Window stage
	 * @param data  Window data
	 */
	public WindowData(final Stage stage, final GenericData data)
	{
		this.stage = stage;
		this.data = data;
	}
	
	/**
	 * Get Window Data
	 * 
	 * @return {@link GenericData}
	 */
	public GenericData getData()
	{
		return this.data;
	}
	
	/**
	 * Set Window Data
	 * 
	 * @param data Data to set
	 */
	public void setData(final GenericData data)
	{
		this.data = data;
	}
	
	/**
	 * Get Window Stage
	 * 
	 * @return Window Stage
	 */
	public Stage getStage()
	{
		return this.stage;
	}
	
	/**
	 * Set Stage
	 * 
	 * @param stage Stage to set
	 */
	public void setStage(final Stage stage)
	{
		this.stage = stage;
	}
}
