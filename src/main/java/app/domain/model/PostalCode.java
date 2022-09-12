package app.domain.model;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class responsable for represents the postal code and respective validations
 */

//Based on https://howtodoinjava.com/java/regex/us-postal-zip-code-validation/
//and on email validation on AuthLib
public class PostalCode implements Serializable {

    private static final String POSTAL_CODE_REGEX = "\\d{1,4}((-)\\d{1,3})";

    private static final long serialVersionUID = -2156876057073056382L;

    private String number;

    /**
     * Constructs an instance of PostalCode
     *
     * @param number the number of Postal Code
     */
    public PostalCode(String number)
    {
        if (!this.isValid(number))
        {
            throw new IllegalArgumentException("Invalid postal code number.");
        } else
        {
            this.number = number;
        }
    }

    /**
     * Constructs an instance of PostalCode from another PostalCode instance
     *
     * @param postalCode the PostalCode
     */
    public PostalCode(PostalCode postalCode)
    {
        if (postalCode == null)
        {
            throw new IllegalArgumentException("PostalCode object cannot be null");
        }

        this.number = postalCode.number;

    }

    public String getNumber()
    {
        return number;
    }

    /**
     * Validates if the inputed postal code is valid:
     * <ul>
     *      <li>checks if  <strong>postal code</strong> has the pattern <strong> four digits - three digits</strong></li>
     *
     * @param number the postal code number
     * @return a boolean that indicates whether the postal code is of a valid pattern
     */
    private boolean isValid(String number)
    {
        Pattern pattern = Pattern.compile(POSTAL_CODE_REGEX);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

}
