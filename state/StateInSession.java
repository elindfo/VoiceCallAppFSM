package state;

public class StateInSession extends AbstractBusyState{

    public StateInSession(MachineData machineData) {
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.IN_SESSION;
    }

    @Override
    public AbstractVoiceAppState endCall(){
        System.out.println("Method call: endCall\nState: InSession\noutsignal: BYE");
        getMachineData().getClientPrintWriter().println("BYE");
        return new StateEndingCall(getMachineData());
    }

    @Override
    public AbstractVoiceAppState bye(){
        System.out.println("Method call: bye\nState: InSession\noutsignal: OK");
        getMachineData().getClientPrintWriter().println("OK");
        return new StateWaiting(getMachineData());
    }
}
