package lab2b.state;

import java.io.IOException;

public class StateEndingCall extends AbstractBusyState{

    public StateEndingCall(MachineData machineData) {
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.ENDING_CALL;
    }

    @Override
    public AbstractVoiceAppState ok() throws IOException {
        System.out.println("Method call: ok\nState: EndingCall\noutsignal:");
        getMachineData().reset();
        return new StateWaiting(getMachineData());
    }
}
