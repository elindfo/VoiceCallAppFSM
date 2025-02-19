package lab2b;

import lab2b.state.VoiceAppStateHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

class ServerSocketListener extends Thread{

    private VoiceAppStateHandler handler;
    private int port;
    private AtomicBoolean isFree;
    private Socket currentClient;
    private Queue<SignalType> signalQueue;
    private boolean normalMode;
    private AtomicInteger testCase;

    public ServerSocketListener(Socket currentClient, VoiceAppStateHandler handler, int port, Queue<SignalType> signalQueue, AtomicBoolean isFree, boolean normalMode, AtomicInteger testCase){
        this.handler = handler;
        this.port = port;
        this.isFree = isFree;
        this.currentClient = currentClient;
        this.signalQueue = signalQueue;
        this.normalMode = normalMode;
        this.testCase = testCase;
    }

    @Override
    public void run(){
        ServerSocket serverSocket = null;
        Socket clientSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            do{
                clientSocket = serverSocket.accept();
                if(handler.invokeIsBusy() || !(isFree.get())){
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);
                    pw.println("BUSY");
                    clientSocket.close();
                }
                else{
                    currentClient = clientSocket;
                    currentClient.setSoTimeout(Main.SOCKET_TIMEOUT);
                    handler.getMachineData().setClientSocket(currentClient);
                    new ClientSocketListener(handler, currentClient, signalQueue, isFree, normalMode, testCase).start();
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
