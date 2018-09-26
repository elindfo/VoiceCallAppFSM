package state;

public class VoiceAppStateHandler {

    private AbstractVoiceAppState currentState;

    public VoiceAppStateHandler(VoiceAppState initialState, MachineData machineData){
        switch(initialState){
            case WAITING: currentState = new StateWaiting(machineData); break;
            case RINGING: currentState = new StateRinging(machineData); break;
            case IN_SESSION: currentState = new StateInSession(machineData); break;
            case CALLING: currentState = new StateCalling(machineData); break;
        }
    }

    public void invokeInvite(){
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

    public VoiceAppState getCurrentState(){
        return currentState.getState();
    }
}
