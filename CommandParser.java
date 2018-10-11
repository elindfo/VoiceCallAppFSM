import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandParser {

    private static String input = "";
    private static SignalType command;
    private static ArrayList<String> args;

    private CommandParser(){}

    public static void load(String s)  throws IllegalArgumentException{
        if(s.isEmpty()){
            throw new IllegalArgumentException("Empty string");
        }
        input = s.toUpperCase();
        command = SignalType.NONE;
        args = new ArrayList<>();
        parse();
    }

    private static void parse() throws IllegalStateException{
        if(input.isEmpty()){
            throw new IllegalStateException("Command not loaded");
        }
        String[] splitInput = input.split(" ");
        //Get command
        switch(splitInput[0]){
            case "INVITE": command = SignalType.INVITE; break;
            case "TRO": command = SignalType.TRO; break;
            case "ACK": command = SignalType.ACK; break;
            case "OK": command = SignalType.OK; break;
            case "END_CALL": command = SignalType.END_CALL; break;
            case "BYE": command = SignalType.BYE; break;
            case "ERR": command = SignalType.ERR; break;
            case "BUSY": command = SignalType.BUSY; break;
            default: command = SignalType.NONE; return;
        }

        //Get arguments
        for(int i = 1; i < splitInput.length; i++){
            args.add(splitInput[i]);
        }
    }

    public static int getNoOfArgs(){
        return args.size();
    }

    public static SignalType getCommand(){
        return command;
    }

    public static List<String> getArgs(){
        return (List<String>)args.clone();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String commands = "";
        do{
            System.out.print("Command: ");
            commands = input.nextLine();
            CommandParser.load(commands);
            System.out.println("Command Found: " + CommandParser.getCommand());
            System.out.println("Args: ");
            CommandParser.getArgs().forEach(arg -> System.out.println(arg));
            System.out.println();
            System.out.println("noOfArgs: " + CommandParser.getNoOfArgs());
        }while(!commands.equals("-1"));
    }
}
