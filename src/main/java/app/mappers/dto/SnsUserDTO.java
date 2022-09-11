package app.mappers.dto;

import app.domain.shared.Gender;
import app.ui.console.utils.AttributesValidationUtils;

import java.util.Calendar;
import java.util.Date;

public class SnsUserDTO {

    private String name;
    private String email;
    private Gender gender;
    private Date birthdate;
    private Long snsNumber;
    private AddressDTO address;
    private Long phoneNumber;
    private String citizenCardNumber;

    /**
     * Constructs an instance of SNS User DTO (Data Transfer Object)
     *
     * @param name              name of the SNS User (required)
     * @param email             email of the SNS User (required,unique)
     * @param gender            gender of the SNS User (optional)
     * @param birthdate         birthdate of the SNS User (required)
     * @param snsNumber         SNS Number of the SNS User (required,unique)
     * @param address           address of the SNS User (required)
     * @param phoneNumber       phone number of the SNS User (required,unique)
     * @param citizenCardNumber citizen card of the SNS User (required,unique)
     */

    public SnsUserDTO(String name,
                      String email,
                      Gender gender,
                      Date birthdate,
                      long snsNumber,
                      AddressDTO address,
                      long phoneNumber,
                      String citizenCardNumber)
    {
        AttributesValidationUtils.validateNonNullAttribute("name", name);
        AttributesValidationUtils.validateNonNullAttribute("email", email);
        AttributesValidationUtils.validateNonNullAttribute("SNS number", snsNumber);
        AttributesValidationUtils.validateNonNullAttribute("birth date", birthdate);
        AttributesValidationUtils.validateNonNullAttribute("address", address);
        AttributesValidationUtils.validateNonNullAttribute("phone number", phoneNumber);

        this.validateNonNullAndLengthCitizenCardNumber(citizenCardNumber);

        this.name = name;
        this.email = email;
        this.gender = gender;
        this.birthdate = birthdate;
        this.snsNumber = snsNumber;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.citizenCardNumber = citizenCardNumber;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public Gender getGender()
    {
        return gender;
    }

    public Date getBirthdate()
    {
        return birthdate;
    }

    public long getSnsNumber()
    {
        return snsNumber;
    }

    public AddressDTO getAddress()
    {
        return address;
    }

    public long getPhoneNumber()
    {
        return phoneNumber;
    }

    public String getCitizenCardNumber()
    {
        return citizenCardNumber;
    }

    @Override
    public String toString()
    {
        return "SNS user information:\n" +
                "- Name: " + name + '\n' +
                "- Email: " + email + '\n' +
                "- Gender: " + gender + '\n' +
                "- Birthdate: " + birthdate + '\n' +
                "- SNS number: " + snsNumber + '\n' +
                "- Phone number: " + phoneNumber + '\n' +
                "- Citizen card number: " + citizenCardNumber + '\n' +
                "- " + address + '\n';
    }

    public int getAge()
    {
        Calendar birthdateCalendar = Calendar.getInstance();
        birthdateCalendar.setTime(this.birthdate);
        Calendar currentDayCalender = Calendar.getInstance();
        currentDayCalender.setTime(new Date());
        return currentDayCalender.get(Calendar.YEAR) - birthdateCalendar.get(Calendar.YEAR);
    }

    /**
     * Validates if SNS User Number is null and if it has the correct length
     * @param number citizen card of the SNS User (required,unique)
     */
    private void validateNonNullAndLengthCitizenCardNumber(String number)
    {
        if (number == null)
        {
            throw new IllegalArgumentException("Citizen card number cannot be null.");
        }
        if (number.length() != 8)
        {
            throw new IllegalArgumentException("Citizen card number size invalid.");
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || (this.getClass() != o.getClass()) )
        {
            return false;
        }
        SnsUserDTO snsUserDTO = (SnsUserDTO) o;
        return this.getEmail().equalsIgnoreCase(snsUserDTO.getEmail());
    }
}
