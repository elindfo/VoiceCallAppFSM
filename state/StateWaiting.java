package state;

public class StateWaiting extends AbstractVoiceAppState{

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.WAITING;
    }

    @Override
    public AbstractVoiceAppState invite(){
        System.out.println("outsignal: TRO");
        return new StateRinging();
    }

    @Override
    public AbstractVoiceAppState call(){
        System.out.println("outsignal: INVITE");
        return new StateCalling();
    }
}
