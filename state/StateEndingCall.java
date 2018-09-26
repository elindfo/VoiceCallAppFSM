package state;

public class StateEndingCall extends AbstractBusyState{

    public StateEndingCall(MachineData machineData) {
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.ENDING_CALL;
    }

    @Override
    public AbstractVoiceAppState ok(){
        return new StateWaiting(getMachineData());
    }
}
