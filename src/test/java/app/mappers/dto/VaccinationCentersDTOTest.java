package app.mappers.dto;

import app.domain.model.*;
import app.domain.shared.Address;
import app.domain.shared.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.text.ParseException;
import java.util.ArrayList;

public class VaccinationCentersDTOTest {

    private String name;
    private int phoneNumber;
    private AddressDTO address;
    private String emailAddress;
    private int vaccinationCenterFaxNumber;
    private String websiteAddress;
    private String openingHours;
    private String closingHours;
    private int slotDuration;
    private int maxNumVaccines;
    private Employee centerCoordinator;
    private VaccineType vaccineType;
    private ArrayList<VaccineType> vaccineTypeList = new ArrayList<VaccineType>();
    private String ars;
    private String ages;
    private HealthCareVaccinationCenterDTO healthcareVaccinationCenter;
    private CommunityMassVaccinationCenterDTO communityMassVaccinationCenter;

    @BeforeEach
    void setupObjects() throws ParseException
    {
        this.name = "ISEP Vaccination Center";
        this.phoneNumber = 921234567;
        this.address = new AddressDTO(
                "street",
                123,
                "1234-123",
                "city",
                "country");
        this.emailAddress = "vaccination@email.com";
        this.vaccinationCenterFaxNumber = 921234567;
        this.websiteAddress = "vaccinationPT.com";
        this.openingHours = "8am";
        this.closingHours = "10pm";
        this.slotDuration = 10;
        this.maxNumVaccines = 2;
        this.centerCoordinator = new Employee(
                "Employee Name",
                new Email("centerCoordinator@gmail.com"),
                new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"),"Porto","Portugal"),
                new PhoneNumber(921234567L),
                new CitizenCard("00000000"),
                Role.CENTER_COORDINATOR
        );
        this.vaccineType = new VaccineType(
                "code1",
                "description1",
                VaccineTechnology.LA
        );
        vaccineTypeList.add(vaccineType);
        vaccineTypeList.add(1,new VaccineType(
                "code2",
                "Description2",
                VaccineTechnology.MRV
        ) );
        this.ars = "Centro Regional Norte";
        this.ages = "Centros de saude do Porto";
        this.healthcareVaccinationCenter = new HealthCareVaccinationCenterDTO(
                this.name,
                this.phoneNumber,
                this.address,
                this.emailAddress,
                this.vaccinationCenterFaxNumber,
                this.websiteAddress,
                this.openingHours,
                this.closingHours,
                this.slotDuration,
                this.maxNumVaccines,
                this.centerCoordinator,
                this.ars,
                this.ages,
                this.vaccineTypeList
        );
        this.communityMassVaccinationCenter = new CommunityMassVaccinationCenterDTO(
                this.name,
                this.phoneNumber,
                this.address,
                this.emailAddress,
                this.vaccinationCenterFaxNumber,
                this.websiteAddress,
                this.openingHours,
                this.closingHours,
                this.slotDuration,
                this.maxNumVaccines,
                this.centerCoordinator,
                this.vaccineType
        );

    }

    @AfterEach
    public void dumpMemory()
    {
        this.name = null;
        this.phoneNumber = 0;
        this.address = null;
        this.emailAddress = null;
        this.vaccinationCenterFaxNumber = 0;
        this.websiteAddress = null;
        this.openingHours = null;
        this.closingHours = null;
        this.slotDuration = 0;
        this.maxNumVaccines = 0;
        this.centerCoordinator = null;
        this.vaccineType = null;
        this.communityMassVaccinationCenter = null;
        this.healthcareVaccinationCenter = null;
    }


    @Test
    public void createHealthCareVaccinatoionCenterSuccessfully()
    {
        // Arrange + Act
        HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO = this.healthcareVaccinationCenter;
        // Assert
        Assertions.assertNotNull(healthcareVaccinationCenter);
    }

    @Test
    public void createCommunityMassVaccinatoionCenterSuccessfully()
    {
        // Arrange + Act
        CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO = this.communityMassVaccinationCenter;
        // Assert
        Assertions.assertNotNull(communityMassVaccinationCenter);
    }


    @Test
    public void createCommunityMassVaccinationCenterWithNullName()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenterDTO(
                            null,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have a name, it cannot be null nor blanc."
        );
    }

    @Test
    public void createHealthCareVaccinationCenterWithNoName() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthCareVaccinationCenterDTO(
                            null,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have a name, it cannot be null nor blanc."
        );
    }


    @Test
    public void createHealthCareVaccinationCenterWithNoPhoneNumber() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthCareVaccinationCenterDTO(
                            this.name,
                            0,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have a defined phone number, it cannot be 0 nor blanc."
        );
    }


    @Test
    public void createCommunityMassVaccinationCenterWithNoPhoneNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenterDTO(
                            this.name,
                            0,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have a phone number, it cannot be 0."
        );
    }

    @Test
    public void createCommunityMassVaccinationCenterWithNullEmail()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenterDTO(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            null,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have an email address, it cannot be null nor blanc."
        );
    }

    @Test
    public void createHealthCareVaccinationCenterWithNoEmail() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthCareVaccinationCenterDTO(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            null,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have an email, it cannot be 0 nor blanc."
        );
    }


    @Test
    public void createHealthCareVaccinationCenterWithNoArs() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthCareVaccinationCenterDTO(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            null,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have an associated ARS, it cannot be null nor blanc."
        );
    }


    @Test
    public void createHealthCareVaccinationCenterWithNoAges() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthCareVaccinationCenterDTO(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.ars,
                            null,
                            this.vaccineTypeList);
                },
                "Health Care VC must have an associated AGES, it cannot be null nor blanc."
        );
    }


    @Test
    public void createCommunityMassVaccinationCenterWithNullOpeningHours()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenterDTO(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            null,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have defined opening hours, it cannot be null nor blank."
        );
    }

    @Test
    public void createCommunityMassVaccinationCenterWithNullClosingHours()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenterDTO(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            null,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have defined closing hours, it cannot be null nor blanc."
        );
    }

    @Test
    public void createCommunityMassVaccinationCenterWithNullAddressHours()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenterDTO(
                            this.name,
                            this.phoneNumber,
                            null,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have an address, it cannot be null nor blanc."
        );
    }
    @Test
    public void createHealthCareVaccinationCenterWithNoAddress() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthCareVaccinationCenterDTO(
                            this.name,
                            this.phoneNumber,
                           null,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have an address, it cannot be null nor blanc."
        );
    }

}
