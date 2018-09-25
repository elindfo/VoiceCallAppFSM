import state.VoiceAppState;
import state.VoiceAppStateHandler;

import java.util.Scanner;

public class Main {

    private static final String MENU =
            "1 - Invite\n" +
                    "2 - Ack\n" +
                    "3 - Bye";

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        VoiceAppStateHandler handler = new VoiceAppStateHandler(VoiceAppState.READY);

        System.out.println("Current state: " + handler.getCurrentState());
        int choice = -1;
        do{
            System.out.println(MENU);
            System.out.print("Input: ");
            choice = input.nextInt();
            switch (choice){
                case 1: handler.invokeInvite(); break;
                case 2: handler.invokeSendAck(); break;
                case 3: handler.invokeEndCall(); break;
            }
            System.out.println("Current state: " + handler.getCurrentState());
        }while(choice != 0);
    }
}
