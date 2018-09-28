package state;

//TODO Fixa errorhantering, t.ex. om man skickar ACK från fel tillstånd.

import java.io.IOException;
import java.net.Socket;

public abstract class AbstractVoiceAppState {

    private MachineData machineData;

    protected AbstractVoiceAppState(MachineData machineData){
        this.machineData = machineData;
    }

    protected MachineData getMachineData(){
        return machineData;
    }

    public abstract VoiceAppState getState();

    public AbstractVoiceAppState invite(Socket clientSocket) throws IOException {
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ack(){
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR ack");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState bye(){
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR bye");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState call(Socket clientSocket){
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR call");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState tro(){
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR tro");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState endCall(){
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR endCall");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ok(){
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR ok");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState err(String m){
        System.out.println("ERR received. Message: " + m);
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState busy(){
        System.out.println("Server: BUSY");
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public boolean isBusy(){
        return false;
    }
}
