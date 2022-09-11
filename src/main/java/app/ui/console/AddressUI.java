package app.ui.console;

import app.mappers.dto.AddressDTO;
import app.ui.console.utils.Utils;

public class AddressUI {

    public static AddressDTO readAddressFromConsole()
    {
        AddressDTO address = null;

        boolean success = false;
        do
        {
            try
            {
                System.out.println("\nPlease insert the user address below");

                String country = Utils.readLineFromConsole("Address country");
                String city = Utils.readLineFromConsole("Address city");
                String street = Utils.readLineFromConsole("Address street");
                int doorNumber = Utils.readIntegerFromConsole("Address door number");
                String postalCode = Utils.readLineFromConsole("Address postal code (XXXX-YYY)");

                address = new AddressDTO(street, doorNumber, postalCode, city, country);
                success = true;
            } catch (IllegalArgumentException exception)
            {
                System.out.println(exception.getMessage());
            }
        } while (!success);

        return address;
    }

}
