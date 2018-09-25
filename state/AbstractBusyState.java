package state;

public abstract class AbstractBusyState extends AbstractVoiceAppState{

    @Override
    public AbstractVoiceAppState invite(){
        System.out.println("outsignal: BUSY");
        return this;
    }
}
