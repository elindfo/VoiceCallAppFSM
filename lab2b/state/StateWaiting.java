package lab2b.state;

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
        System.out.println("Method call: invite\nState: Waiting\noutsignal: TRO");
        getMachineData().getClientPrintWriter().println("TRO " + getMachineData().getAudioUDPStream().getLocalPort());
        return new StateRinging(getMachineData());
    }

    @Override
    public AbstractVoiceAppState call(){
        System.out.println("Method call: call\nState: Waiting\noutsignal: INVITE " + getMachineData().getAudioUDPStream().getLocalPort());
        getMachineData().getClientPrintWriter().println("INVITE " + getMachineData().getAudioUDPStream().getLocalPort());
        return new StateCalling(getMachineData());
    }
}
