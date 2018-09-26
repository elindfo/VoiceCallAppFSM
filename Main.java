import state.MachineData;
import state.VoiceAppState;
import state.VoiceAppStateHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

//TODO Fråga om vi får använda koden från Caller-klassen - Svar ja!
//TODO Fråga om enum för inkommande - Svar: Klass för översättning. Räkna med att UDP-port kan komma via invitet.

//TODO Hantera BUSY utanför tillståndsmaskinen. Så fort någon kontaktar så fråga om maskinen är BUSY. Metod busy() som returnerar true/false som är overridad på olika sätt beroende på vilket tillstånd man är

//Actual todos
//TODO Create two different starting modes, one for receiving a call and one for initiating a call
//TODO Implement basic socket functionality to check if protocol works (no call establishing here). Program menu for either answering call or initiating call needs to be made.
//TODO Error handling over socket. Check so that the client disconnects if wrong protocol message is sent and that everything resets.

public class Main {

    private static final String MENU =
                            "1 - Invite\n" +
                            "2 - Ack\n" +
                            "3 - Bye\n" +
                            "4 - Call\n" +
                            "5 - Tro\n" +
                            "6 - EndCall\n" +
                            "7 - Ok\n" +
                            "8 - IsBusy";

    public static void main(String[] args) {

        if(args.length != 2){
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);
        Scanner input = new Scanner(System.in);
        if(Integer.parseInt(args[1]) == 1){ //Server
            ServerSocket s = null;
            try{
                s = new ServerSocket(port);
                System.out.println("Waiting for client...");
                Socket cs = s.accept();
                System.out.println("Client connected...");
                VoiceAppStateHandler handler = new VoiceAppStateHandler(VoiceAppState.WAITING, new MachineData(cs));
                BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                String recievedMessage = "";
                do{
                    System.out.println("Current state: " + handler.getCurrentState());
                    recievedMessage = br.readLine();
                    CommandParser.load(recievedMessage);
                    System.out.println("Command received: " + CommandParser.getCommand());
                    switch(CommandParser.getCommand()){
                        case INVITE: handler.invokeInvite(); break;
                        case TRO: handler.invokeTro(); break;
                        case ACK: handler.invokeAck(); break;
                        case END_CALL: handler.invokeEndCall(); break;
                        case BYE: handler.invokeBye(); break;
                        case OK: handler.invokeOk(); break;
                        case NONE:
                            System.out.println("Invalid command received: " + CommandParser.getCommand());
                    }
                }while(!recievedMessage.equals("exit"));
            }catch(IOException e){
                System.err.println("Socket error");
            }
            finally{
                if(s == null){
                    try {
                        s.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else{ //Client
            Socket s = null;
            try{
                System.out.println("Connecting to server...");
                s = new Socket("localhost", port);
                System.out.println("Connected...");
                VoiceAppStateHandler handler = new VoiceAppStateHandler(VoiceAppState.WAITING, new MachineData(s));
                BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));

                handler.invokeCall();
                String receivedMessage = "";
                do{
                    System.out.println("Current state: " + handler.getCurrentState());
                    if(handler.getCurrentState() == VoiceAppState.IN_SESSION){
                        handler.invokeEndCall();
                    }
                    receivedMessage = br.readLine();
                    CommandParser.load(receivedMessage);
                    System.out.println("Command received: " + CommandParser.getCommand());
                    switch(CommandParser.getCommand()){
                        case INVITE: handler.invokeInvite(); break;
                        case TRO: handler.invokeTro(); break;
                        case ACK: handler.invokeAck(); break;
                        case END_CALL: handler.invokeEndCall(); break;
                        case BYE: handler.invokeBye(); break;
                        case OK: handler.invokeOk(); break;
                        case NONE: break;
                    }
                }while(handler.getCurrentState() != VoiceAppState.WAITING);
            }catch(IOException e){

            }
        }
    }
}
