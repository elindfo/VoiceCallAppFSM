package lab2b.state;

import lab2b.AudioStreamUDP;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MachineData {

    private Socket clientSocket;
    private AudioStreamUDP audioUDPStream;
    private int remotePort;
    private PrintWriter out;

    public MachineData(){
    }

    public PrintWriter getClientPrintWriter(){
        return out;
    }

    public Socket getClientSocket(){
        return clientSocket;
    }

    public void setClientSocket(Socket clientSocket) throws IOException{
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void setRemotePort(int port){
        remotePort = port;
    }

    public int getRemotePort(){
        return remotePort;
    }

    public AudioStreamUDP getAudioUDPStream(){
        return audioUDPStream;
    }

    public void setAudioUDPStream(AudioStreamUDP audioUDPStream){
        this.audioUDPStream = audioUDPStream;
    }

    public void reset(){
        if(out != null){
            out.close();
        }
        if(clientSocket != null){
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Unable to close clientSocket");
            }
        }
        remotePort = 0;
    }
}