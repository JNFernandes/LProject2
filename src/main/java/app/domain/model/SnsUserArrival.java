package app.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SnsUserArrival implements Comparable<SnsUserArrival>, Serializable {

    private static final long serialVersionUID = 5305880317312418180L;

    private LocalDateTime arrivalDate;
    private SnsNumber snsUserNumber;
    private VaccinationSchedule vaccineSchedule;

    /**
     * Constructs an instance of SnsUser Arrival
     *
     * @param arrivalDate       Arrival DateTime of SNS User(required)
     * @param snsUserNumber         SNS Number of the SNS User (required,unique)
     * @param vaccineSchedule   SNS User Scheduling
     */
    public SnsUserArrival(
            LocalDateTime arrivalDate,
            SnsNumber snsUserNumber,
            VaccinationSchedule vaccineSchedule
    )
    {
        this.validateNonNullAttribute("Arrival Date", arrivalDate);
        this.validateNonNullAttribute("SNS Number", snsUserNumber);
        this.validateNonNullAttribute("Vaccine Schedule", vaccineSchedule);


        this.arrivalDate = arrivalDate;
        this.snsUserNumber = snsUserNumber;
        this.vaccineSchedule = vaccineSchedule;
    }

    /**
     * Default Constructor
     */
    public SnsUserArrival() {

    }

    /**
     * Returns the arrivalDate of the SNS User
     *
     * @return arrivalDate
     */
    public LocalDateTime getArrivalDate()
    {
        return arrivalDate;
    }

    public int getArrivalDayDate()
    {
        return arrivalDate.getDayOfMonth();
    }


    public SnsNumber getSnsUserNumber()
    {
        return snsUserNumber;
    }


    /**
     * Returns the address of the SNS User
     *
     * @return address
     */
    public VaccinationSchedule getVaccineSchedule()
    {
        return vaccineSchedule;
    }

    /**
     * Verifies if a String attribute is not null
     *
     * @param attribute      attribute being validated
     * @param attributeValue attribute value
     */
    private void validateNonNullAttribute(String attribute, Object attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("Sns User Arrival cannot have a null " + attribute + ".");
        }
    }

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
            throw new IllegalArgumentException("Sns User Arrival cannot have the attribute " + attribute + " as null");
        }

        if (attributeValue.isBlank())
        {
            throw new IllegalArgumentException("Sns User Arrival cannot have the attribute " + attribute + " as blank.");
        }
    }

    @Override
    public int compareTo(SnsUserArrival arrival)
    {
        return arrivalDate.compareTo(arrival.getArrivalDate());
    }

}

