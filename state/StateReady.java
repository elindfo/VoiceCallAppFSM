package state;

public class StateReady extends AbstractVoiceAppState{

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.READY;
    }

    @Override
    public AbstractVoiceAppState invite(){
        System.out.println("outsignal: TRO");
        return new StateRinging();
    }
}
