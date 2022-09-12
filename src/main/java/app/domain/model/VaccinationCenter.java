package app.domain.model;

import app.domain.interfaces.CenterPerformanceCalculation;
import app.domain.shared.Address;
import app.domain.shared.Constants;
import app.externalmodule.BenchmarkAlgorithmAdapter;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

/**
 * A new vaccination center to be defined by the administrator.
 * Super class
 */
public class VaccinationCenter implements Serializable {

    private static final long serialVersionUID = 6887869424315281309L;

    private String name;
    private PhoneNumber phoneNumber;
    private Address address;
    private Email emailAddress;
    private PhoneNumber faxNumber;
    private String websiteAddress;
    private String openingHours;
    private String closingHours;
    private int slotDuration;
    private int maxNumVaccines;
    private Employee centerCoordinator;

    /**
     * Default constructor for the new vaccination center.
     */
    public VaccinationCenter()
    {

    }

    /**
     * Constructor for the new vaccination center
     * @param name VC name
     * @param phoneNumber VC phone number
     * @param address VC address
     * @param emailAddress VC email
     * @param vaccinationCenterFaxNumber VC fax number
     * @param websiteAddress VC website address
     * @param openingHours VC opening hours, eg: 8am
     * @param closingHours VC closing hours, eg: 8pm
     * @param slotDuration VC vaccination slot duration, in minutes
     * @param maxNumVaccines max number of vaccines administrated per slot
     * @param centerCoordinator VC coordinator, must be registered as an employee w/ role CenterCoordinator
     */
    public VaccinationCenter(String name,
                             PhoneNumber phoneNumber,
                             Address address,
                             Email emailAddress,
                             PhoneNumber vaccinationCenterFaxNumber,
                             String websiteAddress,
                             String openingHours,
                             String closingHours,
                             int slotDuration,
                             int maxNumVaccines,
                             Employee centerCoordinator)
    {

        this.validateNonNullAttribute("name", name);
        this.validateNonNullAttribute("Phone Number", phoneNumber);
        this.validateNonNullAttribute("Address", address);
        this.validateNonNullAttribute("EmailAddress", emailAddress);
        this.validateNonNullAttribute("Fax Number", vaccinationCenterFaxNumber);
        this.validateNonNullAttribute("Website Address", websiteAddress);
        this.validateNonNullAttribute("Opening Hours", openingHours);
        this.validateNonNullAttribute("Closing Hours", closingHours);
        this.validateZeroAttribute(0, slotDuration);
        this.validateZeroAttribute(0, maxNumVaccines);
        this.validateNonNullAttribute("Center Coordinator", centerCoordinator);

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.emailAddress = emailAddress;
        this.faxNumber = vaccinationCenterFaxNumber;
        this.websiteAddress = websiteAddress;
        this.openingHours = openingHours;
        this.closingHours = closingHours;
        this.slotDuration = slotDuration;
        this.maxNumVaccines = maxNumVaccines;
        this.centerCoordinator = centerCoordinator;
    }

    public VaccinationCenter(String name)
    {
        this.name = name;
    }

    public void setVaccinationCenterName(String name)
    {
        this.name = name;
    }

