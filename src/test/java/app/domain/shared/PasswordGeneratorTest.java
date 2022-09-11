package app.domain.shared;

import app.domain.shared.PasswordGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordGeneratorTest {

    @Test
    public void generatePassword()
    {
        // Arrange + Act
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword();
        // Assert
        int numberOfDigits = this.countDigitsInString(password);
        int numberOfUppercaseLetters = this.countUppercaseLetters(password);

        System.out.println("Generated password: " + password);

        Assertions.assertTrue(numberOfDigits >= 2);
        Assertions.assertTrue(numberOfUppercaseLetters >= 3);
        Assertions.assertEquals(7, password.length());
    }

    @Test
    public void generateAnotherPassword()
    {
        // Arrange + Act
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        String password = passwordGenerator.generatePassword();
        // Assert
        int numberOfDigits = this.countDigitsInString(password);
        int numberOfUppercaseLetters = this.countUppercaseLetters(password);

        System.out.println("Generated password: " + password);

        Assertions.assertTrue(numberOfDigits >= 2);
        Assertions.assertTrue(numberOfUppercaseLetters >= 3);
        Assertions.assertEquals(7, password.length());
    }

    private int countDigitsInString(String password)
    {
        int count = 0;
        for (int i = 0; i < password.length(); i++)
        {
            if (Character.isDigit(password.charAt(i)))
                count++;

        }

        return count;
    }

    private int countUppercaseLetters(String password)
    {
        int count = 0;
        for (int i = 0; i < password.length(); i++)
        {
            if (Character.isUpperCase(password.charAt(i)))
                count++;

        }

        return count;
    }

}