package state;

public class StateRinging extends AbstractBusyState{

    public StateRinging(MachineData machineData) {
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.RINGING;
    }

    @Override
    public AbstractVoiceAppState ack(){
        return new StateInSession(getMachineData());
    }
}
