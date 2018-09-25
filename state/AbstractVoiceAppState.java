package state;

public abstract class AbstractVoiceAppState {

    public abstract VoiceAppState getState();

    public AbstractVoiceAppState invite(){
        return this;
    }

    public AbstractVoiceAppState sendAck(){
        return this;
    }

    public AbstractVoiceAppState endCall(){
        return this;
    }
}
