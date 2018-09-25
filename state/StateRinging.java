package state;

public class StateRinging extends AbstractBusyState{

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.RINGING;
    }

    @Override
    public AbstractVoiceAppState ack(){
        return new StateInSession();
    }
}
