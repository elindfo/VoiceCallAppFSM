package state;

public class VoiceAppStateHandler {

    private AbstractVoiceAppState currentState;

    public VoiceAppStateHandler(VoiceAppState initialState){
        switch(initialState){
            case WAITING: currentState = new StateWaiting(); break;
            case RINGING: currentState = new StateRinging(); break;
            case IN_SESSION: currentState = new StateInSession(); break;
            case CALLING: currentState = new StateCalling(); break;
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

    public VoiceAppState getCurrentState(){
        return currentState.getState();
    }
}
