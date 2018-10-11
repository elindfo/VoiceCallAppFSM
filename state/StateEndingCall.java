package lab2b.state;

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
        System.out.println("Method call: ok\nState: EndingCall\noutsignal:");
        getMachineData().reset();
        return new StateWaiting(getMachineData());
    }
}
