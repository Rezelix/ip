public class ClovisException{
    public static class ArgumentValueMissing extends RuntimeException{
        public ArgumentValueMissing(String message) {
            super(message);
        }
    }

    public static class InvalidInput extends RuntimeException {
        public InvalidInput(String message) {
            super(message);
        }
    }
}
