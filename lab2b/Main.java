package lab2b;

import lab2b.state.VoiceAppState;
import lab2b.state.VoiceAppStateHandler;

import java.io.IOException;
import java.net.Socket;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static final int SOCKET_TIMEOUT = 10000;

    public static void main(String[] args) {

        if(args.length != 1){
            System.exit(0);
        }

        Scanner input = new Scanner(System.in);
        System.out.print("Pick a mode\n0 - Normal\n1 - Test\nInput: ");
        int choice = input.nextInt();
        boolean normalMode = choice == 0;

        int port = Integer.parseInt(args[0]);

        VoiceAppStateHandler handler = new VoiceAppStateHandler(VoiceAppState.WAITING);
        try {
            handler.getMachineData().setAudioUDPStream(new AudioStreamUDP());
        } catch (IOException e) {
            System.err.println("Unable to create AudioStreamUDP");
            System.exit(0);
        }
        Queue<SignalType> signalQueue = new ArrayBlockingQueue<>(64);
        Socket currentClient = null;
        AtomicBoolean isFree = new AtomicBoolean(true);
        AtomicInteger testCase = new AtomicInteger(1);

        new ServerSocketListener(currentClient, handler, port, signalQueue, isFree, normalMode, testCase).start();
        new MenuListener(currentClient, handler, signalQueue, isFree, normalMode, testCase).start();
    }
}