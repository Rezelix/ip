import java.io.Serial;
import java.util.Scanner;
public class Clovis {
    //Constants
    static final String DIVIDER = "__________________________________________________________\n";


    public static void main(String[] args) {
        String logo = "  _____ _            _     \n" +
                " / ____| |          (_)    \n" +
                "| |    | | _____   ___ ___ \n" +
                "| |    | |/ _ \\ \\ / / / __|\n" +
                "| |____| | (_) \\ V /| \\__ \\\n" +
                " \\_____|_|\\___/ \\_/ |_|___/";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do you want from me this time?");
        System.out.print(DIVIDER);
        Scanner inputComm = new Scanner(System.in);
        String line = inputComm.nextLine();
        switch (line) {
        case "list":
            System.out.print(DIVIDER);
            System.out.println("list");
            System.out.print(DIVIDER);
            break;
        case "bye":
            System.out.print(DIVIDER);
            System.out.println("Bye. Don't come again!");
            System.out.print(DIVIDER);
            break;
        default:
            System.out.print(DIVIDER);
            System.out.println(inputComm);
            System.out.print(DIVIDER);
            break;
        }
    }
}
