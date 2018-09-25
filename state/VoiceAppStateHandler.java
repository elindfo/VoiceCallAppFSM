package state;

public class VoiceAppStateHandler {

    private AbstractVoiceAppState currentState;

    public VoiceAppStateHandler(VoiceAppState initialState){
        switch(initialState){
            case READY: currentState = new StateReady(); break;
            case RINGING: currentState = new StateRinging(); break;
            case IN_SESSION: currentState = new StateInSession(); break;
            case HANGING_UP: currentState = new StateHangingUp(); break;
        }
    }

    public void invokeInvite(){
        currentState = currentState.invite();
    }

    public void invokeSendAck(){
        currentState = currentState.sendAck();
    }

    public void invokeEndCall(){
        currentState = currentState.endCall();
    }

    public VoiceAppState getCurrentState(){
        return currentState.getState();
    }
}
