package app.domain.model;

import app.domain.shared.Address;
import app.domain.shared.Gender;
import app.domain.shared.Role;
import org.junit.jupiter.api.*;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.PipedReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test VaccinationSchedule class")
public class VaccinationScheduleTest
{

    private SnsUser snsUser1;
    private LocalDateTime time1;
    private VaccineType vaccineType1;
    private CommunityMassVaccinationCenter vaccinationCenter;
    private Vaccine vaccine1;
    private VaccinationSchedule vaccinationSchedule;
    private VaccinationScheduleState state;

    private VaccinationSchedule vaccinationSchedule2;


    @BeforeEach
    public void initTest()
    {
        vaccineType1 = new VaccineType(
                "456PI",
                "COVID",
                VaccineTechnology.LA
        );

        /*
            Definition of User
         */
        Email email1 = new Email("snsuser@lei.sem2.pt");
        Calendar birthdateCalendar = Calendar.getInstance();
        birthdateCalendar.set(1985, 2, 10);
        Date birthdate1 = birthdateCalendar.getTime();
        SnsNumber snsNumber1 = new SnsNumber(11111111L);
        Address address1 = new Address(
                "Rua Direita",
                7,
                new PostalCode("4425-205"),
                "Braga",
                "Portugal"
        );
        PhoneNumber phoneNumber1 = new PhoneNumber(937654321L);
        CitizenCard citizenCard1 = new CitizenCard("00000000");

        snsUser1 = new SnsUser
                (
                        email1.getEmail(),
                        "Naida Navinda Navolta Pereira",
                        email1,
                        Gender.Male,
                        birthdate1,
                        snsNumber1,
                        address1,
                        phoneNumber1,
                        citizenCard1
                );

        /*
            Definiton of LocalDateTime
         */
        time1 = LocalDateTime.of(2022, 5, 5, 10, 0);


        /*
            Definition of VaccinaitonCenter
         */
        vaccinationCenter = new CommunityMassVaccinationCenter(
                "Porto HCVC",
                new PhoneNumber(921234567L),
                new Address("street", 123, new PostalCode("4350-098"), "city", "country"),
                new Email("vaccinationPorto@email.com"),
                new PhoneNumber(921234567L),
                "vaccinationPorto.com",
                "8:00 AM",
                "8:00 PM",
                10,
                2,
                new Employee("Antonio J.", new Email("antonioj@email.com"), new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"), "Porto", "Portugal"),
                        new PhoneNumber(921234567L),
                        new CitizenCard("00000000"),
                        Role.CENTER_COORDINATOR
                ), vaccineType1);

        /*
            Definition of Vaccine
         */
        // vaccine with 2 doses
        List<Integer> minAgeExample = new ArrayList<>();
        minAgeExample.add(10);
        List<Integer> maxAgeExample = new ArrayList<>();
        maxAgeExample.add(40);
        List<Integer> numberOfDoses = new ArrayList<>();
        numberOfDoses.add(1);
        List<Double> dosage = new ArrayList<>();
        dosage.add(1.0);
        dosage.add(2.0);
        List<Integer> timeElapsed = new ArrayList<>();
        timeElapsed.add(7);

        vaccine1 = new Vaccine(vaccineType1, "Cominarty",
                "Pfizer", 1, minAgeExample,
                maxAgeExample, numberOfDoses, dosage, timeElapsed);

        vaccinationSchedule = new VaccinationSchedule(snsUser1, time1, vaccineType1, vaccinationCenter);
        vaccinationSchedule2 = new VaccinationSchedule(snsUser1, time1, vaccineType1, vaccinationCenter,
                vaccine1);

    }

    @AfterEach
    public void clean()
    {
        snsUser1 = null;
        time1 = null;
        vaccineType1 = null;
        vaccinationCenter = null;
        vaccine1 = null;
    }

    @Test
    @DisplayName("Use the default construtor and check the values")
    public void createSimpleVaccine()
    {
        String correctVaccinationCenter = "Porto HCVC";

        assertEquals(VaccinationScheduleState.CREATED, vaccinationSchedule.getState());
        assertEquals(correctVaccinationCenter, vaccinationSchedule.getVaccinationCenter().getVaccinationCenterName());

    }

    @Test
    @DisplayName("Use other construtor and check if vaccine is properly defined")
    public void checkVaccineNonNullTest()
    {
        assertNotNull(vaccinationSchedule2.getVaccine());
        assertNotNull(vaccinationSchedule2.getSnsUser());
        assertNotNull(vaccinationSchedule2.getDate());
    }

    @Test
    @DisplayName("Set vaccine's state to IN_PROGRESS")
    public void setVaccineStateToINPROGRESSTest()
    {
        vaccinationSchedule2.setState(VaccinationScheduleState.IN_PROGRESS);
        assertNotEquals(VaccinationScheduleState.CREATED,vaccinationSchedule2.getState());
    }

    @Test
    @DisplayName("Check for null vaccine")
    public void testNullVaccine()
    {

        Vaccine nullVaccine = null;
        assertEquals(nullVaccine, vaccinationSchedule.getVaccine());
    }

    @Test
    @DisplayName("Test if objects are null")
    public void testNullObjects()
    {

        assertNotNull(vaccinationSchedule.getVaccineType());
    }

    @Test
    @DisplayName("Test if parameters are ok")
    public void testParameters()
    {
        assertEquals("COVID", vaccinationSchedule.getVaccineType().getDescription());
    }

    @Test
    @DisplayName("Test if null")
    public void testeIfNull()
    {
        assertNotNull(vaccinationSchedule2);
    }



}
