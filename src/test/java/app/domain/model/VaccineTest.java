package app.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Test Vaccine class")
public class VaccineTest
{
    private Vaccine vaccine1;
    private Vaccine vaccine2;

    private String vaccineName;
    private VaccineType vaccineType;
    private String vaccineManufacturer;

    @BeforeEach
    public void initTest()
    {
        vaccine1 = new Vaccine();

        vaccineName = "Comirnaty";
        vaccineType = new VaccineType("xxx", "Covid", VaccineTechnology.LA);
        vaccineManufacturer = "Pfizer";

        // vaccine with 2 doses
        List<Integer> minAgeExample = new ArrayList<>();
        minAgeExample.add(10);
        List<Integer> maxAgeExample = new ArrayList<>();
        maxAgeExample.add(40);
        List<Integer> numberOfDoses = new ArrayList<>();
        numberOfDoses.add(2);
        List<Double> dosage = new ArrayList<>();
        dosage.add(1.0);
        dosage.add(2.0);
        List<Integer> timeElapsed = new ArrayList<>();
        timeElapsed.add(7);

        vaccine2 = new Vaccine(vaccineType, vaccineName, vaccineManufacturer,
                1,minAgeExample,maxAgeExample,numberOfDoses,
                dosage,timeElapsed);
    }

    @AfterEach
    public void clean()
    {
        vaccine1 = null;

        vaccineName = null;
        vaccineType = null;
        vaccineManufacturer = null;
    }

    @Test
    @DisplayName("Use the default construtor")
    public void createSimpleVaccine()
    {
        String defaultName = "-no name-";
//        assertEquals(1, vaccine1.getVaccineId());
        assertEquals(defaultName, vaccine1.getVaccineName());
    }

    @Test
    @DisplayName("Test if objects are null")
    public void testNullObjects()
    {
        assertNotNull(vaccine1.getAdministrationProcess());
    }

    @Test
    @DisplayName("Test if parameters are ok")
    public void testParameters()
    {
        assertEquals("Pfizer", vaccine2.getVaccineManufacturer());
        assertEquals("Covid", vaccine2.getVaccineType().getDescription());
        assertEquals("Comirnaty", vaccine2.getVaccineName());
        assertEquals(1,vaccine2.getVaccineNumberOfAgeGroups());
        assertEquals(10,vaccine2.getMinAgeList().get(0));
        assertEquals(40,vaccine2.getMaxAgeList().get(0));
        assertEquals(2,vaccine2.getNumberOfDoses().get(0));
        assertEquals(7,vaccine2.getTimeElapsed().get(0));
        assertEquals(1,vaccine2.getDosage().get(0));
    }

    @Test
    @DisplayName("Verify is age is outside of limits defined")
    public void verifyIfAgeIsOutsideOfLimitsTest()
    {
        int ageOutsideForTest = 90;
        int ageInsideForTest = 35;
        assertFalse(vaccine2.isWithinAge(ageOutsideForTest));
        assertTrue(vaccine2.isWithinAge(ageInsideForTest));
    }


}
