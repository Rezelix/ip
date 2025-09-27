package clovis;

import clovis.task.Deadline;
import clovis.task.Event;
import clovis.task.Todo;

public class Parser {
    public static String[] splitWords(String line, String regex) {
        return line.trim().split(regex);
    }

    public static int findParamIndex(String[] words, String keyword) throws ClovisException.ArgumentValueMissing {
        for (int i = 1; i < words.length; i++) {
            if (words[i].equals(keyword)) {
                return i;
            }
        }
        //TODO add keyword to show which argument was missing
        throw new ClovisException.ArgumentValueMissing();
    }

    public static String assembleStr(String[] array, int startIndex, int endIndex) {
        String output = "";
        for (int i = startIndex; i < endIndex; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    public static String assembleStr(String[] array, int startIndex) {
        String output = "";
        for (int i = startIndex; i < array.length; i++) {
            output += array[i] + " ";
        }
        output = output.trim();
        return output;
    }

    public static Todo parseTodo (String[] words) {
        return new Todo(assembleStr(words,1));
    }

    public static Deadline parseDeadline(String[] words) throws ClovisException.MissingDeadlineArgument {
        int dateIndex;
        try {
            dateIndex = findParamIndex(words, "/by");
        } catch (ClovisException.ArgumentValueMissing e) {
            throw new ClovisException.MissingDeadlineArgument();
        }
        String description = assembleStr(words,1,dateIndex);
        String deadlineTime = assembleStr(words,dateIndex+1);
        return new Deadline(description,deadlineTime);
    }

    public static Event parseEvent(String[] words) throws ClovisException.MissingEventArguments {
        int fromIndex;
        int toIndex;
        try {
            fromIndex = findParamIndex(words, "/from");
            toIndex = findParamIndex(words, "/to");
        } catch (ClovisException.ArgumentValueMissing e) {
            throw new ClovisException.MissingEventArguments();
        }
        String description = assembleStr(words, 1, fromIndex);
        String startTime = assembleStr(words, fromIndex + 1, toIndex);
        String endTime = assembleStr(words, toIndex + 1);
        return new Event(description,startTime,endTime);
    }

    public static int getTargetIndex(String[] words) {
        return Integer.parseInt(words[1]) - 1;
    }

    public static void checkForArgs(String[] words) throws ClovisException.MissingArgument {
        if (words.length == 1) {
            throw new ClovisException.MissingArgument();
        }
    }

}
