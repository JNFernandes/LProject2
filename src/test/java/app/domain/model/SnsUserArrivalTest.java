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

public class SnsUserArrivalTest {

    private LocalDateTime arrivalDate;
    private SnsNumber snsNumber;
    private VaccinationSchedule vaccineSchedule;
    private SnsUserArrival snsUserArrival;

    // verificar testes com o Luis

    @BeforeEach
    public void setUpObjetcts() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.arrivalDate = LocalDateTime.of(2019, 03, 28, 14, 33, 48);
        this.snsNumber = new SnsNumber(12345678L);
        this.vaccineSchedule = new VaccinationSchedule(
                new SnsUser(
                        "user@email.com",
                        "Antonio Joaquim",
                        new Email("aquim@email.com"),
                        Gender.Female,
                        df.parse("05-11-1998"),
                        this.snsNumber,
                        new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"), "Porto", "Portugal"),
                        new PhoneNumber(921234567L),
                        new CitizenCard("00000001")),
                this.arrivalDate,
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

        );
        this.snsUserArrival = new SnsUserArrival(
                this.arrivalDate,
                this.snsNumber,
                this.vaccineSchedule
        );

    }

    @AfterEach
    public void dumpMemory()
    {
        this.arrivalDate = null;
        this.snsNumber = null;
        this.vaccineSchedule = null;
        this.snsUserArrival = null;
    }


    @Test
    public void createSnsUserSuccessfully()
    {
        // Arrange + Act
        SnsUserArrival snsUserArrival = this.snsUserArrival;
        // Assert
        Assertions.assertNotNull(snsUserArrival);
    }

    @Test
    public void createArrivalWithoutVaccinationSchedule() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUserArrival(
                            this.arrivalDate,
                            this.snsNumber,
                            null);

                },
                "In order to register an arrival, the user must have a vaccine scheduled first!."
        );
    }




    @Test
    public void createArrivalWithNullSnsNumber() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUserArrival(
                            this.arrivalDate,
                            new SnsNumber(null),
                            this.vaccineSchedule);

                },
                "To successfully register an arrival, the SNS Number must be valid/not null."
        );
    }


    @Test
    public void createArrivalWithNullDateTime() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUserArrival(
                            null,
                            this.snsNumber,
                            this.vaccineSchedule);

                },
                "To successfully register an arrival, you must add a valida arrival date and time."
        );
    }






}
