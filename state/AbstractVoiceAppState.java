package state;

public abstract class AbstractVoiceAppState {

    public abstract VoiceAppState getState();

    public AbstractVoiceAppState invite(){
        return this;
    }

    public AbstractVoiceAppState ack(){
        return this;
    }

    public AbstractVoiceAppState bye(){
        return this;
    }

    public AbstractVoiceAppState call(){
        return this;
    }

    public AbstractVoiceAppState tro(){
        return this;
    }
}
