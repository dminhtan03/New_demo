package exceptions;

public class DuplicateIdException extends Exception {

    public DuplicateIdException(String errorMessage) {
        super(errorMessage);
    }
}
