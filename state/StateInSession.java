package state;

public class StateInSession extends AbstractBusyState{

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.IN_SESSION;
    }

    @Override
    public AbstractVoiceAppState endCall(){
        System.out.println("outsignal: BYE");
        return new StateEndingCall();
    }

    @Override
    public AbstractVoiceAppState bye(){
        System.out.println("outsignal: OK");
        return new StateWaiting();
    }
}