    public void setVaccinationCenterPhoneNumber(PhoneNumber phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setEmailAddress(Email emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public void setVaccinationCenterFaxNumber(PhoneNumber faxNumber)
    {
        this.faxNumber = faxNumber;
    }

    public void setWebsiteAddress(String websiteAddress)
    {
        this.websiteAddress = websiteAddress;
    }

    public void setSlotDuration(int slotDuration)
    {
        this.slotDuration = slotDuration;
    }

    public void setMaxNumVaccines(int maxNumVaccines)
    {
        this.maxNumVaccines = maxNumVaccines;
    }

    public void setOpeningHours(String openingHours)
    {
        this.openingHours = openingHours;
    }

    public void setClosingHours(String closingHours)
    {
        this.closingHours = closingHours;
    }

    public void setCenterCoordinator(Employee centerCoordinator)
    {
        this.centerCoordinator = centerCoordinator;
    }

    public String getVaccinationCenterName()
    {
        return name;
    }

    public Address getAddress()
    {
        return address;
    }

    public PhoneNumber getVaccinationCenterPhoneNumber()
    {
        return phoneNumber;
    }

    public Email getVaccinationCenterEmailAddress()
    {
        return emailAddress;
    }

    public PhoneNumber getVaccinationCenterFaxNumber()
    {
        return faxNumber;
    }

    public String getWebsiteAddress()
    {
        return websiteAddress;
    }

    public int getSlotDuration()
    {
        return slotDuration;
    }

    public int getMaxNumVaccines()
    {
        return maxNumVaccines;
    }

    public String getOpeningHours()
    {
        return openingHours;
    }

    public String getClosingHours()
    {
        return closingHours;
    }

    public Employee getCenterCoordinator()
    {
        return centerCoordinator;
    }


    public boolean hasEmail(String emailAddress)
    {
        return this.emailAddress.getEmail().equals(emailAddress);
    }

    public boolean hasName(String name)
    {
        return this.name.equals(name);
    }

    private void validateNonNullAttribute(String attribute, Object attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("User cannot have a null " + attribute + ".");
        }
    }

    private void validateZeroAttribute(int attribute, int attributeValue)
    {

        if (attributeValue == 0)
        {
            throw new IllegalArgumentException("User cannot have a 0" + attribute + ".");
        }

    }


    static public void validateNonNullAttribute(String attribute, String attributeValue)
    {
        if (attributeValue == null)
        {
            throw new IllegalArgumentException("User cannot have the attribute " + attribute + " as null");
        }

        if (attributeValue.isBlank())
        {
            throw new IllegalArgumentException("User cannot have the attribute " + attribute + " as blank.");
        }
    }

    /**
     * method to obtain the working period of a center in minutes
     * @return working period
     */

    public long obtainWorkingPeriodMinutes() {
        DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

        LocalTime timeOpen = LocalTime.parse(getOpeningHours(), formatter12);
        LocalTime timeClose = LocalTime.parse(getClosingHours(), formatter12);

        Duration duration = Duration.between(timeOpen, timeClose);

        return duration.toMinutes();
    }

    /**
     * method to get the class name that is in config properties for analysis of the algorithm
     * @return class name
     */

    public static String getClassNameForPerformanceAnalysis() {
        Properties props = new Properties();
        String className;
        try {
            File file = new File(Constants.PARAMS_FILENAME);
            FileInputStream in = new FileInputStream(file);
            props.load(in);
            className = props.getProperty(Constants.PARAMS_PERFORMACE_ANALYSIS);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to the get the specified class from config properties file");
            className = "app.externalmodule.BruteForceAlgorithmAdapter";
            return className;
        }
        return className;
    }

    /**
     * method that by reflexion obtains the class defined in config properties and goes to the respective adapter
     * @param timeResolution
     * @param day
     * @param listArrivals
     * @param listLeavings
     */
    public void getAdapterForPerformanceAnalysis(int timeResolution, LocalDate day, List<LocalDateTime> listArrivals, List<LocalDateTime> listLeavings )
    {
        int workingPeriod = (int) obtainWorkingPeriodMinutes();
        try
        {
            String className = getClassNameForPerformanceAnalysis();
            Class<?> reflectionClass = Class.forName(className);
            CenterPerformanceCalculation instance = (CenterPerformanceCalculation) reflectionClass.getDeclaredConstructor().newInstance();
            instance.calculatePerformance(timeResolution, day, workingPeriod, listArrivals, listLeavings);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException |
                 InstantiationException e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get the right algorithm. By deafault the algorithm to run will be BruteForce Algorithm");
            BenchmarkAlgorithmAdapter benchmarkAlgorithmAdapter = new BenchmarkAlgorithmAdapter();
            benchmarkAlgorithmAdapter.calculatePerformance(timeResolution, day, workingPeriod, listArrivals, listLeavings);
        }
    }


    @Override
    public String toString() {
        return String.format("The vaccination center name is %s, it opens at %s and closes at %s",name, openingHours, closingHours);
    }


    private void writeObject(java.io.ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(this.name);
        stream.writeObject(this.phoneNumber);
        stream.writeObject(this.address);
        stream.writeObject(this.emailAddress.getEmail());
        stream.writeObject(this.faxNumber);
        stream.writeObject(this.websiteAddress);
        stream.writeObject(this.openingHours);
        stream.writeObject(this.closingHours);
        stream.writeObject(this.slotDuration);
        stream.writeObject(this.maxNumVaccines);
        stream.writeObject(this.centerCoordinator);
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException
    {
        this.name = (String) stream.readObject();
        this.phoneNumber = (PhoneNumber) stream.readObject();
        this.address = (Address) stream.readObject();
        this.emailAddress = new Email((String) stream.readObject());
        this.faxNumber = (PhoneNumber) stream.readObject();
        this.websiteAddress = (String) stream.readObject();
        this.openingHours = (String) stream.readObject();
        this.closingHours = (String) stream.readObject();
        this.slotDuration = (int) stream.readObject();
        this.maxNumVaccines = (int) stream.readObject();
        this.centerCoordinator = (Employee) stream.readObject();
    }

}
