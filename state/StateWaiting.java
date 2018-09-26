package state;

public class StateWaiting extends AbstractVoiceAppState{

    public StateWaiting(MachineData machineData) {
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.WAITING;
    }

    @Override
    public AbstractVoiceAppState invite(){
        System.out.println("outsignal: TRO");
        getMachineData().getClientPrintWriter().println("TRO");
        return new StateRinging(getMachineData());
    }

    @Override
    public AbstractVoiceAppState call(){
        System.out.println("outsignal: INVITE");
        getMachineData().getClientPrintWriter().println("INVITE");
        return new StateCalling(getMachineData());
    }
}
