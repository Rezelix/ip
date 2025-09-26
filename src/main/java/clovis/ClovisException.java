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
    public static class TaskAlreadyMarked extends RuntimeException{
        public TaskAlreadyMarked() {
        }
    }
    public static class TaskAlreadyUnmarked extends RuntimeException{
        public TaskAlreadyUnmarked() {
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

    public static class MissingTaskDescription extends RuntimeException{
        public MissingTaskDescription() {
        }
    }

    public static class TargetIndexOutOfRange extends RuntimeException{
        public TargetIndexOutOfRange() {
        }
    }
}
