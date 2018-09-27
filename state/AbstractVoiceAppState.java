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

    public AbstractVoiceAppState answer(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState bye(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState call(Socket clientSocket){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState callAnswered(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState endCall(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ok(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState err(String m){
        reset();
        return new StateWaiting(machineData);
    }

    private void reset(){

    }

    public boolean isBusy(){
        return false;
    }
}
