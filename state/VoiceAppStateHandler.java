package state;

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
        currentState = currentState.invite(clientSocket);
    }

    public void invokeAck(){
        currentState = currentState.answer();
    }

    public void invokeBye(){
        currentState = currentState.bye();
    }

    public void invokeCall(Socket clientSocket){
        currentState = currentState.call(clientSocket);
    }

    public void invokeTro(){
        currentState = currentState.callAnswered();
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
}
