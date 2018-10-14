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
        System.out.println("Method call: ack State: Ringing outsignal:");
        InetAddress inetAddress = getMachineData().getClientSocket().getInetAddress();
        int port = getMachineData().getRemotePort();
        System.out.println("ack: initiating call with " + inetAddress.getHostAddress() + " on port " + port);
        getMachineData().getAudioUDPStream().connectTo(inetAddress, port);
        getMachineData().getAudioUDPStream().startStreaming();
        getMachineData().getClientSocket().setSoTimeout(0);
        return new StateInSession(getMachineData());
    }
}
