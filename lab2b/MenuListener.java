package lab2b;

import lab2b.state.VoiceAppStateHandler;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class MenuListener extends Thread{

    private static final String MENU =
                    "1 - Call\n" +
                    "2 - Answer\n" +
                    "3 - Decline/End Call";
    private static final String TEST_MENU =
                    "1 - Call\n" +
                    "2 - Answer\n" +
                    "3 - Decline/End Call\n" +
                    "4 - [LOAD TEST 1] Send double INVITE (caller)\n" +
                    "5 - [LOAD TEST 2] Timeout on ACK (caller)\n" +
                    "6 - [LOAD TEST 3] Receive OK instead of ACK (callee)\n" +
                    "7 - [LOAD TEST 4] Receive invalid PDU (callee)";

    private VoiceAppStateHandler handler;
    private Queue<SignalType> signalQueue;
    private AtomicBoolean isFree;
    private Socket currentClient;
    private boolean normalMode;
    private AtomicInteger testCase;

    public MenuListener(Socket currentClient, VoiceAppStateHandler handler, Queue<SignalType> signalQueue, AtomicBoolean isFree, boolean normalMode, AtomicInteger testCase){
        this.handler = handler;
        this.signalQueue = signalQueue;
        this.currentClient = currentClient;
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
        Scanner input = new Scanner(System.in);
        int data = 0;
        System.out.println(MENU);
        do{
            System.out.println();
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
                            clientSocket.setSoTimeout(Main.SOCKET_TIMEOUT);
                            currentClient = clientSocket;
                            handler.getMachineData().setClientSocket(currentClient);
                            handler.invokeCall();
                            new ClientSocketListener(handler, currentClient, signalQueue, isFree, normalMode, testCase).start();
                        }catch(SocketTimeoutException e){
                            System.err.println("Socket timeout");
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
                            handler.invokeInvite();
                            isFree.set(true);
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
                        isFree.set(true);
                        break;
                    }
                    else if(handler.invokeIsBusy()){
                        try {
                            handler.invokeEndCall();
                        } catch (SocketException e) {
                            try {
                                handler.reset();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }while(data != 0);
    }

    private void testMode(){
        Scanner input = new Scanner(System.in);
        int data = 0;
        System.out.println(TEST_MENU);

        do{
            System.out.println("Test Mode - Current case: " + testCase.get());
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
                            clientSocket.setSoTimeout(Main.SOCKET_TIMEOUT);
                            currentClient = clientSocket;
                            handler.getMachineData().setClientSocket(currentClient);
                            handler.invokeCall();
                            if(testCase.get() == 1){
                                handler.invokeCall();
                            }
                            new ClientSocketListener(handler, currentClient, signalQueue, isFree, normalMode, testCase).start();
                        }catch(SocketTimeoutException e){
                            System.err.println("Socket timeout");
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
                            handler.invokeInvite();
                            isFree.set(true);
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
                        isFree.set(true);
                        break;
                    }
                    else if(handler.invokeIsBusy()){
                        try {
                            handler.invokeEndCall();
                        } catch (SocketException e) {
                            try {
                                handler.reset();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                case 4: testCase.set(1); break;
                case 5: testCase.set(2); break;
                case 6: testCase.set(3); break;
                case 7: testCase.set(4); break;
            }
        }while(data != 0);
    }
}

