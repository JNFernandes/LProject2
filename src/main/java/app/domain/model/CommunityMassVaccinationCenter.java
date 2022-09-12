package app.domain.model;

import app.domain.shared.Address;
import pt.isep.lei.esoft.auth.domain.model.Email;

/**
 * Type of Vaccination center, Community Mass Vaccination Center, to be defined and created by the admin
 */
public class CommunityMassVaccinationCenter extends VaccinationCenter {

    private VaccineType vaccineType;

    /**
     * Default constructor
     */
    public CommunityMassVaccinationCenter(){

    }

    /**
     * Constructor for the new CMVC (Community Mass Vaccination Center)
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
     * @param vaccineType vaccine type that can be administered at this CMVC
     */
    public CommunityMassVaccinationCenter(String name,
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
                                          VaccineType vaccineType) {

        super(name, phoneNumber, address, emailAddress, faxNumber, websiteAddress, openingHours, closingHours, slotDuration, maxNumVaccines, centerCoordinator);
        this.vaccineType = vaccineType;
    }

    public VaccineType getVaccineType() {
        return vaccineType;
    }
}

