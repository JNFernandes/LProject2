package app.ui.console.utils;

/**
 * Class to handle simple validations of UI inputed attributes
 */
public class AttributesValidationUtils {

    /**
     * Verifies if a String attribute is not null and not blank
     *
     * @param attribute      attribute being validated
     * @param attributeValue attribute value
     */
    static public void validateNonNullAttribute(String attribute, String attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("Cannot have the attribute " + attribute + " as null");
        }

        if (attributeValue.isBlank())
        {
            throw new IllegalArgumentException("Cannot have the attribute " + attribute + " as blank.");
        }
    }

    /**
     * Verifies if an Integer attribute is not null and greater than zero
     *
     * @param attribute      attribute being validated
     * @param attributeValue attribute value
     */
    static public void validateNonNullAttribute(String attribute, Integer attributeValue)
    {
        validateNonNullAttribute(attribute, Long.valueOf(attributeValue));
    }

    /**
     * Verifies if a Long attribute is not null and greater than zero
     *
     * @param attribute      attribute being validated
     * @param attributeValue attribute value
     */
    static public void validateNonNullAttribute(String attribute, Long attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("Cannot have a null " + attribute + ".");
        }
        if (attributeValue < 0)
        {
            throw new IllegalArgumentException("Cannot have a negative " + attribute + ".");
        }
        if (attributeValue == 0)
        {
            throw new IllegalArgumentException("Cannot have the attribute " + attribute + " equal to zero.");
        }
    }

    /**
     * Verifies if an Object attribute is not null
     *
     * @param attribute      attribute being validated
     * @param attributeValue attribute value
     */
    static public void validateNonNullAttribute(String attribute, Object attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("Cannot have a null " + attribute + ".");
        }
    }

}
