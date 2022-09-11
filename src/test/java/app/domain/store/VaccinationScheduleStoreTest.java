package app.domain.store;

import app.domain.model.*;
import app.domain.shared.Address;
import app.domain.shared.Gender;
import app.domain.shared.Role;
import app.mappers.dto.*;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VaccinationScheduleStoreTest {

    private SnsUserDTO snsUser1;
    private SnsUserDTO snsUser2;
    private LocalDateTime date;
    private VaccineTypeDTO vaccineType1;
    private VaccineTypeDTO vaccineType2;
    private VaccinationCenterDTO vaccinationCenter1;
    private VaccinationCenterDTO vaccinationCenter2;

    private String serializationFileName;
    private VaccinationScheduleDTO vaccinationScheduleDTO;
    private VaccinationScheduleStore vaccinationScheduleStore;

    @BeforeEach
    void setUp() throws ParseException
    {
        this.serializationFileName = "vaccination_schedules_tests.dat";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        this.snsUser1 = new SnsUserDTO(
                "User Name",
                "user@email.com",
                Gender.Female,
                df.parse("01-09-1986"),
                12345678L,
                new AddressDTO(
                        "street",
                        123,
                        "1234-123",
                        "city",
                        "country"),
                921234567L,
                "00000000"
        );

        this.snsUser2 = new SnsUserDTO(
                "User Name",
                "user@email.com",
                Gender.Female,
                df.parse("01-09-1986"),
                123456789L,
                new AddressDTO(
                        "street",
                        123,
                        "1234-123",
                        "city",
                        "country"),
                921234567L,
                "00000001"
        );

        this.vaccineType1 = new VaccineTypeDTO(
                "code1",
                "Description",
                VaccineTechnology.LA
        );

        this.vaccineType2 = new VaccineTypeDTO(
                "code2",
                "Description",
                VaccineTechnology.LA
        );

        this.date = LocalDateTime.of(2030, 12, 12, 10, 30);

        this.vaccinationCenter1 = new CommunityMassVaccinationCenterDTO(
                "ISEP Vaccination Center",
                921234567L,
                new AddressDTO(
                        "street",
                        123,
                        "1234-123",
                        "city",
                        "country"),
                "vaccination@Braga.com",
                921234567L,
                "vaccinationPT.com",
                "8:00 AM",
                "8:00 PM",
                2,
                10,
                new Employee(
                        "Employee Name",
                        new Email("centerCoordinator@gmail.com"),
                        new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"), "Porto", "Portugal"),
                        new PhoneNumber(921234567L),
                        new CitizenCard("00000002"),
                        Role.CENTER_COORDINATOR
                ),
                new VaccineType(
                        "code1",
                        "Description",
                        VaccineTechnology.LA
                )
        );

        this.vaccinationCenter2 = new CommunityMassVaccinationCenterDTO(
                "ISEP Vaccination Center",
                921234567L,
                new AddressDTO(
                        "street",
                        123,
                        "1234-123",
                        "city",
                        "country"),
                "vaccination@Braga.com",
                921234567L,
                "vaccinationPT.com",
                "8:00 AM",
                "8:00 PM",
                2,
                10,
                new Employee(
                        "Employee Name",
                        new Email("centerCoordinator@gmail.com"),
                        new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"), "Porto", "Portugal"),
                        new PhoneNumber(921234567L),
                        new CitizenCard("00000003"),
                        Role.CENTER_COORDINATOR
                ),
                new VaccineType(
                        "code2",
                        "Description",
                        VaccineTechnology.LA
                )
        );

        this.vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser1,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );

        this.vaccinationScheduleStore = new VaccinationScheduleStore(this.serializationFileName);
    }

    @AfterEach
    public void dumpMemory() throws IOException {
        this.snsUser1 = null;
        this.date = null;
        this.vaccineType1 = null;
        this.vaccinationCenter1 = null;
        FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,  this.serializationFileName);
    }

    @Test
    @Order(1)
    public void addVaccinationScheduleForUser1Sucessfully()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = this.vaccinationScheduleDTO;
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        // Assert
        Assertions.assertTrue(addedToStore);
    }

    @Test
    @Order(2)
    public void existsVaccinationScheduleForUser1Sucessfully()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = this.vaccinationScheduleDTO;
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        Optional<VaccinationSchedule> vaccinationScheduleRetrieved = this.vaccinationScheduleStore.existsVaccinationSchedule(this.vaccinationScheduleDTO);
        // Assert
        Assertions.assertTrue(vaccinationScheduleRetrieved.isPresent());
    }

    @Test
    @Order(3)
    public void dontExistsVaccinationScheduleforUser2Sucessfully()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser2,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        Optional<VaccinationSchedule> vaccinationScheduleRetrieved = this.vaccinationScheduleStore.existsVaccinationSchedule(this.vaccinationScheduleDTO);
        // Assert
        Assertions.assertFalse(vaccinationScheduleRetrieved.isPresent());
    }

    @Test
    @Order(4)
    public void dontExistsVaccinationScheduleforUser1AndVaccineType2Sucessfully()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser1,
                this.date,
                this.vaccineType2,
                this.vaccinationCenter2
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        Optional<VaccinationSchedule> vaccinationScheduleRetrieved = this.vaccinationScheduleStore.existsVaccinationSchedule(this.vaccinationScheduleDTO);
        // Assert
        Assertions.assertFalse(vaccinationScheduleRetrieved.isPresent());
    }

    @Test
    @Order(5)
    public void existScheduleForSnsUserOnArrivalDate()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser2,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        Optional<VaccinationSchedule> existScheduleForSnsUserOnArrivalDate = this.vaccinationScheduleStore.existsVaccinationScheduleForUserOnArrivalDate(123456789L, LocalDateTime.now());
        // Assert
        Assertions.assertTrue(existScheduleForSnsUserOnArrivalDate.isPresent());
    }

    @Test
    @Order(6)
    public void doesNotExistScheduleForSnsUserOnArrivalDate()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser2,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        Optional<VaccinationSchedule> existScheduleForSnsUserOnArrivalDate = this.vaccinationScheduleStore.existsVaccinationScheduleForUserOnArrivalDate(987456123L, LocalDateTime.now());
        // Assert
        Assertions.assertFalse(existScheduleForSnsUserOnArrivalDate.isPresent());
    }

    @Test
    @Order(7)
    public void getAllVaccinationSchedules()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser2,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        List<VaccinationScheduleDTO> snsUserVaccinationSchedules = this.vaccinationScheduleStore.getAllVaccinationSchedules(123456789L);
        // Assert
        Assertions.assertEquals(1, snsUserVaccinationSchedules.size());
    }

    @Test
    @Order(8)
    public void getAllExistingVaccinationSchedule()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser2,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        List<VaccinationSchedule> vaccinationSchedules = this.vaccinationScheduleStore.getAllExistingVaccinationSchedule();
        // Assert
        Assertions.assertTrue(vaccinationSchedules.size() >= 1);
    }

    @Test
    @Order(9)
    public void getNumberOfPreviousVaccinations()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser2,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        int numberOfPreviousVaccinations = this.vaccinationScheduleStore.getNumberOfPreviousVaccinations(11111111L, "456PI");
        // Assert
        Assertions.assertEquals(0, numberOfPreviousVaccinations);
    }

    /*
    @Test
    @Order(10)
    public void getLastVaccinationDatePerSnsUser()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser2,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        LocalDateTime lastVaccinationDatePerSnsUser = this.vaccinationScheduleStore.getLastVaccinationDatePerSnsUser(11111111L);
        // Assert
        Assertions.assertEquals(LocalDateTime.of(2022, 5, 5, 10, 0), lastVaccinationDatePerSnsUser);
    }
    */

    /*
    @Test
    @Order(11)
    public void getLastAdministeredVaccinePerSnsUser()
    {
        // Arrange + Act
        VaccinationScheduleDTO vaccinationScheduleDTO = new VaccinationScheduleDTO(
                this.snsUser2,
                this.date,
                this.vaccineType1,
                this.vaccinationCenter1
        );
        VaccinationSchedule vaccinationSchedule = this.vaccinationScheduleStore.createVaccinationSchedule(vaccinationScheduleDTO);
        boolean addedToStore = this.vaccinationScheduleStore.addVaccinationSchedule(vaccinationSchedule);
        Vaccine vaccine = this.vaccinationScheduleStore.getLastAdministeredVaccinePerSnsUser(11111111L);
        // Assert
        Assertions.assertNotNull(vaccine);
    }
    */

}
