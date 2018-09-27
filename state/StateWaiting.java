package state;

import java.io.IOException;
import java.net.Socket;

public class StateWaiting extends AbstractVoiceAppState{

    public StateWaiting(MachineData machineData) {
        super(machineData);
    }

    @Override
    public VoiceAppState getState() {
        return VoiceAppState.WAITING;
    }

    @Override
    public AbstractVoiceAppState invite(Socket clientSocket) throws IOException{
        System.out.println("outsignal: TRO");
        getMachineData().setClientSocket(clientSocket);
        getMachineData().getClientPrintWriter().println("TRO");
        return new StateRinging(getMachineData());
    }

    @Override
    public AbstractVoiceAppState call(Socket clientSocket){
        System.out.println("outsignal: INVITE");
        try {
            getMachineData().setClientSocket(clientSocket);
            getMachineData().getClientPrintWriter().println("INVITE");
            return new StateCalling(getMachineData());
        } catch (IOException e) {
            System.out.println("call IOException");
            return this;
        }
    }
}
