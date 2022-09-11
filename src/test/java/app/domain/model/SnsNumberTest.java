package app.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SnsNumberTest {

    @Test
    public void createSnsNumberSuccessfully()
    {
        // Arrange + Act
        SnsNumber phoneNumber = new SnsNumber(921234567L);
        // Assert
        Assertions.assertNotNull(phoneNumber);
    }

    @Test
    public void createSnsNumberNullNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsNumber(null);
                },
                "Invalid SNS number."
        );
    }

    @Test
    public void createSnsNumberZeroNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsNumber(0L);
                },
                "Invalid SNS number."
        );
    }

    @Test
    public void createSnsNumberNegativeNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsNumber(-10L);
                },
                "Invalid SNS number."
        );
    }

}
