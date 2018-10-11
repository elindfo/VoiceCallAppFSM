package lab2b;

import lab2b.state.VoiceAppStateHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;

class ClientSocketListener extends Thread{

    private VoiceAppStateHandler handler;
    private Queue<SignalType> signalQueue;
    private Socket clientSocket;

    public ClientSocketListener(VoiceAppStateHandler handler, Socket clientSocket, Queue<SignalType> signalQueue){
        this.handler = handler;
        this.signalQueue = signalQueue;
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
                signalQueue.add(CommandParser.getCommand());
                System.out.println("SIGNAL QUEUE PEEK: " + signalQueue.peek());
                switch(signalQueue.peek()){
                    case INVITE: System.out.println("Nu ringer det"); break;
                    case ACK: signalQueue.poll(); handler.invokeAck(); break;
                    case OK: signalQueue.poll(); handler.invokeOk(); break;
                    case TRO: signalQueue.poll(); handler.invokeTro(); break;
                    case BYE: signalQueue.poll(); handler.invokeBye(); break;
                    case END_CALL: signalQueue.poll(); handler.invokeEndCall(); break;
                    case ERR: signalQueue.poll(); handler.invokeErr(CommandParser.getArgs().get(0)); running = false; break;
                    case BUSY: signalQueue.poll(); handler.invokeBusy(); running = false; break;
                }
                System.out.println("Current State: " + handler.getCurrentState());
            }while(running); //TODO Fixa här sen
        }catch (SocketTimeoutException e){
            System.out.println("SocketTimeoutException");
            handler.reset();
        }catch (IOException e) {
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
