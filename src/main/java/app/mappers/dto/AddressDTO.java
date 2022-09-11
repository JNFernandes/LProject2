package app.mappers.dto;

import app.ui.console.utils.AttributesValidationUtils;

public class AddressDTO {

    private String street;
    private int doorNumber;
    private String postalCode;
    private String city;
    private String country;


    public AddressDTO(String street,
                      Integer doorNumber,
                      String postalCode,
                      String city,
                      String country)
    {
        AttributesValidationUtils.validateNonNullAttribute("street", street);
        AttributesValidationUtils.validateNonNullAttribute("door number", doorNumber);
        AttributesValidationUtils.validateNonNullAttribute("postal code", postalCode);
        AttributesValidationUtils.validateNonNullAttribute("city", city);
        AttributesValidationUtils.validateNonNullAttribute("country", country);

        this.street = street;
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }


    public String getStreet()
    {
        return street;
    }

    public int getDoorNumber()
    {
        return doorNumber;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    @Override
    public String toString()
    {
        return "Address:\n" +
                "\t- Street: " + street + '\n' +
                "\t- Door number: " + doorNumber + '\n' +
                "\t- Postal code: " + postalCode + '\n' +
                "\t- City: " + city + '\n' +
                "\t- Country: " + country + '\n';
    }
}
