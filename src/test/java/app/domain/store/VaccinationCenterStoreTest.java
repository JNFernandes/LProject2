package app.domain.store;

import app.domain.model.*;
import app.domain.shared.Address;
import app.domain.shared.Role;
import app.mappers.dto.AddressDTO;
import app.mappers.dto.CommunityMassVaccinationCenterDTO;
import app.mappers.dto.HealthCareVaccinationCenterDTO;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class VaccinationCenterStoreTest {

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
    private VaccinationCenterStore vaccinationCenterStore;
    private String serializationFileName;

    @BeforeEach
    void setupObjects() throws ParseException
    {
        this.serializationFileName = "vaccination_centers_tests.dat";
        this.vaccinationCenterStore = new VaccinationCenterStore(this.serializationFileName);
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
        this.openingHours = "08:00 AM";
        this.closingHours = "10:00 PM";
        this.slotDuration = 10;
        this.maxNumVaccines = 2;
        this.centerCoordinator = new Employee(
                "Employee Name",
                new Email("centerCoordinator@gmail.com"),
                new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"), "Porto", "Portugal"),
                new PhoneNumber(921234567L),
                new CitizenCard("00000000"),
                Role.CENTER_COORDINATOR
        );
        this.vaccineType = new VaccineType(
                "code",
                "Description",
                VaccineTechnology.LA
        );
        vaccineTypeList.add(vaccineType);
        vaccineTypeList.add(new VaccineType(
                "code2",
                "Description2",
                VaccineTechnology.MRV
        ));
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
    public void dumpMemory() throws IOException {
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
        this.vaccinationCenterStore = null;
        FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,  this.serializationFileName);
    }

    @Test
    public void createCommunityMassVCSuccessfully()
    {
        // Arrange + Act
        CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO = this.communityMassVaccinationCenter;
        CommunityMassVaccinationCenter communityMassVaccinationCenter = this.vaccinationCenterStore.createCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
        // Assert
        Assertions.assertNotNull(communityMassVaccinationCenter);
        Assertions.assertEquals("ISEP Vaccination Center", communityMassVaccinationCenter.getVaccinationCenterName());
        Assertions.assertEquals(921234567, communityMassVaccinationCenter.getVaccinationCenterPhoneNumber().getNumber());
        Assertions.assertEquals(this.address.getCity(), communityMassVaccinationCenter.getAddress().getCity());
        Assertions.assertEquals(this.address.getCountry(), communityMassVaccinationCenter.getAddress().getCountry());
        Assertions.assertEquals(this.address.getStreet(), communityMassVaccinationCenter.getAddress().getStreet());
        Assertions.assertEquals(this.address.getDoorNumber(), communityMassVaccinationCenter.getAddress().getDoorNumber());
        Assertions.assertEquals(this.address.getPostalCode(), communityMassVaccinationCenter.getAddress().getPostalCode().getNumber());
        Assertions.assertEquals("vaccination@email.com", communityMassVaccinationCenter.getVaccinationCenterEmailAddress().getEmail());
        Assertions.assertEquals(921234567, communityMassVaccinationCenter.getVaccinationCenterFaxNumber().getNumber());
        Assertions.assertEquals("vaccinationPT.com", communityMassVaccinationCenter.getWebsiteAddress());
        Assertions.assertEquals("08:00 AM", communityMassVaccinationCenter.getOpeningHours());
        Assertions.assertEquals("10:00 PM", communityMassVaccinationCenter.getClosingHours());
        Assertions.assertEquals(10, communityMassVaccinationCenter.getSlotDuration());
        Assertions.assertEquals(2, communityMassVaccinationCenter.getMaxNumVaccines());
        Assertions.assertEquals(this.centerCoordinator.getName(), communityMassVaccinationCenter.getCenterCoordinator().getName());
        Assertions.assertEquals(this.centerCoordinator.getEmail(), communityMassVaccinationCenter.getCenterCoordinator().getEmail());
        Assertions.assertEquals(this.centerCoordinator.getAddress(), communityMassVaccinationCenter.getCenterCoordinator().getAddress());
        Assertions.assertEquals(this.centerCoordinator.getPhoneNumber(), communityMassVaccinationCenter.getCenterCoordinator().getPhoneNumber());
        Assertions.assertEquals(this.centerCoordinator.getCitizenCardNumber(), communityMassVaccinationCenter.getCenterCoordinator().getCitizenCardNumber());
        Assertions.assertEquals(this.vaccineType.getCode(), communityMassVaccinationCenter.getVaccineType().getCode());
        Assertions.assertEquals(this.vaccineType.getDescription(), communityMassVaccinationCenter.getVaccineType().getDescription());
    }

    @Test
    public void createHealthCareVCSuccessfully()
    {
        // Arrange + Act
        HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO = this.healthcareVaccinationCenter;
        HealthcareVaccinationCenter healthcareVaccinationCenter = this.vaccinationCenterStore.createHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
        // Assert
        Assertions.assertNotNull(healthcareVaccinationCenter);
        Assertions.assertEquals("ISEP Vaccination Center", healthcareVaccinationCenter.getVaccinationCenterName());
        Assertions.assertEquals(921234567, healthcareVaccinationCenter.getVaccinationCenterPhoneNumber().getNumber());
        Assertions.assertEquals(this.address.getCity(), healthcareVaccinationCenter.getAddress().getCity());
        Assertions.assertEquals(this.address.getCountry(), healthcareVaccinationCenter.getAddress().getCountry());
        Assertions.assertEquals(this.address.getStreet(), healthcareVaccinationCenter.getAddress().getStreet());
        Assertions.assertEquals(this.address.getDoorNumber(), healthcareVaccinationCenter.getAddress().getDoorNumber());
        Assertions.assertEquals(this.address.getPostalCode(), healthcareVaccinationCenter.getAddress().getPostalCode().getNumber());
        Assertions.assertEquals("vaccination@email.com", healthcareVaccinationCenter.getVaccinationCenterEmailAddress().getEmail());
        Assertions.assertEquals(921234567, healthcareVaccinationCenter.getVaccinationCenterFaxNumber().getNumber());
        Assertions.assertEquals("vaccinationPT.com", healthcareVaccinationCenter.getWebsiteAddress());
        Assertions.assertEquals("08:00 AM", healthcareVaccinationCenter.getOpeningHours());
        Assertions.assertEquals("10:00 PM", healthcareVaccinationCenter.getClosingHours());
        Assertions.assertEquals(10, healthcareVaccinationCenter.getSlotDuration());
        Assertions.assertEquals(2, healthcareVaccinationCenter.getMaxNumVaccines());
        Assertions.assertEquals(this.centerCoordinator.getName(), healthcareVaccinationCenter.getCenterCoordinator().getName());
        Assertions.assertEquals(this.centerCoordinator.getEmail(), healthcareVaccinationCenter.getCenterCoordinator().getEmail());
        Assertions.assertEquals(this.centerCoordinator.getAddress(), healthcareVaccinationCenter.getCenterCoordinator().getAddress());
        Assertions.assertEquals(this.centerCoordinator.getPhoneNumber(), healthcareVaccinationCenter.getCenterCoordinator().getPhoneNumber());
        Assertions.assertEquals(this.centerCoordinator.getCitizenCardNumber(), healthcareVaccinationCenter.getCenterCoordinator().getCitizenCardNumber());
        Assertions.assertEquals("Centro Regional Norte", healthcareVaccinationCenter.getArs());
        Assertions.assertEquals("Centros de saude do Porto", healthcareVaccinationCenter.getAges());
    }


    @Test
    public void addCommunityMassVCSuccessfully()
    {
        // Arrange + Act
        CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO = this.communityMassVaccinationCenter;
        CommunityMassVaccinationCenter communityMassVaccinationCenter = this.vaccinationCenterStore.createCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
        boolean addedToStore = this.vaccinationCenterStore.addVaccinationCenter(communityMassVaccinationCenter);
        // Assert
        Assertions.assertTrue(addedToStore);
    }

    @Test
    public void addHealthCareVCSuccessfully()
    {
        // Arrange + Act
        HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO = this.healthcareVaccinationCenter;
        HealthcareVaccinationCenter healthcareVaccinationCenter = this.vaccinationCenterStore.createHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
        boolean addedToStore = this.vaccinationCenterStore.addVaccinationCenter(healthcareVaccinationCenter);
        // Assert
        Assertions.assertTrue(addedToStore);
    }

    @Test
    public void existsCommunityMassVaccinationCenterSuccessfully()
    {
        // Arrange + Act
        CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO = this.communityMassVaccinationCenter;
        CommunityMassVaccinationCenter communityMassVaccinationCenter = this.vaccinationCenterStore.createCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
        boolean addedToStore = this.vaccinationCenterStore.addVaccinationCenter(communityMassVaccinationCenter);
        boolean vaccinationCenterRetrieved = this.vaccinationCenterStore.exists(communityMassVaccinationCenter.getVaccinationCenterEmailAddress());
        // Assert
        Assertions.assertTrue(vaccinationCenterRetrieved);
    }

    @Test
    public void existsHealthCareVaccinationCenterSuccessfully()
    {
        // Arrange + Act
        HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO = this.healthcareVaccinationCenter;
        HealthcareVaccinationCenter healthcareVaccinationCenter = this.vaccinationCenterStore.createHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
        boolean addedToStore = this.vaccinationCenterStore.addVaccinationCenter(healthcareVaccinationCenter);
        boolean vaccinationCenterRetrieved = this.vaccinationCenterStore.exists(healthcareVaccinationCenter.getVaccinationCenterEmailAddress());
        // Assert
        Assertions.assertTrue(vaccinationCenterRetrieved);
    }

    @Test
    public void notExistHealthCareVaccinationCenterSuccessfully()
    {
        // Arrange + Act
        HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO = this.healthcareVaccinationCenter;
        HealthcareVaccinationCenter healthcareVaccinationCenter = this.vaccinationCenterStore.createHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
        boolean addedToStore = this.vaccinationCenterStore.addVaccinationCenter(healthcareVaccinationCenter);
        boolean vaccinationCenterRetrieved = this.vaccinationCenterStore.exists(new Email("incorrect@email.com"));
        // Assert
        Assertions.assertFalse(vaccinationCenterRetrieved);
    }

    @Test
    public void notExistCommunityMassVaccinationCenterSuccessfully()
    {
        // Arrange + Act
        CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO = this.communityMassVaccinationCenter;
        CommunityMassVaccinationCenter communityMassVaccinationCenter = this.vaccinationCenterStore.createCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
        boolean addedToStore = this.vaccinationCenterStore.addVaccinationCenter(communityMassVaccinationCenter);
        boolean vaccinationCenterRetrieved = this.vaccinationCenterStore.exists(new Email("incorrect@email.com"));
        // Assert
        Assertions.assertFalse(vaccinationCenterRetrieved);
    }

    @Test
    public void getHealthCareVaccinationCenterByEmailSuccessfully()
    {
        // Arrange + Act
        HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO = this.healthcareVaccinationCenter;
        HealthcareVaccinationCenter healthcareVaccinationCenter = this.vaccinationCenterStore.createHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
        boolean addedToStore = this.vaccinationCenterStore.addVaccinationCenter(healthcareVaccinationCenter);
        Optional<VaccinationCenter> vaccinationCenterRetrieved = this.vaccinationCenterStore.getByEmail(healthcareVaccinationCenter.getVaccinationCenterEmailAddress().getEmail());
        // Assert
        if (vaccinationCenterRetrieved.isPresent())
        {
            Assertions.assertEquals(healthcareVaccinationCenter, vaccinationCenterRetrieved.get());
        }
    }

    @Test
    public void checkIfVaccinationCenterIsOpen() {
        // Arrange + Act
        HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO = this.healthcareVaccinationCenter;
        HealthcareVaccinationCenter healthcareVaccinationCenter = this.vaccinationCenterStore.createHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
        boolean addedToStore = this.vaccinationCenterStore.addVaccinationCenter(healthcareVaccinationCenter);
        boolean vaccinationCenterOpen = this.vaccinationCenterStore.checkIfUserArrivedWithinVaccinationCenterWorkingHours(LocalDateTime.of(2022, 05, 29, 16, 15), "ISEP Vaccination Center");
        // Assert
        Assertions.assertTrue(vaccinationCenterOpen);
    }



}
