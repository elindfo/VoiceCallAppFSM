import state.VoiceAppState;
import state.VoiceAppStateHandler;

import java.util.Scanner;

//TODO Fråga om vi får använda koden från Caller-klassen - Svar ja!
//TODO Fråga om enum för inkommande - Svar: Klass för översättning. Räkna med att UDP-port kan komma via invitet.

//TODO Hantera BUSY utanför tillståndsmaskinen. Så fort någon kontaktar så fråga om maskinen är BUSY. Metod busy() som returnerar true/false som är overridad på olika sätt beroende på vilket tillstånd man är

//Actual todos
//TODO Create two different starting modes, one for receiving a call and one for initiating a call
//TODO Implement basic socket functionality to check if protocol works (no call establishing here). Program menu for either answering call or initiating call needs to be made.
//TODO Error handling over socket. Check so that the client disconnects if wrong protocol message is sent and that everything resets.
//TODO Implement method for parsing messages received from socket

public class Main {

    private static final String MENU =
                            "1 - Invite\n" +
                            "2 - Ack\n" +
                            "3 - Bye\n" +
                            "4 - Call\n" +
                            "5 - Tro\n" +
                            "6 - EndCall\n" +
                            "7 - Ok\n" +
                            "8 - IsBusy";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        VoiceAppStateHandler handler = new VoiceAppStateHandler(VoiceAppState.WAITING);

        System.out.println("Current state: " + handler.getCurrentState());
        int choice = -1;
        do{
            System.out.println(MENU);
            System.out.print("Input: ");
            choice = input.nextInt();
            System.out.println();
            switch (choice){
                case 1: handler.invokeInvite(); break;
                case 2: handler.invokeAck(); break;
                case 3: handler.invokeBye(); break;
                case 4: handler.invokeCall(); break;
                case 5: handler.invokeTro(); break;
                case 6: handler.invokeEndCall(); break;
                case 7: handler.invokeOk(); break;
                case 8: handler.invokeIsBusy(); break;
            }
            System.out.println();
            System.out.println("Current state: " + handler.getCurrentState());
        }while(choice != 0);
    }
}
