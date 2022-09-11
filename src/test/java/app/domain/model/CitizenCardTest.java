package app.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CitizenCardTest {

    @Test
    public void createCitizenCardSuccessfully()
    {
        // Arrange + Act
        CitizenCard citizenCard = new CitizenCard("00000000");
        // Assert
        Assertions.assertNotNull(citizenCard);
    }

    @Test
    public void createCitizenCardInvalidNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CitizenCard("123456780ZZ4");
                },
                "Invalid Citizen card number."
        );
    }

    @Test
    public void createCitizenCardNullNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CitizenCard((CitizenCard) null);
                },
                "Invalid Citizen card number."
        );
    }

    @Test
    public void createCitizenCardInvalidLength()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CitizenCard("000000000ZZ41");
                },
                "Invalid Citizen card number."
        );
    }

    @Test
    public void createCitizenCardInvalidCharacter()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CitizenCard("000000000ÇZ4");
                },
                "Invalid Citizen card number."
        );
    }

}
