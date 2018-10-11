package lab2b.state;

import java.io.IOException;
import java.net.Socket;

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

    public void invokeInvite(Socket clientSocket) throws IOException {
        currentState = currentState.invite();
    }

    public void invokeAck(){
        currentState = currentState.ack();
    }

    public void invokeBye(){
        currentState = currentState.bye();
    }

    public void invokeCall(){
        currentState = currentState.call();
    }

    public void invokeTro(){
        currentState = currentState.tro();
    }

    public void invokeEndCall() {
        currentState = currentState.endCall();
    }

    public void invokeOk(){
        currentState = currentState.ok();
    }

    public boolean invokeIsBusy(){
        return currentState.isBusy();
    }

    public void invokeErr(String m){
        currentState = currentState.err(m);
    }

    public void invokeBusy(){
        currentState = currentState.busy();
    }

    public VoiceAppState getCurrentState(){
        return currentState.getState();
    }

    public MachineData getMachineData(){
        return machineData;
    }

    public void reset(){
        machineData.reset();
        currentState = new StateWaiting(machineData);
    }
}
