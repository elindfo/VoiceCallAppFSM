package lab2b.state;

import java.io.IOException;
import java.net.SocketException;

public class VoiceAppStateHandler {

    private AbstractVoiceAppState currentState;
    private MachineData machineData;

    public VoiceAppStateHandler(VoiceAppState initialState){
        this.machineData = new MachineData();
        switch(initialState){
            case WAITING: currentState = new StateWaiting(this.machineData); break;
            case RINGING: currentState = new StateRinging(this.machineData); break;
            case IN_SESSION: currentState = new StateInSession(this.machineData); break;
            case CALLING: currentState = new StateCalling(this.machineData); break;
        }
    }

    public synchronized void invokeInvite() throws IOException {
        currentState = currentState.invite();
    }

    public synchronized void invokeAck() throws IOException {
        currentState = currentState.ack();
    }

    public synchronized void invokeBye() throws IOException {
        currentState = currentState.bye();
    }

    public synchronized void invokeCall(){
        currentState = currentState.call();
    }

    public synchronized void invokeTro() throws IOException {
        currentState = currentState.tro();
    }

    public synchronized void invokeEndCall() throws SocketException {
        currentState = currentState.endCall();
    }

    public synchronized void invokeOk() throws IOException {
        currentState = currentState.ok();
    }

    public synchronized boolean invokeIsBusy(){
        return currentState.isBusy();
    }

    public synchronized void invokeErr(String m) throws IOException {
        currentState = currentState.err(m);
    }

    public synchronized void invokeBusy() throws IOException {
        currentState = currentState.busy();
    }

    public synchronized VoiceAppState getCurrentState(){
        return currentState.getState();
    }

    public synchronized MachineData getMachineData(){
        return machineData;
    }

    public synchronized void reset() throws IOException {
        machineData.reset();
        currentState = new StateWaiting(machineData);
    }
}
