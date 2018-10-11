package lab2b.state;

import lab2b.Main;

import java.io.IOException;
import java.net.SocketException;

public class StateInSession extends AbstractBusyState{

    public StateInSession(MachineData machineData) {
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.IN_SESSION;
    }

    @Override
    public AbstractVoiceAppState endCall() throws SocketException {
        System.out.println("Method call: endCall\nState: InSession\noutsignal: BYE");
        getMachineData().getAudioUDPStream().stopStreaming();
        getMachineData().getAudioUDPStream().close();
        getMachineData().getClientPrintWriter().println("BYE");
        getMachineData().getClientSocket().setSoTimeout(Main.SOCKET_TIMEOUT);
        return new StateEndingCall(getMachineData());
    }

    @Override
    public AbstractVoiceAppState bye() throws IOException {
        System.out.println("Method call: bye\nState: InSession\noutsignal: OK");
        getMachineData().getAudioUDPStream().stopStreaming();
        getMachineData().getAudioUDPStream().close();
        getMachineData().getClientPrintWriter().println("OK");
        getMachineData().getClientSocket().setSoTimeout(Main.SOCKET_TIMEOUT);
        getMachineData().reset();
        return new StateWaiting(getMachineData());
    }
}
