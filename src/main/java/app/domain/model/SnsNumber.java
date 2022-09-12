package app.domain.model;

import java.io.Serializable;

/**
 * Class responsable for represents the SNS number and respective validations
 */
public class SnsNumber implements Serializable {

    private static final long serialVersionUID = -6207064006308911119L;

    private long number;

    /**
     * Constructs an instance of SNS Number
     *
     * @param number SNS Number
     */
    public SnsNumber(Long number)
    {
        if (!this.isValid(number))
        {
            throw new IllegalArgumentException("Invalid Sns number.");
        } else
        {
            this.number = number;
        }
    }

    /**
     * Validates if the inputed SNS number is valid:
     * <ul>
     * <li>checks if  <strong>SNS number</strong> is null</li>
     * <li>checks if  <strong>SNS number</strong> is greater than zero</li>
     * </ul>
     *
     * @param number the SNS number
     * @return a boolean that indicates whether the sns number is valid
     */
    //Can't find an algorithm to validate SNS number
    private boolean isValid(Long number)
    {
        if (number == null)
        {
            throw new IllegalArgumentException("Invalid SNS number, it cannot be null.");
        }

        if (number <= 0L)
        {
            throw new IllegalArgumentException("Invalid SNS number, it cannot be equal or lower than zero.");
        }

        return true;
    }

    public long getNumber()
    {
        return number;
    }

}
