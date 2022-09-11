package app.externalmodule;

public class InvalidDelimiterCsvFileException extends IllegalArgumentException{

    /**
     * methods to verify specific exeptions
     */

    public InvalidDelimiterCsvFileException() {super("An error has occured");}

    /**
     * methods to verify specific exeptions
     */

    public InvalidDelimiterCsvFileException(String message) {super(message);}

}
