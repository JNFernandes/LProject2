package app.domain.model;

import app.domain.shared.Address;
import app.domain.shared.Role;
import app.domain.store.VaccinationCenterStore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.text.ParseException;
import java.util.ArrayList;


public class VaccinationCentersTest {


    private String name;
    private PhoneNumber phoneNumber;
    private Address address;
    private Email emailAddress;
    private PhoneNumber vaccinationCenterFaxNumber;
    private String websiteAddress;
    private String openingHours;
    private String closingHours;
    private int slotDuration;
    private int maxNumVaccines;
    private Employee centerCoordinator;
    private VaccineType vaccineType;
    private ArrayList<VaccineType> vaccineTypeList  = new ArrayList<VaccineType>();
    private String ars;
    private String ages;
    private HealthcareVaccinationCenter healthcareVaccinationCenter;
    private CommunityMassVaccinationCenter communityMassVaccinationCenter;
    private long expectedWorkingHourPeriodHC;
    private long actualWorkingHourPeriodHC;

    private String expectedClassName;

    private String actualClassName;




    @BeforeEach
    public void setupObjects() throws ParseException
    {

        this.name = "ISEP Vaccination Center";
        this.phoneNumber = new PhoneNumber(921234567L);
        this.address = new Address(
                "street",
                123,
                new PostalCode("1234-123"),
                "city",
                "country");
        this.emailAddress = new Email("vaccination@email.com");
        this.vaccinationCenterFaxNumber = new PhoneNumber(921234567L);
        this.websiteAddress = "vaccinationPT.com";
        this.openingHours = "08:00 AM";
        this.closingHours = "10:00 PM";
        this.slotDuration = 10;
        this.maxNumVaccines = 2;
        this.centerCoordinator = new Employee(
                "Employee Name",
                new Email("centerCoordinator@gmail.com"),
                new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"),"Porto","Portugal"),
                new PhoneNumber(921234567L),
                new CitizenCard("00000001"),
                Role.CENTER_COORDINATOR
        );
        this.vaccineType = new VaccineType(
                "code1",
                "description",
                VaccineTechnology.LA
        );
        vaccineTypeList.add(vaccineType);
        vaccineTypeList.add(new VaccineType(
                "code2",
                "description2",
                VaccineTechnology.MRV
        ) );
        this.ars = "Centro Regional Norte";
        this.ages = "Centros de saude do Porto";
        this.healthcareVaccinationCenter = new HealthcareVaccinationCenter(
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
        this.communityMassVaccinationCenter = new CommunityMassVaccinationCenter(
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

        this.expectedWorkingHourPeriodHC = 840;

        this.actualWorkingHourPeriodHC = this.healthcareVaccinationCenter.obtainWorkingPeriodMinutes();


        this.actualClassName = HealthcareVaccinationCenter.getClassNameForPerformanceAnalysis();
        if (actualClassName.equalsIgnoreCase("app.externalmodule.BenchmarkAlgorithmAdapter")) {
            this.expectedClassName = "app.externalmodule.BenchmarkAlgorithmAdapter";
        } else {
            this.expectedClassName = "app.externalmodule.BruteForceAlgorithmAdapter";
        }


    }

    @AfterEach
    public void dumpMemory()
    {
        this.name = null;
        this.phoneNumber = null;
        this.address = null;
        this.emailAddress = null;
        this.vaccinationCenterFaxNumber = null;
        this.websiteAddress = null;
        this.openingHours = null;
        this.closingHours = null;
        this.slotDuration = 0;
        this.maxNumVaccines = 0;
        this.centerCoordinator = null;
        this.vaccineType = null;
    }

    @Test
    public void createMassVaccinationCenterSuccessfully()
    {
        // Arrange + Act
        CommunityMassVaccinationCenter communityMassVaccinationCenter = this.communityMassVaccinationCenter;
        // Assert
        Assertions.assertNotNull(communityMassVaccinationCenter);
    }


    @Test
    public void createHealthCareCenterSuccessfully()
    {
        // Arrange + Act
        HealthcareVaccinationCenter healthcareVaccinationCenter = this.healthcareVaccinationCenter;
        // Assert
        Assertions.assertNotNull(healthcareVaccinationCenter);
    }

    @Test
    public void createCommunityMassVaccinationCenterWithNoPhoneNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
                            this.name,
                            null,
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
                "Community Mass VC must have a phone number, it cannot be null."
        );
    }



