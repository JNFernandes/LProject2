package app.domain.model;

import app.domain.shared.Address;
import app.domain.shared.Gender;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Class responsible for defining/characterizing the SNS User
 */
public class SnsUser implements Serializable {

    private static final long serialVersionUID = 2101421056376644143L; //https://blog.algaworks.com/serialversionuid/
    private String id;
    private String name;
    private Email email;
    private Gender gender;
    private Date birthdate;
    private SnsNumber snsNumber;
    private Address address;
    private PhoneNumber phoneNumber;
    private CitizenCard citizenCard;

    public SnsUser()
    {
    }

    /**
     * Constructs an instance of SNS User
     *
     * @param id          identifier of the SNS User - adopted the email for this purpose (required,unique)
     * @param name        name of the SNS User (required)
     * @param email       email of the SNS User (required,unique)
     * @param gender      gender of the SNS User (optional)
     * @param birthdate   birthdate of the SNS User (required)
     * @param snsNumber   SNS Number of the SNS User (required,unique)
     * @param address     address of the SNS User (required)
     * @param phoneNumber phone number of the SNS User (required,unique)
     * @param citizenCard citizen card of the SNS User (required,unique)
     */
    public SnsUser(String id,
                   String name,
                   Email email,
                   Gender gender,
                   Date birthdate,
                   SnsNumber snsNumber,
                   Address address,
                   PhoneNumber phoneNumber,
                   CitizenCard citizenCard)
    {
        validateNonNullAttribute("id", id);
        validateNonNullAttribute("name", name);
        this.validateNonNullAttribute("email", email);
        this.validateNonNullAttribute("SNS number", snsNumber);
        this.validateNonNullAttribute("birthdate", birthdate);
        this.validateNonNullAttribute("address", address);
        this.validateNonNullAttribute("phone number", phoneNumber);
        this.validateNonNullAttribute("citizen card number", citizenCard);
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthdate = birthdate;
        this.snsNumber = snsNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.citizenCard = citizenCard;
    }

    /**
     * Returns the Id of the SNS User
     *
     * @return id
     */
    public String getId()
    {
        return this.id;
    }

    /**
     * Returns the name of the SNS User
     *
     * @return name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the email of the SNS User
     *
     * @return email
     */
    public Email getEmail()
    {
        return email;
    }

    /**
     * Returns the gender of the SNS User
     *
     * @return gender
     */
    public Gender getGender()
    {
        return gender;
    }

    /**
     * Returns the birthdate of the SNS User
     *
     * @return birthdate
     */
    public Date getBirthdate()
    {
        return birthdate;
    }

    /**
     * Returns the SNS number of the SNS User
     *
     * @return snsNumber
     */
    public SnsNumber getSnsNumber()
    {
        return snsNumber;
    }

    /**
     * Returns the address of the SNS User
     *
     * @return address
     */
    public Address getAddress()
    {
        return address;
    }

    /**
     * Returns the phone number of the SNS User
     *
     * @return phoneNumber
     */
    public PhoneNumber getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Returns the citizen card of the SNS User
     *
     * @return citizenCard
     */
    public CitizenCard getCitizenCard()
    {
        return citizenCard;
    }

    /**
     * Verifies if a String attribute is not null
     *
     * @param attribute      attribute being validated
     * @param attributeValue attribute value
     */
    private void validateNonNullAttribute(String attribute, Object attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("User cannot have a null " + attribute + ".");
        }
    }

    /**
     * Verifies if a String attribute is not null and not blank
     *
     * @param attribute      attribute being validated
     * @param attributeValue attribute value
     */
    static public void validateNonNullAttribute(String attribute, String attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("User cannot have the attribute " + attribute + " as null");
        }
        if (attributeValue.isBlank())
        {
            throw new IllegalArgumentException("User cannot have the attribute " + attribute + " as blank.");
        }
    }

    public int getAge()
    {
        Calendar birthdateCalendar = Calendar.getInstance();
        birthdateCalendar.setTime(this.birthdate);
        Calendar currentDayCalender = Calendar.getInstance();
        currentDayCalender.setTime(new Date());
        return currentDayCalender.get(Calendar.YEAR) - birthdateCalendar.get(Calendar.YEAR);
    }

    //estes métodos são necessários sempre que seja precisa lógica adicional para serializar ou deserealizar os dados,
    // neste caso o Email não permite implement serializable porque pertence a uma biblioteca externa (VER SLIDES TEORICA SERIALIZAÇÃO)
    private void writeObject(java.io.ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(this.id);
        stream.writeObject(this.name);
        stream.writeObject(this.email.getEmail());
        stream.writeObject(this.gender);
        stream.writeObject(this.birthdate);
        stream.writeObject(this.snsNumber);
        stream.writeObject(this.address);
        stream.writeObject(this.phoneNumber);
        stream.writeObject(this.citizenCard);
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException
    {
        this.id = (String) stream.readObject();
        this.name = (String) stream.readObject();
        this.email = new Email((String) stream.readObject());
        this.gender = (Gender) stream.readObject();
        this.birthdate = (Date) stream.readObject();
        this.snsNumber = (SnsNumber) stream.readObject();
        this.address = (Address) stream.readObject();
        this.phoneNumber = (PhoneNumber) stream.readObject();
        this.citizenCard = (CitizenCard) stream.readObject();
    }

}