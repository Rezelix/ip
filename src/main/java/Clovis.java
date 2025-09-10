import java.util.Scanner;

public class Clovis {
    //Constants
    static final String DIVIDER = "__________________________________________________________\n";
    static final int MAX_NUM_OF_TASKS = 100;
    static final int CHARNUM_OF_DATELINE = 8;
    static final int CHARNUM_OF_TODO = 4;
    //static final int CHARNUM_OF_EVENT = 5;

    public static Task[] tasks = new Task[MAX_NUM_OF_TASKS];

    public static void main(String[] args) {
        String logo = "  _____ _            _\n" +
                " / ____| |          (_)\n" +
                "| |    | | _____   ___ ___\n" +
                "| |    | |/ _ \\ \\ / / / __|\n" +
                "| |____| | (_) \\ V /| \\__ \\\n" +
                " \\_____|_|\\___/ \\_/ |_|___/";
        System.out.println("Hello from\n" + logo);
        System.out.println("What do you want from me this time?");
        printDivider();
        int taskIndex = 0;
        Scanner input = new Scanner(System.in);
        while (true) {
            String line = input.nextLine();
            String[] words = line.split(" ");
            words = trimWords(words);
            String cmdType = words[0];
            printDivider();
            switch (cmdType) {
            case "list":
                printTasks(tasks);
                break;
            case "bye":
                System.out.println("Bye. Don't come again!");
                taskIndex = 0;
//                while (true);
                System.exit(0);
                break;
            case "mark":
                int taskNumMark = Integer.parseInt(words[1]);
                // TODO Error Handling - cases for 0 and a number not spanning the total number of tasks
                tasks[taskNumMark - 1].setDone();
                break;
            case "unmark":
                int taskNumUnmark = Integer.parseInt(words[1]);
                // TODO Error Handling - cases for 0 and a number not spanning the total number of tasks
                tasks[taskNumUnmark - 1].resetDone();
                break;
            case "deadline":
                int dateIndex;
                for (dateIndex = 0; dateIndex < words.length; dateIndex += 1) {
                    if (words[dateIndex].startsWith("/by")) {
                        break;
                    }
                }
                if (dateIndex == words.length - 1) {
                    System.out.println("No deadline found! Insert another Task!");
                    break;
                }
                String subStrTask = line.substring(CHARNUM_OF_DATELINE + 1, line.indexOf(" /by"));
                String subStrDeadline = line.substring(line.indexOf("/by") + 4);
                tasks[taskIndex] = new Deadline(subStrTask, subStrDeadline);
                printAck(tasks[taskIndex].toString());
                printTotalInList(taskIndex + 1);
                taskIndex += 1;
                break;
            case "todo":
                tasks[taskIndex] = new Todo(line.substring(CHARNUM_OF_TODO + 1));
                printAck(tasks[taskIndex].toString());
                printTotalInList(taskIndex + 1);
                taskIndex += 1;
                break;
            case "event":
                int fromIndex = findParamIndex(words, "/from");
                int toIndex = findParamIndex(words, "/to");
                String subStrEvent = assembleStringFromArrayIndexes(words, 1, fromIndex);
//                for (String word : words) {
//                    System.out.println(word);
//                }
                String subStrFrom = assembleStringFromArrayIndexes(words, fromIndex + 1, toIndex);
                String subStrTo = assembleStringFromArrayIndexes(words, toIndex + 1);
//                String subStrEvent = line.substring(CHARNUM_OF_EVENT,line.indexOf(" /from"));
//                String subStrFrom = line.substring(line.indexOf("/from")+6, line.indexOf(" /to"));
//                String subStrTo = line.substring(line.indexOf("/to")+4);
                tasks[taskIndex] = new Event(subStrEvent, subStrFrom, subStrTo);
                printAck(tasks[taskIndex].toString());
                printTotalInList(taskIndex + 1);
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
        for (int i = 0; tasks[i] != null; i++) {
            System.out.println(i + 1 + "." + tasks[i].toString());
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

    public static String[] trimWords(String[] words) {
        String[] output = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            output[i] = words[i].trim();
        }
        return output;
    }

    public static int findParamIndex(String[] array, String keyword) {
        for (int i = 1; i < array.length; i++) {
            if (array[i].equals(keyword)) {
                return i;
            }
        }
        return -1; //TODO error handling - Param not found
    }

    public static String assembleStringFromArrayIndexes(String[] array, int startIndex, int endIndex) {
        String output = "";
        for (int i = startIndex; i < endIndex; i++) {
            output += array[i] + " ";
//            output = output.concat(array[i] + " ");
        }
        output = output.trim();
        return output;
    }

    public static String assembleStringFromArrayIndexes(String[] array, int startIndex) {
        String output = "";
        for (int i = startIndex; i < array.length; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

}
