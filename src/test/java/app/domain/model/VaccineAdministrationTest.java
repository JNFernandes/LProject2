package app.domain.model;

import app.domain.shared.Address;
import app.domain.shared.Gender;
import app.domain.shared.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class VaccineAdministrationTest {

    private SnsUserArrival snsUserArrival;
    private SnsUser snsUser;
    private Vaccine vaccine;
    private int doseNumber;
    private String lotNumber;
    private LocalDateTime administrationDate;
    private LocalDateTime leavingDate;
    private VaccineAdministrationState vaccineAdministrationState;
    private VaccineAdministration vaccineAdministration;

    @BeforeEach
    public void setUpObjetcts() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.snsUserArrival = new SnsUserArrival(
                LocalDateTime.of(2019, 03, 28, 14, 33, 48),
        new SnsNumber(12345678L),
        new VaccinationSchedule(
                new SnsUser(
                        "user@email.com",
                        "Antonio Joaquim",
                        new Email("aquim@email.com"),
                        Gender.Female,
                        df.parse("05-11-1998"),
                        new SnsNumber(12345678L),
                        new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"), "Porto", "Portugal"),
                        new PhoneNumber(921234567L),
                        new CitizenCard("00000001")),
                LocalDateTime.of(2019, 03, 28, 14, 33, 48),
                new VaccineType("code1",
                        "description1",
                        VaccineTechnology.LA
                ),
                new CommunityMassVaccinationCenter(
                        "PortoVC",
                        new PhoneNumber(921234567L),
                        new Address(
                                "street",
                                123,
                                new PostalCode("1239-452"),
                                "city",
                                "country"),
                        new Email("vaccination@gmail.com"),
                        new PhoneNumber(921234567L),
                        "vaccinationPT.com",
                        "8am",
                        "7pm",
                        10,
                        5,
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
                )

        )
        );
        this.snsUser = new SnsUser(
                        "user@email.com",
                        "Antonio Joaquim",
                        new Email("aquim@email.com"),
                        Gender.Female,
                        df.parse("05-11-1998"),
                        new SnsNumber(12345678L),
                        new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"), "Porto", "Portugal"),
                        new PhoneNumber(921234567L),
                        new CitizenCard("00000001")
        );
        this.vaccine = new Vaccine(
                new VaccineType(
                        "code1",
                        "desc1",
                        VaccineTechnology.LA
                ),
                "Comirnaty",
                "Pfizer");
        this.doseNumber = 1;
        this.lotNumber = "444AAA";
        this.administrationDate = LocalDateTime.of(2019, 03, 28, 15, 33, 48);
        this.leavingDate = LocalDateTime.of(2019, 03, 28, 15, 53, 48);
        this.vaccineAdministrationState = VaccineAdministrationState.IN_PROGRESS;

        this.vaccineAdministration = new VaccineAdministration(
                this.snsUserArrival,
                this.snsUser,
                this.vaccine,
                this.doseNumber,
                this.lotNumber,
                this.administrationDate,
                this.leavingDate);
    }

    @AfterEach
    public void dumpMemory()
    {
        this.snsUserArrival = null;
        this.snsUser = null;
        this.vaccine = null;
        this.doseNumber = 0;
        this.lotNumber = null;
        this.administrationDate = null;
        this.leavingDate = null;
    }

    @Test
    public void createVaccineAdministrationSuccessfully()
    {
        // Arrange + Act
        VaccineAdministration vaccineAdministration = this.vaccineAdministration;
        // Assert
        Assertions.assertNotNull(vaccineAdministration);
    }


    @Test
    public void createVaccineAdministrationWithoutSnsUserArrival()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministration(
                            null,
                            this.snsUser,
                            this.vaccine,
                            this.doseNumber,
                            this.lotNumber,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to register the vaccine administration, the sns user arrival must be registered!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutSnsUser()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministration(
                            this.snsUserArrival,
                           null,
                            this.vaccine,
                            this.doseNumber,
                            this.lotNumber,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to register the vaccine administration, the sns user cannot be null!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutVaccine()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministration(
                            this.snsUserArrival,
                            this.snsUser,
                            null,
                            this.doseNumber,
                            this.lotNumber,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to register the vaccine administration, the vaccine to administer must be selected!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutDoseNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministration(
                            this.snsUserArrival,
                            this.snsUser,
                            this.vaccine,
                            0,
                            this.lotNumber,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to register the vaccine administration, you must type the dose number!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutLotNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministration(
                            this.snsUserArrival,
                            this.snsUser,
                            this.vaccine,
                            this.doseNumber,
                            null,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to register the vaccine administration, you must type the lot number!"
        );
    }

    @Test
    public void createVaccineAdministrationWithoutAdministrationDate()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministration(
                            this.snsUserArrival,
                            this.snsUser,
                            this.vaccine,
                            this.doseNumber,
                            this.lotNumber,
                            null,
                            this.leavingDate
                    );
                },
                "In order to register the vaccine administration, the administration date and time must be defined!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutLeavingDate()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministration(
                            this.snsUserArrival,
                            this.snsUser,
                            this.vaccine,
                            this.doseNumber,
                            this.lotNumber,
                            this.administrationDate,
                            null
                    );
                },
                "In order to register the vaccine administration, the leaving date and time must be defined!"
        );
    }

}
