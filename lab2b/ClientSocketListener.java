package lab2b;

import lab2b.state.VoiceAppStateHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class ClientSocketListener extends Thread{

    private VoiceAppStateHandler handler;
    private Queue<SignalType> signalQueue;
    private Socket clientSocket;
    private AtomicBoolean isFree;
    private boolean normalMode;
    private AtomicInteger testCase;

    public ClientSocketListener(VoiceAppStateHandler handler, Socket clientSocket, Queue<SignalType> signalQueue, AtomicBoolean isFree, boolean normalMode, AtomicInteger testCase){
        this.handler = handler;
        this.signalQueue = signalQueue;
        this.clientSocket = clientSocket;
        this.isFree = isFree;
        this.normalMode = normalMode;
        this.testCase = testCase;
    }

    @Override
    public void run(){
        if(normalMode){
            normalMode();
        }
        else{
            testMode();
        }
    }

    private void normalMode(){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String receivedMessage = "";
            boolean running = true;
            do{
                receivedMessage = in.readLine();
                System.out.println();
                System.out.println("Currently in queue: " + signalQueue);
                System.out.println("ClientSocketListener received: " + receivedMessage);
                if(receivedMessage == null){
                    System.out.println("received = null");
                    throw new IOException();
                }
                CommandParser.load(receivedMessage);
                signalQueue.add(CommandParser.getCommand());
                System.out.println("SIGNAL QUEUE PEEK: " + signalQueue.peek());
                System.out.println("Command: " + CommandParser.getCommand() + " args: " + CommandParser.getArgs());
                switch(signalQueue.peek()){
                    case INVITE: {
                        isFree.set(false);
                        if(handler.getMachineData().getRemotePort() <= 0){
                            handler.getMachineData().setRemotePort(Integer.parseInt(CommandParser.getArgs().get(0)));
                        }
                        System.out.println("Nu ringer det");
                        break;
                    }
                    case ACK: signalQueue.poll(); handler.invokeAck(); break;
                    case OK: signalQueue.poll(); handler.invokeOk(); break;
                    case TRO: {
                        signalQueue.poll();
                        handler.getMachineData().setRemotePort(Integer.parseInt(CommandParser.getArgs().get(0)));
                        handler.invokeTro();
                        break;
                    }
                    case BYE: signalQueue.poll(); handler.invokeBye(); break;
                    case END_CALL: signalQueue.poll(); handler.invokeEndCall(); break;
                    case ERR: signalQueue.clear(); handler.invokeErr(CommandParser.getArgs().get(0)); running = false; break;
                    case BUSY: signalQueue.poll(); handler.invokeBusy(); running = false; break;
                    case NONE: signalQueue.poll(); handler.invokeErr("Invalid PDU received"); running = false; break;
                }
                System.out.println("Current State: " + handler.getCurrentState());
            }while(running); //TODO Fixa här sen
        }catch (SocketTimeoutException e){
            System.out.println("SocketTimeoutException");
            try {
                handler.reset();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (IOException e) {
            try {
                signalQueue.clear();
                isFree.set(true);
                handler.invokeErr("Lost connection to client.");
                System.out.println(signalQueue);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
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

    private void testMode(){
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

                if(testCase.get() == 4){
                    CommandParser.load("HACKTHESYSTEM");
                }
                else{
                    CommandParser.load(receivedMessage);
                }
                signalQueue.add(CommandParser.getCommand());
                System.out.println("SIGNAL QUEUE PEEK: " + signalQueue.peek());
                System.out.println("Command: " + CommandParser.getCommand() + " args: " + CommandParser.getArgs());
                switch(signalQueue.peek()){
                    case INVITE: {
                        isFree.set(false);
                        if(handler.getMachineData().getRemotePort() <= 0){
                            handler.getMachineData().setRemotePort(Integer.parseInt(CommandParser.getArgs().get(0)));
                        }
                        System.out.println("Nu ringer det");
                        break;
                    }
                    case ACK: {
                        signalQueue.poll();
                        if(testCase.get() == 3) {
                            handler.invokeOk();
                        }
                        else{
                            handler.invokeAck();
                        }
                        break;
                    }
                    case OK: signalQueue.poll(); handler.invokeOk(); break;
                    case TRO: {
                        signalQueue.poll();
                        handler.getMachineData().setRemotePort(Integer.parseInt(CommandParser.getArgs().get(0)));
                        if(testCase.get() == 2){
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.invokeTro();
                        break;
                    }
                    case BYE: signalQueue.poll(); handler.invokeBye(); break;
                    case END_CALL: signalQueue.poll(); handler.invokeEndCall(); break;
                    case ERR: signalQueue.poll(); handler.invokeErr(CommandParser.getArgs().get(0)); running = false; break;
                    case BUSY: signalQueue.poll(); handler.invokeBusy(); running = false; break;
                    case NONE: signalQueue.poll(); handler.invokeErr("Invalid PDU received"); running = false; break;
                }
                System.out.println("Current State: " + handler.getCurrentState());
            }while(running); //TODO Fixa här sen
        }catch (SocketTimeoutException e){
            System.out.println("SocketTimeoutException");
            try {
                handler.reset();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }catch (IOException e) {
            try {
                signalQueue.clear();
                isFree.set(true);
                handler.invokeErr("Lost connection to client.");
                System.out.println(signalQueue);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
