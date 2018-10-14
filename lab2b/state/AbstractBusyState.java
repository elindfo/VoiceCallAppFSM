package lab2b.state;

public abstract class AbstractBusyState extends AbstractVoiceAppState{


    protected AbstractBusyState(MachineData machineData) {
        super(machineData);
    }

    @Override
    public boolean isBusy(){
        return true;
    }
}
