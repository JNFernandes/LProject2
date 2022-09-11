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

public class VaccineAdministrationDTOTest {

    private SnsUserArrivalDTO snsUserArrival;
    private SnsUserDTO snsUser;
    private VaccineDTO vaccine;
    private int doseNumber;
    private String lotNumber;
    private LocalDateTime administrationDate;
    private LocalDateTime leavingDate;
    private VaccineAdministrationDTO vaccineAdministration;


    @BeforeEach
    public void setupObjects() throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.snsUserArrival = new SnsUserArrivalDTO(
                LocalDateTime.of(2020, 03, 28, 14, 33, 48),
                1345678L,
                new VaccinationScheduleDTO(
                        new SnsUserDTO(
                                "Antonio Joaquim",
                                "user@email.com",
                                Gender.Female,
                                df.parse("05-11-1998"),
                                1345678L,
                                new AddressDTO(
                                        "street",
                                        123,
                                        "1234-123",
                                        "city",
                                        "country"
                                ),
                                921234567L,
                                "00000000"),
                        LocalDateTime.of(2020, 03, 28, 14, 33, 48),
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
                )
        );
        this.snsUser = new SnsUserDTO("Antonio Joaquim",
                "user@email.com",
                Gender.Female,
                df.parse("05-11-1998"),
                1345678L,
                new AddressDTO(
                        "street",
                        123,
                        "1234-123",
                        "city",
                        "country"
                ),
                921234567L,
                "00000000");
        this.vaccine = new VaccineDTO(new VaccineType("xxx",
                "Covid",
                VaccineTechnology.LA),
                "Zier",
                "Toteil");
        this.doseNumber = 1;
        this.lotNumber = "456SSS";
        this.administrationDate = LocalDateTime.of(2020, 03, 28, 15, 33, 48);
        this.leavingDate = LocalDateTime.of(2020, 03, 28, 15, 53, 48);

        this.vaccineAdministration = new VaccineAdministrationDTO(
                this.snsUserArrival,
                this.snsUser,
                this.vaccine,
                this.doseNumber,
                this.lotNumber,
                this.administrationDate,
                this.leavingDate
        );


    }

    @AfterEach
    public void dumpMemory(){
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
        VaccineAdministrationDTO vaccineAdministration = this.vaccineAdministration;
        // Assert
        Assertions.assertNotNull(vaccineAdministration);
    }

    @Test
    public void createVaccineAdministrationWithoutSnsUserChosen()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministrationDTO(
                            this.snsUserArrival,
                            null,
                            this.vaccine,
                            this.doseNumber,
                            this.lotNumber,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to record the administration of a Vaccine, you must chose the SNS User first!"
        );
    }

    @Test
    public void createVaccineAdministrationWithoutTheSnsUserArrivalRegistered()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministrationDTO(
                            null,
                            this.snsUser,
                            this.vaccine,
                            this.doseNumber,
                            this.lotNumber,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to record the administration of a Vaccine, the SNS user must have an arrival registered first!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutChoosingVaccine()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministrationDTO(
                            this.snsUserArrival,
                            this.snsUser,
                            null,
                            this.doseNumber,
                            this.lotNumber,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to record the administration of a Vaccine, you must chose vaccine to administer first!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutDefiningTheDoseNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministrationDTO(
                            this.snsUserArrival,
                            this.snsUser,
                            this.vaccine,
                            0,
                            this.lotNumber,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to record the administration of a Vaccine, you must insert the dose number being administered first!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutInsertingVaccineLotNumber()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministrationDTO(
                            this.snsUserArrival,
                            this.snsUser,
                            this.vaccine,
                            this.doseNumber,
                            null,
                            this.administrationDate,
                            this.leavingDate
                    );
                },
                "In order to record the administration of a Vaccine, you must insert the vaccine's lot number first!"
        );
    }

    @Test
    public void createVaccineAdministrationWithoutAdministrationDate()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministrationDTO(
                            this.snsUserArrival,
                            this.snsUser,
                            this.vaccine,
                            this.doseNumber,
                            this.lotNumber,
                            null,
                            this.leavingDate
                    );
                },
                "In order to record the administration of a Vaccine, you must define the administration date and time first!"
        );
    }


    @Test
    public void createVaccineAdministrationWithoutLeavingDateAndTime()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineAdministrationDTO(
                            this.snsUserArrival,
                            this.snsUser,
                            this.vaccine,
                            this.doseNumber,
                            this.lotNumber,
                            this.administrationDate,
                           null
                    );
                },
                "In order to record the administration of a Vaccine, the leaving date and time must be defined!"
        );
    }
}
