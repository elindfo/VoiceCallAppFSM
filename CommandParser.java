import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandParser {

    public enum CommandType{
        INVITE, TRO, ACK, OK, END_CALL, BYE, ERR, BUSY, NONE
    }

    private static String input = "";
    private static CommandType command;
    private static ArrayList<String> args;

    private CommandParser(){}

    public static void load(String s)  throws IllegalArgumentException{
        if(s.isEmpty()){
            throw new IllegalArgumentException("Empty string");
        }
        input = s.toUpperCase();
        command = CommandType.NONE;
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
            case "INVITE": command = CommandType.INVITE; break;
            case "TRO": command = CommandType.TRO; break;
            case "ACK": command = CommandType.ACK; break;
            case "OK": command = CommandType.OK; break;
            case "END_CALL": command = CommandType.END_CALL; break;
            case "BYE": command = CommandType.BYE; break;
            case "ERR": command = CommandType.ERR; break;
            case "BUSY": command = CommandType.BUSY; break;
            default: command = CommandType.NONE; return;
        }

        //Get arguments
        for(int i = 1; i < splitInput.length; i++){
            args.add(splitInput[i]);
        }
    }

    public static int getNoOfArgs(){
        return args.size();
    }

    public static CommandType getCommand(){
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
