package state;

public class StateInSession extends AbstractBusyState{

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.IN_SESSION;
    }

    @Override
    public AbstractVoiceAppState bye(){
        System.out.println("outsignal: OK");
        return new StateWaiting();
    }
}
