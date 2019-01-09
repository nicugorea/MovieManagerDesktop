package mmd.common.types;

import javafx.stage.Stage;

public class WindowData
{
    private GenericData data;
    private Stage stage;

    public WindowData(final Stage stage,final GenericData data) {
	this.stage=stage;
	this.data=data;
    }



    public GenericData getData()
    {
	return this.data;
    }

    public void setData(final GenericData data)
    {
	this.data = data;
    }

    public Stage getStage()
    {
	return this.stage;
    }

    public void setStage(final Stage stage)
    {
	this.stage = stage;
    }
}
