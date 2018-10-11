package lab2b.state;

//TODO Fixa errorhantering, t.ex. om man skickar ACK från fel tillstånd.

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public abstract class AbstractVoiceAppState {

    private MachineData machineData;

    protected AbstractVoiceAppState(MachineData machineData){
        this.machineData = machineData;
    }

    protected MachineData getMachineData(){
        return machineData;
    }

    public abstract VoiceAppState getState();

    public AbstractVoiceAppState invite() throws IOException {
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ack() throws IOException {
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR ack");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState bye() throws IOException {
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR bye");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState call(){
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR call");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState tro() throws IOException {
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR tro");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState endCall() throws SocketException {
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR endCall");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ok() throws IOException {
        System.out.println("output: ERR");
        getMachineData().getClientPrintWriter().println("ERR ok");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState err(String m) throws IOException {
        System.out.println("ERR received. Message: " + m);
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState busy() throws IOException {
        System.out.println("Server: BUSY");
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public boolean isBusy(){
        return false;
    }
}
