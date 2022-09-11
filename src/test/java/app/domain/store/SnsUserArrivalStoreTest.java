package app.domain.store;

import app.domain.model.*;
import app.domain.shared.Address;
import app.domain.shared.Gender;
import app.domain.shared.Role;
import app.mappers.dto.*;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SnsUserArrivalStoreTest {

    private LocalDateTime arrivalDate;
    private Long snsNumber;
    private VaccinationScheduleDTO vaccineSchedule;
    private SnsUserArrivalDTO snsUserArrival;
    private SnsUserArrivalStore snsUserArrivalStore;
    private String serializationFileName;

    private List<LocalDateTime> expectedArrivalList;

    private List<LocalDateTime> actualArrivalList;

    private SnsUserArrival snsUserArrival2;

    private SnsNumber snsNumber2;

    private VaccinationSchedule vaccinationSchedule;

    private List<LocalDate> expectedDayList;

    private List<LocalDate> actualDayList;



    @BeforeEach
    void setUp() throws ParseException
    {
        this.serializationFileName = "sns_users_arrivals_tests.dat";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.arrivalDate = LocalDateTime.of(2019, 03, 28, 14, 33, 48);
        this.snsNumber = 12345678L;
        this.snsNumber2 = new SnsNumber(22335675L);
        this.vaccineSchedule = new VaccinationScheduleDTO(
                new SnsUserDTO(
                        "Antonio Joaquim",
                        "user@email.com",
                        Gender.Female,
                        df.parse("05-11-1998"),
                        this.snsNumber,
                        new AddressDTO(
                                "street",
                                123,
                                "1234-123",
                                "city",
                                "country"
                        ),
                        921234567L,
                        "00000000"),
                this.arrivalDate,
                new VaccineTypeDTO("code1",
                        "description1",
                        VaccineTechnology.LA
                ),
                new CommunityMassVaccinationCenterDTO(
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
                                new CitizenCard("00000000"),
                                Role.CENTER_COORDINATOR
                        ),
                        new VaccineType(
                                "code1",
                                "Description",
                                VaccineTechnology.LA
                        )
                ));
        this.snsUserArrival = new SnsUserArrivalDTO(
                this.arrivalDate,
                this.snsNumber,
                this.vaccineSchedule
        );

        this.vaccinationSchedule = new VaccinationSchedule(
                new SnsUser("user@email.com", "Antonio Joaquim", new Email("user@email.com"), Gender.Female, df.parse("05-11-1998"),
                        this.snsNumber2, new Address("street", 123, new PostalCode("1234-123"), "city", "country"),
                        new PhoneNumber(921234567L),
                        new CitizenCard("00000000")),
                this.arrivalDate,
                new VaccineType("code1",
                        "Description",
                        VaccineTechnology.LA),
                new CommunityMassVaccinationCenter(
                        "ISEP Vaccination Center",
                        new PhoneNumber(921234567L),
                        new Address(
                                "street",
                                123,
                                new PostalCode("1234-123"),
                                "city",
                                "country"),
                        new Email("vaccination@Braga.com"),
                        new PhoneNumber(921234567L),
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
                                new CitizenCard("00000000"),
                                Role.CENTER_COORDINATOR
                        ),
                        new VaccineType(
                                "code1",
                                "Description",
                                VaccineTechnology.LA
                        )
                ));



        this.snsUserArrivalStore = new SnsUserArrivalStore(this.serializationFileName);

        // Test for mehtod getListArrivals
        this.snsUserArrival2 = new SnsUserArrival(this.arrivalDate, this.snsNumber2, this.vaccinationSchedule);
        String vaccinationCenterEmail = "vaccination@Braga.com";
        this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival2);
        this.expectedArrivalList = new ArrayList<>();
        expectedArrivalList.add(this.arrivalDate);
        this.actualArrivalList = this.snsUserArrivalStore.getListArrivals(vaccinationCenterEmail);

        //Test for method getListDaysForPerformance
        String dateStr = "2019-03-28";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(dateStr, formatter);
        this.expectedDayList = new ArrayList<>();
        expectedDayList.add(date);
        this.actualDayList = this.snsUserArrivalStore.getListDaysForPerformance(vaccinationCenterEmail);

    }

    @AfterEach
    public void dumpMemory() throws IOException {
        this.arrivalDate = null;
        this.snsNumber = null;
        this.vaccineSchedule = null;
        FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,  this.serializationFileName);
    }

    @Test
    public void addSuccessfullySnsUserArrival()
    {
        // Arrange + Act
        SnsUserArrivalDTO snsUserArrivalDTO = this.snsUserArrival;
        SnsUserArrival snsUserArrival = this.snsUserArrivalStore.createSnsUserArrival(snsUserArrivalDTO);
        boolean addedToStoreSuccessfully = this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival);
        //Assert
        Assertions.assertFalse(addedToStoreSuccessfully);
    }

    @Test
    public void existsSnsUserArrivalAlready()
    {
        // Arrange + Act
        SnsUserArrivalDTO snsUserArrivalDTO = this.snsUserArrival;
        SnsUserArrival snsUserArrival = this.snsUserArrivalStore.createSnsUserArrival(snsUserArrivalDTO);
        boolean addedToStoreSuccessfully = this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival);
        boolean alreadyRegistered = this.snsUserArrivalStore.existsSnsUserArrival(87654321L, LocalDateTime.now());
        //Assert
        Assertions.assertFalse(alreadyRegistered);
    }

    @Test
    public void notExistsSnsUserArrivalAlready()
    {
        // Arrange + Act
        SnsUserArrivalDTO snsUserArrivalDTO = this.snsUserArrival;
        SnsUserArrival snsUserArrival = this.snsUserArrivalStore.createSnsUserArrival(snsUserArrivalDTO);
        boolean addedToStoreSuccessfully = this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival);
        boolean alreadyRegistered = this.snsUserArrivalStore.existsSnsUserArrival(123456889L, LocalDateTime.now());
        //Assert
        Assertions.assertFalse(alreadyRegistered);
    }

    @Test
    public void userArrivedAtCorrectVaccinationCenter()
    {
        // Arrange + Act
        SnsUserArrivalDTO snsUserArrivalDTO = this.snsUserArrival;
        SnsUserArrival snsUserArrival = this.snsUserArrivalStore.createSnsUserArrival(snsUserArrivalDTO);
        boolean addedToStoreSuccessfully = this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival);
        boolean correctVaccinationCenter = this.snsUserArrivalStore.checkIfVaccinationCenterIsTheOneOnTheVaccinationSchedule(vaccineSchedule, "ISEP Vaccination Center");
        //Assert
        Assertions.assertTrue(correctVaccinationCenter);
    }

    @Test
    public void userArrivedAtInCorrectVaccinationCenter()
    {
        // Arrange + Act
        SnsUserArrivalDTO snsUserArrivalDTO = this.snsUserArrival;
        SnsUserArrival snsUserArrival = this.snsUserArrivalStore.createSnsUserArrival(snsUserArrivalDTO);
        boolean addedToStoreSuccessfully = this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival);
        boolean correctVaccinationCenter = this.snsUserArrivalStore.checkIfVaccinationCenterIsTheOneOnTheVaccinationSchedule(vaccineSchedule, "Coimbra Vaccination Center");
        //Assert
        Assertions.assertFalse(correctVaccinationCenter);
    }

    @Test
    public void vaccinationScheduleStateChangedCorrectly()
    {
        //Arrange + Act
        SnsUserArrivalDTO snsUserArrivalDTO = this.snsUserArrival;
        SnsUserArrival snsUserArrival = this.snsUserArrivalStore.createSnsUserArrival(snsUserArrivalDTO);
        this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival);
        boolean changeStateCorrectly = this.snsUserArrivalStore.changeState(snsUserArrival);
        //Assert
        Assertions.assertTrue(changeStateCorrectly);

    }

    @Test
    void getListArrivals() {
        Assertions.assertEquals(expectedArrivalList, actualArrivalList);
    }

    @Test
    void getListDaysForPerformance() {
        Assertions.assertEquals(expectedDayList, actualDayList);
    }


}
