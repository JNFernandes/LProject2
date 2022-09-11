package app.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PostalCodeTest {

    @Test
    public void createPostalCodeSuccessfully()
    {
        // Arrange + Act
        PostalCode postalCode = new PostalCode("4425-704");
        // Assert
        Assertions.assertNotNull(postalCode);
    }

    @Test
    public void createPostalCodeInvalid1()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PostalCode("50000-704");
                },
                "Invalid postal code number."
        );
    }

    @Test
    public void createPostalCodeInvalid2()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PostalCode("5000-7000");
                },
                "Invalid postal code number."
        );
    }

    @Test
    public void createPostalCodeInvalid3()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PostalCode("5000");
                },
                "Invalid postal code number."
        );
    }

    @Test
    public void createPostalCodeInvalid4()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PostalCode("300");
                },
                "Invalid postal code number."
        );
    }

}
