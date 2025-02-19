package lab2b.state;

import java.io.IOException;
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
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ack() throws IOException {
        System.out.println("output: ERR");
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState bye() throws IOException {
        System.out.println("output: ERR");
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState call() throws IOException {
        System.out.println("output: ERR");
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState tro() throws IOException {
        System.out.println("output: ERR");
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState endCall() throws IOException {
        System.out.println("output: ERR");
        getMachineData().reset();
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ok() throws IOException {
        System.out.println("output: ERR");
        getMachineData().reset();
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
