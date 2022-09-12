package app.domain.model;

import app.domain.shared.Address;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;

/**
 * Type of Vaccination center, Health Care Vaccination Center, to be defined and created by the admin
 */
public class HealthcareVaccinationCenter extends VaccinationCenter {

    private String ars;
    private String ages;
    private ArrayList<VaccineType> vaccineTypes;

    /**
     * Default constructor for the HCVC
     */
   public HealthcareVaccinationCenter(){

   }


    /**
     * Constructor for the new HCVC (Health Care Vaccination Center)
     * @param name VC name
     * @param phoneNumber VC Phone number
     * @param address VC Address
     * @param emailAddress VC email
     * @param faxNumber VC fax number
     * @param websiteAddress VC website address
     * @param openingHours VC opening Hours
     * @param closingHours VC closing hours
     * @param slotDuration VC slot duration, in minutos
     * @param maxNumVaccines maximum number of vaccines administrated per slot duration
     * @param centerCoordinator center coordinator, must be an employee registered w/ center coordinator role
     * @param ars Administração Regional de Saúde HCVC is associated to
     * @param ages Agrupamentos de Centros de Saúde HCVC is associated to
     * @param vaccineTypes vaccine types that can be administered at this HCVC
     */
    public HealthcareVaccinationCenter(String name,
                                       PhoneNumber phoneNumber,
                                       Address address,
                                       Email emailAddress,
                                       PhoneNumber faxNumber,
                                       String websiteAddress,
                                       String openingHours,
                                       String closingHours,
                                       int slotDuration,
                                       int maxNumVaccines,
                                       Employee centerCoordinator,
                                       String ars,
                                       String ages,
                                       ArrayList vaccineTypes) {



        super(name, phoneNumber, address, emailAddress, faxNumber, websiteAddress, openingHours, closingHours, slotDuration, maxNumVaccines, centerCoordinator);
        this.validateNonNullAttribute("ARS", ars);
        this.validateNonNullAttribute("AGES", ages);

        this.ars = ars;
        this.ages = ages;
        this.vaccineTypes = vaccineTypes;


    }

    public void setAges(String ages) {
        this.ages = ages;
    }

    public void setArs(String ars) {
        this.ars = ars;
    }


    public String getArs() {
        return ars;
    }

    public String getAges() {
        return ages;
    }

    public ArrayList<VaccineType> getVaccineTypes() {
        return this.vaccineTypes;
    }

    public boolean supportsVaccineType(String vaccineTypeCode)
    {
        for (VaccineType vaccineType : this.vaccineTypes)
        {
            if (vaccineType.getCode().equalsIgnoreCase(vaccineTypeCode))
            {
                return true;
            }
        }
        return false;
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




}
