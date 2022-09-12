package app.domain.shared;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Based on https://mkyong.com/java/java-password-generator-example
public class PasswordGenerator {

    private static final int PASSWORD_LENGTH = 7;
    private static final String DIGIT = "0123456789";
    private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
    private static final String PASSWORD_ALLOW = CHAR_LOWERCASE + CHAR_UPPERCASE + DIGIT;

    private static final SecureRandom random = new SecureRandom();

    /**
     * Generates password holding <strong>seven</strong> alphanumeric characters including:
     * <ul>
     *  <li> <strong> three capital letters</strong> </li>
     *  <li> <strong> two digits</strong> </li>
     * </ul>
     *
     * @return a String
     */
    public String generatePassword()
    {
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);

        // at least 3 chars (uppercase)
        String strLowerCase = generateRandomString(CHAR_UPPERCASE, 3);
        password.append(strLowerCase);

        // at least 2 digits
        String strDigit = generateRandomString(DIGIT, 2);
        password.append(strDigit);

        // remaining, just random
        String strOther = generateRandomString(PASSWORD_ALLOW, PASSWORD_LENGTH - 5);
        password.append(strOther);

        return shuffleString(password.toString());
    }

    /**
     * Generates password with a given number of characters
     *
     * @param input a String
     * @param size  the number of characters
     * @return a String
     */
    // generate a random char[], based on `input`
    private static String generateRandomString(String input, int size)
    {
        if (input == null || input.isBlank())
        {
            throw new IllegalArgumentException("Invalid input.");
        }

        if (size < 1)
        {
            throw new IllegalArgumentException("Invalid size.");
        }

        StringBuilder result = new StringBuilder(size);
        for (int i = 0; i < size; i++)
        {
            // produce a random order
            int index = random.nextInt(input.length());
            result.append(input.charAt(index));
        }

        return result.toString();
    }

    /**
     * Shuffles the characters of a String
     *
     * @param input a String
     * @return a String
     */
    // for final password, make it more random
    public static String shuffleString(String input)
    {
        List<String> result = Arrays.asList(input.split(""));
        Collections.shuffle(result);

        return String.join("", result);
    }

}
