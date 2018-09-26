package state;

//TODO Fixa errorhantering, t.ex. om man skickar ACK från fel tillstånd.

public abstract class AbstractVoiceAppState {

    public abstract VoiceAppState getState();

    public AbstractVoiceAppState invite(){
        System.out.println("output: ERR");
        return new StateWaiting();
    }

    public AbstractVoiceAppState ack(){
        System.out.println("output: ERR");
        return new StateWaiting();
    }

    public AbstractVoiceAppState bye(){
        System.out.println("output: ERR");
        return new StateWaiting();
    }

    public AbstractVoiceAppState call(){
        System.out.println("output: ERR");
        return new StateWaiting();
    }

    public AbstractVoiceAppState tro(){
        System.out.println("output: ERR");
        return new StateWaiting();
    }

    public AbstractVoiceAppState endCall(){
        System.out.println("output: ERR");
        return new StateWaiting();
    }

    public AbstractVoiceAppState ok(){
        System.out.println("output: ERR");
        return new StateWaiting();
    }

    public boolean isBusy(){
        return false;
    }
}
