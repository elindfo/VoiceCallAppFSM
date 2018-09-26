package state;

public class StateCalling extends AbstractBusyState{

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.CALLING;
    }

    @Override
    public AbstractVoiceAppState tro(){
        System.out.println("outsignal: ACK");
        return new StateInSession();
    }
}
