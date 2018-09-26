package state;

//TODO Fixa errorhantering, t.ex. om man skickar ACK från fel tillstånd.

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

    public AbstractVoiceAppState endCall(){
        return this;
    }

    public AbstractVoiceAppState ok(){
        return this;
    }

    public boolean isBusy(){
        return false;
    }
}
