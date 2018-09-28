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

public class Main {

    private static final String MENU =
                            "1 - Call\n" +
                            "2 - Answer\n" +
                            "3 - End Call";

    public static void main(String[] args) {

        if(args.length != 1){
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        VoiceAppStateHandler handler = new VoiceAppStateHandler(VoiceAppState.WAITING);
        new ServerSocketListener(handler, port).start();
        handleInput(handler);
    }

    private static void handleInput(VoiceAppStateHandler handler){
        Scanner input = new Scanner(System.in);
        int data = 0;
        System.out.println(MENU);
        do{
            System.out.println(handler.getCurrentState());
            data = input.nextInt();
            switch(data){
                case 1: {
                    System.out.print("ip: ");
                    input.nextLine();
                    String ip = input.nextLine();
                    System.out.print("port: ");
                    int port = input.nextInt();
                    Socket clientSocket = null;
                    try{
                        clientSocket = new Socket(ip, port);
                        handler.invokeCall(clientSocket);
                        new ClientSocketListener(handler, clientSocket).start();
                    }catch(IOException e){
                        System.err.println("Unable to connect to ip: " + ip + ", port: " + port);
                    }
                    break;
                }
                case 3: handler.invokeEndCall(); break;
                case 4: handler.invokeAck();
            }
        }while(data != 0);
    }
}

class ServerSocketListener extends Thread{

    private VoiceAppStateHandler handler;
    private int port;

    public ServerSocketListener(VoiceAppStateHandler handler, int port){
        this.handler = handler;
        this.port = port;
    }

    @Override
    public void run(){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            do{
                clientSocket = serverSocket.accept();
                if(handler.invokeIsBusy()){
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                    pw.println("BUSY");
                    clientSocket.close();
                }
                else{
                    new ClientSocketListener(handler, clientSocket).start();
                }

            }while(true);
        }catch(IOException e){

        }finally{
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.err.println("Unable to close serverSocket");
                }
            }

            if(clientSocket != null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Unable to close clientSocket");
                }
            }
        }
    }
}

class ClientSocketListener extends Thread{

    private VoiceAppStateHandler handler;
    private Socket clientSocket;

    public ClientSocketListener(VoiceAppStateHandler handler, Socket clientSocket){
        this.handler = handler;
        this.clientSocket = clientSocket;
    }

    @Override
    public void run(){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String receivedMessage = "";
            boolean running = true;
            do{
                receivedMessage = in.readLine();
                System.out.println("ClientSocketListener received: " + receivedMessage);
                if(receivedMessage == null){
                    System.out.println("received = null");
                    throw new IOException();
                }
                CommandParser.load(receivedMessage);
                switch(CommandParser.getCommand()){
                    case INVITE: handler.invokeInvite(clientSocket); break;
                    case ACK: handler.invokeAck(); break;
                    case OK: handler.invokeOk(); break;
                    case TRO: handler.invokeTro(); break;
                    case BYE: handler.invokeBye(); break;
                    case END_CALL: handler.invokeEndCall(); break;
                    case ERR: handler.invokeErr(CommandParser.getArgs().get(0)); running = false; break;
                    case BUSY: handler.invokeBusy(); running = false; break;
                }
                System.out.println("Current State: " + handler.getCurrentState());
            }while(running); //TODO Fixa här sen
        } catch (IOException e) {
            e.printStackTrace();
            handler.invokeErr("Lost connection to client.");
        } finally{
            if(in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    System.err.println("Unable to close BufferedReader");
                }
            }
            if(clientSocket != null){
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Unable to close clientSocket");
                }
            }
        }
    }
}

