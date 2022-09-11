package app.externalmodule;

public class InvalidTypeFileException extends IllegalArgumentException{

    /**
     * methods to verify specific exeptions
     */

    public InvalidTypeFileException() {super("An error has occured");}

    /**
     * methods to verify specific exeptions
     */

    public InvalidTypeFileException(String message) {super(message);}
}
