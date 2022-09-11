package app.externalmodule;


public class ListLinesErrorsException extends ArrayIndexOutOfBoundsException{

    /**
     * methods to verify specific exeptions
     */

    public ListLinesErrorsException () {super("Fields of the file are not correct");}

    /**
     * methods to verify specific exeptions
     */

    public ListLinesErrorsException (String message) {super(message);}



}
