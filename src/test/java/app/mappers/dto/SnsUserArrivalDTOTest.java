package app.mappers.dto;

import app.domain.model.*;
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

public class SnsUserArrivalDTOTest {

    private LocalDateTime arrivalDate;
    private Long snsNumber;
    private VaccinationScheduleDTO vaccineSchedule;
    private SnsUserArrivalDTO snsUserArrival;


    @BeforeEach
    void setupObjects() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.arrivalDate = LocalDateTime.of(2019, 03, 28, 14, 33, 48);
        this.snsNumber = 12345678L;
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
                )

        );
        this.snsUserArrival = new SnsUserArrivalDTO(
                this.arrivalDate,
                this.snsNumber,
                this.vaccineSchedule
        );


    }


    @AfterEach
    public void dumpMemory() {
        this.arrivalDate = null;
        this.snsNumber = null;
        this.vaccineSchedule = null;
    }

    @Test
    public void createUserArrivalSuccessfully()
    {
        // Arrange + Act
        SnsUserArrivalDTO snsUserArrival = this.snsUserArrival;
        // Assert
        Assertions.assertNotNull(snsUserArrival);

    }

    @Test
    public void createArrivalWithoutVaccinationSchedule() {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new SnsUserArrivalDTO(
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
                    new SnsUserArrivalDTO(
                            this.arrivalDate,
                            0,
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
                    new SnsUserArrivalDTO(
                            null,
                            this.snsNumber,
                            this.vaccineSchedule);
                },
                "To successfully register an arrival, you must add a valida arrival date and time."
        );
    }



}
