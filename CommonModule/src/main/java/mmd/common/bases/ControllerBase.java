package mmd.common.bases;

import javafx.fxml.Initializable;
import mmd.common.enums.StageNameEnum;
import mmd.common.types.Tuple;

public abstract class ControllerBase implements Initializable
{
    public ControllerBase() {
	this.initName();
    }
    protected Tuple<Object, Class<?>> stageData;
    protected StageNameEnum stageName;


    public StageNameEnum getName() {
	return this.stageName;
    }

    public Tuple<Object, Class<?>> getStageData(){return this.stageData;}
    public void setStageData(final Tuple<Object, Class<?>> stageData){this.stageData=stageData;}

    protected abstract void initName();
}
