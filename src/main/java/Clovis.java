import java.util.Scanner;

public class Clovis {
    //Constants
    static final String DIVIDER = "__________________________________________________________\n";
    static final int MAX_NUM_OF_TASKS = 100;
    static final int CHARNUM_OF_DATELINE = 8;
    static final int CHARNUM_OF_TODO = 4;
    static final int CHARNUM_OF_BY = 3;
    //static final int CHARNUM_OF_EVENT = 5;

    public static Task[] tasks = new Task[MAX_NUM_OF_TASKS];
    //public static ClovisException uncheckedException = new ClovisException();

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
                System.exit(0);
                break;
            case "mark":
                try {
                    int taskNumMark = markEval(words, taskIndex);
                    tasks[taskNumMark - 1].setDone();
                } catch (ClovisException.InvalidInput e) {
                    System.out.println("Invalid input! It shouldn't be 0 or span outside of the active tasks!");
                    break;
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("Enter the task number after 'mark' (e.g. mark 1)");
                    break;
                } catch (ClovisException.HumanError e) {
                    System.out.println("Task " + taskIndex + " was already marked done!");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! It should be a number!");
                }
                break;
            case "unmark":
                try {
                    int taskNumUnmark = unmarkEval(words, taskIndex);
                    tasks[taskNumUnmark - 1].resetDone();
                } catch (ClovisException.InvalidInput e) {
                    System.out.println("Invalid input! It shouldn't be 0 or span outside of the active tasks!");
                    break;
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("Enter the task number after 'unmark' (e.g. unmark 1)");
                    break;
                } catch (ClovisException.HumanError e) {
                    System.out.println("Task " + taskIndex + " wasn't done yet!");
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input! It should be a number!");
                }
                break;
            case "deadline":
                try {
                    int dateIndex = deadlineEval(words,line,taskIndex);
                    String subStrTask = assembleStringFromArrayIndexes(words,1,dateIndex);
                    String subStrDeadline = assembleStringFromArrayIndexes(words,dateIndex+1);
                    tasks[taskIndex] = new Deadline(subStrTask, subStrDeadline);
                    printAck(tasks[taskIndex].toString());
                    printTotalInList(taskIndex + 1);
                    taskIndex += 1;
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("Missing Arguments, either no description or no deadline");
                    break;
                }
                break;
            case "todo":
                try {
                    todoEval(words);
                } catch (ClovisException.ArgumentValueMissing e) {
                    System.out.println("You are missing your task description!");
                    break;
                }
                tasks[taskIndex] = new Todo(assembleStringFromArrayIndexes(words,1));
                printAck(tasks[taskIndex].toString());
                printTotalInList(taskIndex + 1);
                taskIndex += 1;
                break;
            case "event":
                //TODO: Throw and Catch Exception
                if (words.length == 1) {
                    System.out.println("Your event task has no description!");
                    break;
                }
                int fromIndex = findParamIndex(words, "/from");
                int toIndex = findParamIndex(words, "/to");
                String subStrEvent = assembleStringFromArrayIndexes(words, 1, fromIndex);
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
                //TODO: Throw and Catch Exception
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

    public static int markEval (String[] words, int taskIndex) {
        if (words.length == 1) {
            throw new ClovisException.ArgumentValueMissing();
        }
        int taskNumMark = Integer.parseInt(words[1]);
        if (taskNumMark == 0 || taskNumMark >= taskIndex + 1) {
            throw new ClovisException.InvalidInput();
        }
        if (tasks[taskNumMark - 1].isDone()) {
            throw new ClovisException.HumanError();
        }
        return taskNumMark;
    }

    public static int unmarkEval (String[] words, int taskIndex) {
        if (words.length == 1) {
            throw new ClovisException.ArgumentValueMissing();
        }
        int taskNumUnMark = Integer.parseInt(words[1]);
        if (taskNumUnMark == 0 || taskNumUnMark >= taskIndex + 1) {
            throw new ClovisException.InvalidInput();
        }
        if (!tasks[taskNumUnMark - 1].isDone()) {
            throw new ClovisException.HumanError();
        }
        return taskNumUnMark;
    }

    public static int deadlineEval (String[] words, String line,  int taskIndex) {
        //TODO: Throw and Catch Exception
        if (words.length == 1) {
            throw new ClovisException.ArgumentValueMissing();
        }
        int dateIndex;
        for (dateIndex = 0; dateIndex < words.length; dateIndex += 1) {
            if (words[dateIndex].startsWith("/by")) {
                break;
            }
        }
        if (dateIndex == words.length) {
            throw new ClovisException.ArgumentValueMissing();
        }
        return dateIndex;
    }

    public static void todoEval (String[] words) {
        if (words.length == 1) {
            throw new ClovisException.ArgumentValueMissing();
        }
    }
}
