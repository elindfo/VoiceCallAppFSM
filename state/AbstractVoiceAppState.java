package state;

//TODO Fixa errorhantering, t.ex. om man skickar ACK från fel tillstånd.

public abstract class AbstractVoiceAppState {

    private MachineData machineData;

    protected AbstractVoiceAppState(MachineData machineData){
        this.machineData = machineData;
    }

    protected MachineData getMachineData(){
        return machineData;
    }

    public abstract VoiceAppState getState();

    public AbstractVoiceAppState invite(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ack(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState bye(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState call(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState tro(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState endCall(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public AbstractVoiceAppState ok(){
        System.out.println("output: ERR");
        return new StateWaiting(machineData);
    }

    public boolean isBusy(){
        return false;
    }
}
