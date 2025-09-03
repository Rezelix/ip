import java.util.Scanner;
public class Clovis {
    //Constants
    static final String DIVIDER = "__________________________________________________________\n";
    static final int MAX_NUM_OF_TASKS = 100;
    public static Task[] tasks =  new Task[MAX_NUM_OF_TASKS];

    public static void main(String[] args) {
        String logo = "  _____ _            _     \n" +
                " / ____| |          (_)    \n" +
                "| |    | | _____   ___ ___ \n" +
                "| |    | |/ _ \\ \\ / / / __|\n" +
                "| |____| | (_) \\ V /| \\__ \\\n" +
                " \\_____|_|\\___/ \\_/ |_|___/";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do you want from me this time?");
        printDivider();
        int taskIndex = 0;
        while (true) {
            Scanner inputComm = new Scanner(System.in);
            String line = inputComm.nextLine();
            String[] words = line.split(" ");
            String cmdWord = words[0];
            printDivider();
            switch (cmdWord) {
            case "list":
                printTasks(tasks);
                break;
            case "bye":
                System.out.println("Bye. Don't come again!");
                taskIndex = 0;
                System.exit(0);
                break;
            case "mark":
                int taskNumMark = Integer.parseInt(words[1]);
                tasks[taskNumMark].setDone();
                break;
            case "unmark":
                int taskNumUnmark = Integer.parseInt(words[1]);
                tasks[taskNumUnmark].resetDone();
                break;
            default:
                tasks[taskIndex] = new Task(line);
                taskIndex += 1;
                System.out.println("added: " + line);
                break;
            }
            printDivider();
        }
    }

    public static void printTasks(Task[] tasks) {
        for (int i = 0; tasks[i] != null ; i++) {
            System.out.println(i+1 + "." + (tasks[i].isDone() ? "[X] " : "[] ") + tasks[i].getName());
        }
    }
    
    public static void printDivider() {
        System.out.print(DIVIDER);
    }

}
