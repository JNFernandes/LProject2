package app.mappers.dto;

import app.domain.model.PostalCode;
import app.domain.shared.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SnsUserDTOTest {

    private String name;
    private String email;
    private Date birthDate;
    private SnsUserDTO snsUser;
    private AddressDTO address;
    private Long snsNumber;
    private Long phoneNumber;
    private String citizenCard;

    @BeforeEach
    public void setupObjects() throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.name = "User Name";
        this.email = "user@email.com";
        this.birthDate = df.parse("01-09-1986");
        this.snsNumber = 12345678L;
        this.phoneNumber = 921234567L;
        this.citizenCard = "00000000";
        this.address = new AddressDTO(
                "street",
                123,
                "1234-123",
                "city",
                "country");
        this.snsUser = new SnsUserDTO(
                this.name,
                "user@email.com",
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
        this.name = null;
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
        SnsUserDTO snsUser = this.snsUser;
        // Assert
        Assertions.assertNotNull(snsUser);
    }

    @Test
    public void createSnsUserWithNullGenderSuccessfully()
    {
        // Arrange + Act
        SnsUserDTO snsUser = new SnsUserDTO(
                this.name,
                this.email,
                null,
                this.birthDate,
                this.snsNumber,
                this.address,
                this.phoneNumber,
                this.citizenCard);
        // Assert
        Assertions.assertNotNull(snsUser);
    }


    @Test
    public void createSnsUserWithNullName()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUserDTO(
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
                    new SnsUserDTO(
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
                    new SnsUserDTO(
                            this.name,
                            null,
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
                    new SnsUserDTO(
                            this.name,
                            "",
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
                    new SnsUserDTO(
                            this.name,
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            -10L,
                            this.address,
                            this.phoneNumber,
                            this.citizenCard);
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
                    new SnsUserDTO(
                            this.name,
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            0L,
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
                    new SnsUserDTO(
                            this.name,
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            -10L,
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
                    new SnsUserDTO(
                            this.name,
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            0L,
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
                    new SnsUserDTO(
                            this.name,
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            "-10L");
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
                    new SnsUserDTO(
                            this.name,
                            this.email,
                            Gender.Female,
                            this.birthDate,
                            this.snsNumber,
                            this.address,
                            this.phoneNumber,
                            "0");
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
                    new SnsUserDTO(
                            this.name,
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
                    new SnsUserDTO(
                            this.name,
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