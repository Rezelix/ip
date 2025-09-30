package clovis;

public class ClovisException{
    public static class ArgumentValueMissing extends RuntimeException{
        public ArgumentValueMissing() {
        }
    }
    public static class InvalidInput extends RuntimeException {
        public InvalidInput() {
        }
    }
    public static class TaskAlreadyMarkedCorrectly extends RuntimeException{
        public TaskAlreadyMarkedCorrectly() {
        }
    }
    public static class MissingArgument extends RuntimeException{
        public MissingArgument() {
        }
    }
    public static class NoActiveTasks extends RuntimeException{
        public NoActiveTasks() {
        }
    }
    public static class MissingDeadlineArgument extends RuntimeException{
        public MissingDeadlineArgument() {
        }
    }

    public static class MissingEventArguments extends RuntimeException{
        public MissingEventArguments() {
        }
    }

    public static class TargetIndexOutOfRange extends RuntimeException{
        public TargetIndexOutOfRange() {
        }
    }

    public static class DataDirCouldNotBeMade extends RuntimeException{
        public DataDirCouldNotBeMade() {
        }
    }

    public static class NotYetImplemented extends RuntimeException{
        public NotYetImplemented() {}
    }

    public static class KeywordNotFound extends RuntimeException{
        public KeywordNotFound() {}
    }
}
