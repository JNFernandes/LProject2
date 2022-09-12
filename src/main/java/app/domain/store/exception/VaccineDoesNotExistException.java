package app.domain.store.exception;

public class VaccineDoesNotExistException extends RuntimeException
{
    public VaccineDoesNotExistException(String errorMessage)
    {
        super(errorMessage);
    }

}
