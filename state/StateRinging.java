package lab2b.state;

import java.io.IOException;
import java.net.InetAddress;

public class StateRinging extends AbstractBusyState{

    public StateRinging(MachineData machineData) {
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.RINGING;
    }

    @Override
    public AbstractVoiceAppState ack() throws IOException {
        System.out.println("Method call: ack\nState: Ringing\noutsignal:");
        InetAddress inetAddress = getMachineData().getClientSocket().getInetAddress();
        System.out.println("ack: initiating call with " + inetAddress.getHostAddress());
        int port = getMachineData().getRemotePort();
        getMachineData().getAudioUDPStream().connectTo(inetAddress, port);
        getMachineData().getAudioUDPStream().startStreaming();
        return new StateInSession(getMachineData());
    }
}