    @Test
    public void createCommunityMassVaccinationCenterWithNullName()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
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
    public void createCommunityMassVaccinationCenterWithNullEmail()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
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
    public void createCommunityMassVaccinationCenterWithNullFax()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            null,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have a fax number, it cannot be 0."
        );
    }


    @Test
    public void createCommunityMassVaccinationCenterWithNullWebsiteAddress()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            null,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have a website, it cannot be null nor blanc."
        );
    }



    @Test
    public void createCommunityMassVaccinationCenterWithNullOpeningHours()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
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
                "Community Mass VC must have defined opening hours, it cannot be null nor blanc."
        );
    }

    @Test
    public void createCommunityMassVaccinationCenterWithNullClosingHours()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
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
    public void createCommunityMassVaccinationCenterWithNoVaccinesNbrPerSlot()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            0,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have a maximum number of vaccines that can be given per slot , it cannot be 0."
        );
    }

    @Test
    public void createCommunityMassVaccinationCenterWithNoSlotDuration()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            0,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.vaccineType);
                },
                "Community Mass VC must have a slot duration defined , it cannot be 0."
        );
    }


    @Test
    public void createCommunityMassVaccinationCenterWithNoCenterCoordinator()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
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
                            null,
                            this.vaccineType);
                },
                "Community Mass VC must have a center coordinator allocated, it cannot be null nor blanc."
        );
    }

    /**
    @Test
    public void createCommunityMassVaccinationCenterWithNoVaccineType()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new CommunityMassVaccinationCenter(
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
                            null);
                },
                "Community Mass VC must have a defined vaccine to administer, it cannot be null nor blanc."
        );
    }
   **/


    @Test
    public void createHealthCareVaccinationCenterWithNoSlotDuration() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthcareVaccinationCenter(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            0,
                            this.maxNumVaccines,
                            this.centerCoordinator,
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have a defined slot duration, it cannot be 0 or null."
        );
    }

    @Test
    public void createHealthCareVaccinationCenterWithNoName() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthcareVaccinationCenter(
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
                    new HealthcareVaccinationCenter(
                            this.name,
                            null,
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
    public void createHealthCareVaccinationCenterWithNoArs() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthcareVaccinationCenter(
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
                    new HealthcareVaccinationCenter(
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
    public void createHealthCareVaccinationCenterWithNoFaxNumber() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthcareVaccinationCenter(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            null,
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
                "Health Care VC must have a fax number, it cannot be 0 nor blanc."
        );
    }


    @Test
    public void createHealthCareVaccinationCenterWithNoOpeningHours() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthcareVaccinationCenter(
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
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have defined opening hours, it cannot be null nor blanc."
        );
    }


    @Test
    public void createHealthCareVaccinationCenterWithNoClosingHours() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthcareVaccinationCenter(
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
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have defined closing hours, it cannot be null nor blanc."
        );
    }



    @Test
    public void createHealthCareVaccinationCenterWithNoMaxNumberVaccines() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthcareVaccinationCenter(
                            this.name,
                            this.phoneNumber,
                            this.address,
                            this.emailAddress,
                            this.vaccinationCenterFaxNumber,
                            this.websiteAddress,
                            this.openingHours,
                            this.closingHours,
                            this.slotDuration,
                            0,
                            this.centerCoordinator,
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have a maximum number of vaccines that can be given per slot , it cannot be 0."
        );
    }


    @Test
    public void createHealthCareVaccinationCenterWithNoCoordinator() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new HealthcareVaccinationCenter(
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
                            null,
                            this.ars,
                            this.ages,
                            this.vaccineTypeList);
                },
                "Health Care VC must have a center coordinator, it cannot be null nor blanc."
        );
    }

    @Test
    void obtainWorkingPeriodMinutes() {
        Assertions.assertEquals(this.expectedWorkingHourPeriodHC, this.actualWorkingHourPeriodHC);
    }

    @Test
    void getClassNameForPerformanceAnalysis() {
        Assertions.assertEquals(expectedClassName, actualClassName);
    }



}