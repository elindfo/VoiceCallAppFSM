import state.VoiceAppState;
import state.VoiceAppStateHandler;

import java.util.Scanner;

//TODO Fråga om vi får använda koden från Caller-klassen
//TODO Fråga om vi ska ha en eller flera inSession-klasser och om samtalsinitieringen är lika för båda sidor
//TODO Fråga om det kanske räcker med en Ringing-klass istället för Ringing och Calling
//TODO Fråga om enum för inkommande kommandon

public class Main {

    private static final String MENU =
                    "1 - Invite\n" +
                    "2 - Ack\n" +
                    "3 - Bye\n" +
                    "4 - Call\n" +
                    "5 - Tro";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        VoiceAppStateHandler handler = new VoiceAppStateHandler(VoiceAppState.WAITING);

        System.out.println("Current state: " + handler.getCurrentState());
        int choice = -1;
        do{
            System.out.println(MENU);
            System.out.print("Input: ");
            choice = input.nextInt();
            switch (choice){
                case 1: handler.invokeInvite(); break;
                case 2: handler.invokeAck(); break;
                case 3: handler.invokeBye(); break;
                case 4: handler.invokeCall(); break;
                case 5: handler.invokeTro(); break;
            }
            System.out.println("Current state: " + handler.getCurrentState());
        }while(choice != 0);
    }
}
