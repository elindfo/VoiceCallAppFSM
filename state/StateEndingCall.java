package state;

public class StateEndingCall extends AbstractBusyState{
    @Override
    public VoiceAppState getState() {
        return VoiceAppState.ENDING_CALL;
    }

    @Override
    public AbstractVoiceAppState ok(){
        return new StateWaiting();
    }
}
