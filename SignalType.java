public enum SignalType {

    INVITE("INVITE"),
    ACK("ACK"),
    OK("OK"),
    TRO("TRO"),
    BYE("BYE"),
    END_CALL("END_CALL"),
    ERR("ERR"),
    BUSY("BUSY"),
    NONE("NONE");

    private final String info;

    SignalType(String info){
        this.info = info;
    }

    @Override
    public String toString(){
        return info;
    }
}
