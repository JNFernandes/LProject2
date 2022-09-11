package app.externalmodule;

import java.util.InputMismatchException;

public class HeaderNotMatchException extends InputMismatchException {

    /**
     * methods to verify specific exeptions
     */

    public HeaderNotMatchException() {super("an error occured");}

    /**
     * methods to verify specific exeptions
     */

    public HeaderNotMatchException(String message) {super(message);}
}
