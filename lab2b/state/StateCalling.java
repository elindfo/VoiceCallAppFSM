package lab2b.state;

import java.io.IOException;
import java.net.InetAddress;

public class StateCalling extends AbstractBusyState{

    public StateCalling(MachineData machineData){
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.CALLING;
    }

    @Override
    public AbstractVoiceAppState tro() throws IOException {
        System.out.println("Method: tro State: Calling outsignal: ACK");
        getMachineData().getClientPrintWriter().println("ACK");
        InetAddress inetAddress = getMachineData().getClientSocket().getInetAddress();
        System.out.println("tro: initiating call with " + inetAddress.getHostAddress());
        int port = getMachineData().getRemotePort();
        getMachineData().getAudioUDPStream().connectTo(inetAddress, port);
        getMachineData().getAudioUDPStream().startStreaming();
        getMachineData().getClientSocket().setSoTimeout(0);
        return new StateInSession(getMachineData());
    }
}
