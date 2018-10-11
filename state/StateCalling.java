package lab2b.state;

public class StateCalling extends AbstractBusyState{

    public StateCalling(MachineData machineData){
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.CALLING;
    }

    @Override
    public AbstractVoiceAppState tro(){
        System.out.println("Method: tro\nState: Calling\noutsignal: ACK");
        getMachineData().getClientPrintWriter().println("ACK");
        return new StateInSession(getMachineData());
    }
}
