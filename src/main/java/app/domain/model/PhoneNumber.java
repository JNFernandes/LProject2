package app.domain.model;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.Serializable;

/**
 * Class responsable for represents the phone number and respective validations
 */
public class PhoneNumber implements Serializable {

    private static final int COUNTRY_CODE = 351;

    private static final long serialVersionUID = -6297245713144892484L;

    private long number;

    /**
     * Constructs an instance of Phone Number
     *
     * @param number phone number
     */
    public PhoneNumber(Long number)
    {
        if (!this.isValid(number))
        {
            throw new IllegalArgumentException("Invalid phone number format.");
        } else
        {
            this.number = number;
        }
    }

    /**
     * Constructs an instance of Phone Number from another Phone Number instance
     *
     * @param phoneNumber a phone number
     */
    public PhoneNumber(PhoneNumber phoneNumber)
    {
        if (phoneNumber == null)
        {
            throw new IllegalArgumentException("PhoneNumber object cannot be null");
        }

        this.number = phoneNumber.number;

    }

    public long getNumber()
    {
        return number;
    }

    /**
     * Validates if the inputed phone number is valid:
     * <ul>
     * <li>checks if  <strong>phone number</strong> is null</li>
     * <li>checks if  <strong>phone number</strong> is greater than zero</li>
     * </ul>
     *
     * @param number the phone number
     * @return a boolean that indicates whether the phone number is of a valid pattern
     */
//    private boolean isValid(Long number)
//    {
//        if (number == null)
//        {
//            throw new IllegalArgumentException("Invalid phone number, it cannot be null.");
//        }
//
//        if (number <= 0L)
//        {
//            throw new IllegalArgumentException("Invalid phone number, it cannot be equal or lower than zero.");
//        }
//
//        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
//
//        Phonenumber.PhoneNumber phoneNumber = new Phonenumber.PhoneNumber();
//        phoneNumber.setCountryCode(COUNTRY_CODE);
//        phoneNumber.setNationalNumber(number);
//
//        return phoneNumberUtil.isValidNumber(phoneNumber);
//    }

    private boolean isValid(Long number)
    {
        if (number == null)
        {
            throw new IllegalArgumentException("Invalid phone number, it cannot be null.");
        }

        if (number >= 999999999 || number<10000000)
        {
            throw new IllegalArgumentException("Invalid phone number, it cannot have less than 9 digits");
        }

        return true;
    }

}
