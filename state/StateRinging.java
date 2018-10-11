package lab2b.state;

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
        System.out.println("Method call: ack\nState: Ringing\noutsignal:");
        return new StateInSession(getMachineData());
    }
}
