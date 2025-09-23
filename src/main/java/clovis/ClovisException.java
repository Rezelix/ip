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
    public static class HumanError extends RuntimeException{
        public HumanError() {
        }
    }
}
