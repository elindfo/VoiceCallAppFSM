package state;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MachineData {

    private Socket clientSocket;
    private PrintWriter out;

    public MachineData(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public PrintWriter getClientPrintWriter(){
        return out;
    }

    public Socket getClientSocket(){
        return clientSocket;
    }
}