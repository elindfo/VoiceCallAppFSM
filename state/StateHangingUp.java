package state;

public class StateHangingUp extends AbstractBusyState{
    @Override
    public VoiceAppState getState() {
        return VoiceAppState.HANGING_UP;
    }
}
