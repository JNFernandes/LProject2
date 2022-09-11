package app.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PhoneNumberTest {

    @Test
    public void createPhoneNumberSuccessfully1()
    {
        // Arrange + Act
        PhoneNumber phoneNumber = new PhoneNumber(921234567L);
        // Assert
        Assertions.assertNotNull(phoneNumber);
    }

    @Test
    public void createPhoneNumberSuccessfully2()
    {
        // Arrange + Act
        PhoneNumber phoneNumber = new PhoneNumber(221234567L);
        // Assert
        Assertions.assertNotNull(phoneNumber);
    }

//    @Test
//    public void createPhoneNumberInvalidNumber()
//    {
//        Assertions.assertThrows(
//                IllegalArgumentException.class,
//                () -> {
//                    new PhoneNumber(121234567L);
//                },
//                "Invalid phone number."
//        );
//    }

    @Test
    public void createPhoneNumberNullNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PhoneNumber((PhoneNumber) null);
                },
                "Invalid phone number."
        );
    }

    @Test
    public void createPhoneNumberInvalidLength()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new PhoneNumber(9212345678L);
                },
                "Invalid phone number."
        );
    }

}
