package state;

public abstract class AbstractBusyState extends AbstractVoiceAppState{

    @Override
    public AbstractVoiceAppState invite(){
        System.out.println("Server busy");
        return this;
    }
}
