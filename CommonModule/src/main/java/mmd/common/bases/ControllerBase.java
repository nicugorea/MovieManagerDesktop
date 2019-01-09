package mmd.common.bases;

import javafx.event.Event;
import javafx.fxml.Initializable;
import mmd.common.enums.SceneNameEnum;
import mmd.common.types.GenericData;

public abstract class ControllerBase implements Initializable
{
    public ControllerBase() {
	this.initName();
    }
    protected GenericData stageData;
    protected SceneNameEnum stageName;


    public SceneNameEnum getName() {
	return this.stageName;
    }

    public GenericData getStageData(){return this.stageData;}
    public void setStageData(final GenericData stageData){this.stageData=stageData;}

    public void shutdown(final Event event) {
    }

    protected abstract void initName();
}
