package app.domain.model;

import app.domain.shared.Address;
import app.domain.shared.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SnsUserTest {

    private Email email;
    private Date birthDate;
    private SnsUser snsUser;
    private Address address;
    private SnsNumber snsNumber;
    private PhoneNumber phoneNumber;
    private CitizenCard citizenCard;

    @BeforeEach
    public void setupObjects() throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.email = new Email("user@email.com");
        this.birthDate = df.parse("01-09-1986");
        this.snsNumber = new SnsNumber(12345678L);
        this.phoneNumber = new PhoneNumber(921234567L);
        this.citizenCard = new CitizenCard("00000000");
        this.address = new Address(
                "street",
                123,
                new PostalCode("1234-123"),
                "city",
                "country");
        this.snsUser = new SnsUser(
                "user@email.com",
                "User Name",
                this.email,
                Gender.Female,
                this.birthDate,
                this.snsNumber,
                this.address,
                this.phoneNumber,
                this.citizenCard);
    }

    @AfterEach
    public void dumpMemory()
    {
        this.email = null;
        this.birthDate = null;
        this.snsUser = null;
        this.address = null;
        this.snsNumber = null;
        this.citizenCard = null;
        this.phoneNumber = null;
    }

    @Test
    public void createSnsUserSuccessfully()
    {
        // Arrange + Act
        SnsUser snsUser = this.snsUser;
        // Assert
        Assertions.assertNotNull(snsUser);
    }

    @Test
    public void createSnsUserWithNullId()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            null,
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have an id as null/blank."
        );
    }

    @Test
    public void createSnsUserWithBlankId()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "",
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have an id as null/blank."
        );
    }

    @Test
    public void createSnsUserWithNullName()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            null,
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have a name as null/blank."
        );
    }

    @Test
    public void createSnsUserWithBlankName()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have a name as null/blank."
        );
    }

    @Test
    public void createSnsUserWithNullEmail()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            new Email(null),
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have an email as null/blank."
        );
    }

    @Test
    public void createSnsUserWithBlankEmail()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            new Email(""),
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have an email as null/blank."
        );
    }

    @Test
    public void createSnsUserWithNegativeSnsNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            new SnsNumber(-10L),
                            this.address,
                            this.phoneNumber,
                            new CitizenCard("13009877L"));
                },
                "User cannot have a SNS Number as null/blank."
        );
    }

    @Test
    public void createSnsUserWithZeroSnsNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            new SnsNumber(0L),
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have a SNS Number equal to zero."
        );
    }

    @Test
    public void createSnsUserWithNegativePhoneNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            new PhoneNumber((PhoneNumber)null),
                            this.citizenCard);
                },
                "User cannot have a Phone number as null/blank."
        );
    }

    @Test
    public void createSnsUserWithZeroPhoneNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            new PhoneNumber(0L),
                            this.citizenCard);
                },
                "User cannot have a Phone number equal to zero."
        );
    }

    @Test
    public void createSnsUserWithNegativeCitizenCardNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            new CitizenCard("-10L"));
                },
                "User cannot have a Citizen card number as null/blank."
        );
    }

    @Test
    public void createSnsUserWithZeroCitizenCardNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            new CitizenCard("0L"));
                },
                "User cannot have a Citizen card number equal to zero."
        );
    }

    @Test
    public void createSnsUserWithNullBirthdate()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            this.email,
                            Gender.Female,
                            null,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have a null birthdate"
        );
    }

    @Test
    public void createSnsUserWithNullAddress()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUser(
                            "user@email.com",
                            "User Name",
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            null,
                            this.phoneNumber,
                            this.citizenCard);
                },
                "User cannot have a null address"
        );
    }

}