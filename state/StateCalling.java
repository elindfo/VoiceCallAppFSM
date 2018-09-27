package state;

public class StateCalling extends AbstractBusyState{

    public StateCalling(MachineData machineData){
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.CALLING;
    }

    @Override
    public AbstractVoiceAppState callAnswered(){
        System.out.println("outsignal: ACK");
        System.out.println("callAnswered: " + getMachineData().getClientSocket().toString());
        getMachineData().getClientPrintWriter().println("ACK");
        return new StateInSession(getMachineData());
    }
}
