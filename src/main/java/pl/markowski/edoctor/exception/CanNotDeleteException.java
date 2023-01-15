package pl.markowski.edoctor.exception;

public class CanNotDeleteException extends RuntimeException{

    public CanNotDeleteException(String message) {
        super(message);
    }
}
