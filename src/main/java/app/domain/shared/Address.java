package app.domain.shared;

import app.domain.model.PostalCode;

import java.io.Serializable;

/**
 * Address of vaccination Center to be defined by the administrator.
 */
public class Address implements Serializable {

    // ATTRIBUTES
    private static final String DEFAULT_STREET = "ND";
    private static final int DEFAULT_DOORNUMBER = 0;
    private static final int DEFAULT_POSTALCODE = 0;
    private static final String DEFAULT_CITY = "N/D";
    private static final String DEFAULT_COUNTRY = "N/D";

    private String street;
    private int doorNumber;
    private PostalCode postalCode;
    private String city;
    private String country;

    /**
     * Default constructor for the address.
     */
    public Address()
    {
    }


    /**
     * Constructor for the address
     *
     * @param street
     * @param doorNumber
     * @param postalCode
     * @param city
     * @param country
     */

    public Address(String street,
                   int doorNumber,
                   PostalCode postalCode,
                   String city,
                   String country)
    {
        this.street = street.trim();
        this.doorNumber = doorNumber;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country; //faz sentido definir o pais tendo em conta que vai ser em portugal???
    }

    public Address(Address address)
    {
        if (address == null)
        {
            throw new IllegalArgumentException("Address object cannot be null");
        }
        this.street = address.street;
        this.doorNumber = address.doorNumber;
        this.postalCode = new PostalCode(address.postalCode);
        this.city = address.city;
        this.country = address.country;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public void setDoorNumber(int doorNumber)
    {
        this.doorNumber = doorNumber;
    }

    public void setPostalCode(PostalCode postalCode)
    {
        this.postalCode = postalCode;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public void setCountry(String country)
    {
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

    public PostalCode getPostalCode()
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
        return String.format("Address: %s %d, %s %s, %s", street, doorNumber, postalCode.getNumber(), city, country);
    }

}