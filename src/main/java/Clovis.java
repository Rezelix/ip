import java.util.Scanner;
public class Clovis {
    //Constants
    static final String DIVIDER = "__________________________________________________________\n";
    static final int MAX_NUM_OF_TASKS = 100;
    static final int CHARNUM_OF_DATELINE = 8;

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
            Scanner input = new Scanner(System.in);
            String line = input.nextLine();
            String[] words = line.split(" ");
            String cmdType = words[0];
            printDivider();
            switch (cmdType) {
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
                tasks[taskNumMark - 1].setDone();
                break;
            case "unmark":
                int taskNumUnmark = Integer.parseInt(words[1]);
                tasks[taskNumUnmark].resetDone();
                break;
            case "deadline":
                int dateIndex;
                for (dateIndex = 0; dateIndex < words.length; dateIndex += 1) {
                    if (words[dateIndex].startsWith("/by")) {
                        break;
                    }
                }
                if (dateIndex == words.length - 1 ) {
                    System.out.println("No deadline found! Insert another Task!");
                    break;
                }
                String subStrTask = line.substring(CHARNUM_OF_DATELINE,line.indexOf(" /by"));
                String subStrDeadline = line.substring(line.indexOf("/by") + 4);
                tasks[taskIndex] = new Deadline(subStrTask, subStrDeadline);
                printAck(line);
                printTotalInList(taskIndex+1);
                taskIndex += 1;
                break;
            default:
                System.out.println("Don't give me nonsense! Re-enter!");
                break;
            }
            printDivider();
        }
    }

    public static void printTasks(Task[] tasks) {
//        for (int i = 0; tasks[i] != null ; i++) {
//            System.out.println(i+1 + "." + (tasks[i].isDone() ? "[X] " : "[] ") + tasks[i].getName());
//        }
        for (int i = 0; tasks[i] != null ; i++) {
            System.out.println(i+1 + "." + tasks[i].toString());
        }
    }
    
    public static void printDivider() {
        System.out.print(DIVIDER);
    }

    public static void printAck(String line) {
        System.out.println("added: " + line);
    }

    public static void printTotalInList(int numOfTasks) {
        System.out.println("You currently have " + numOfTasks + " tasks in your list");
    }

}
