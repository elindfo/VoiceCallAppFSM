import state.VoiceAppState;
import state.VoiceAppStateHandler;
import sun.misc.Signal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

//TODO Skapa en kö till menyhanteraren. Svara finns i menyn, men får ingen effekt så länge det inte ligger någon invite i kön.

public class Main {

    public static final int SOCKET_TIMEOUT = 2000;

    public static void main(String[] args) {

        if(args.length != 1){
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        VoiceAppStateHandler handler = new VoiceAppStateHandler(VoiceAppState.WAITING);
        Queue<SignalType> signalQueue = new ArrayBlockingQueue<>(64);
        Socket currentClient = null;
        AtomicBoolean isFree = new AtomicBoolean(true);

        new ServerSocketListener(currentClient, handler, port, signalQueue, isFree).start();
        new MenuListener(currentClient, handler, signalQueue, isFree).start();
    }
}