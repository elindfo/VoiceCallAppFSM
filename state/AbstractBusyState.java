package state;

public abstract class AbstractBusyState extends AbstractVoiceAppState{

    @Override
    public boolean isBusy(){
        return true;
    }
}
