package lab2b;

import lab2b.state.VoiceAppState;
import lab2b.state.VoiceAppStateHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class MenuListener extends Thread{

    private static final String MENU =
            "1 - Call\n" +
                    "2 - Answer\n" +
                    "3 - Decline/End Call";

    private VoiceAppStateHandler handler;
    private Queue<SignalType> signalQueue;
    private AtomicBoolean isFree;
    private Socket currentClient;

    public MenuListener(Socket currentClient, VoiceAppStateHandler handler, Queue<SignalType> signalQueue, AtomicBoolean isFree){
        this.handler = handler;
        this.signalQueue = signalQueue;
        this.currentClient = currentClient;
        this.isFree = isFree;
    }

    @Override
    public void run(){
        Scanner input = new Scanner(System.in);
        int data = 0;
        System.out.println(MENU);
        do{
            System.out.println(handler.getCurrentState());
            data = input.nextInt();
            switch(data){
                case 1: {
                    //Boolean som visar om man håller på att starta ett samtal
                    if(signalQueue.isEmpty() && isFree.get() && !handler.invokeIsBusy()){
                        isFree.set(false);
                        System.out.print("ip: ");
                        input.nextLine();
                        String ip = input.nextLine();
                        System.out.print("port: ");
                        int port = input.nextInt();
                        Socket clientSocket = null;
                        try{
                            clientSocket = new Socket(ip, port);
                            //clientSocket.setSoTimeout(Main.SOCKET_TIMEOUT);
                            currentClient = clientSocket;
                            handler.getMachineData().setClientSocket(currentClient);
                            handler.invokeCall();
                            new ClientSocketListener(handler, currentClient, signalQueue).start();
                        }catch(SocketTimeoutException e){
                            System.err.println("Socket Timeout");
                        }catch(IOException e){
                            System.err.println("Unable to connect to ip: " + ip + ", port: " + port);
                        }finally{
                            isFree.set(true);
                        }
                    }
                    else{
                        System.out.println("You are already calling or there's an incoming call");
                    }
                    break;
                }
                case 2: {
                    //Answer
                    if(signalQueue.peek() == SignalType.INVITE){
                        signalQueue.poll();
                        try {
                            handler.invokeInvite(currentClient);
                        } catch (IOException e) {
                            System.err.println("Unable to answer");
                        }
                    }
                    else{
                        System.out.println("No one's calling");
                    }
                    break;
                }
                case 3: {
                    if(signalQueue.peek() == SignalType.INVITE){
                        signalQueue.poll();
                        handler.getMachineData().getClientPrintWriter().println("BUSY");
                        break;
                    }
                    else if(handler.invokeIsBusy()){
                        handler.invokeEndCall();
                    }

                }
            }
        }while(data != 0);
    }
}

