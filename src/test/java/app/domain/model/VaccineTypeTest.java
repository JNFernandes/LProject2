package app.domain.model;


import org.junit.jupiter.api.*;

import java.text.ParseException;

@DisplayName("Test Vaccine Type")
public class VaccineTypeTest {

    private VaccineType vaccineType;
    private String code;
    private String description;
    private VaccineTechnology technology;

    @BeforeEach
    public void setupObjects() throws ParseException
    {
        this.code = "123TT";
        this.description = "Test";
        this.technology = VaccineTechnology.LA;
        this.vaccineType = new VaccineType(
                this.code,
                this.description,
                this.technology);
    }

    @AfterEach
    public void dumpMemory()
    {
        this.code = null;
        this.description = null;
        this.technology = null;
    }

    @Test
    public void createVaccineTypeSuccessfully()
    {
        // Arrange + Act
        VaccineType vaccineType = this.vaccineType;
        // Assert
        Assertions.assertNotNull(vaccineType);
    }

    @Test
    public void createVaccineTypeWithNullCode()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineType(
                            null,
                            this.description,
                            this.technology);
                },
                "Vaccine Type cannot have a null code."
        );
    }

    @Test
    public void createVaccineTypeWithBlankCode()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineType(
                            null,
                            this.description,
                            this.technology);
                },
                "Vaccine Type cannot have a blank code."
        );
    }

    @Test
    public void createVaccineTypeWithNullDescription()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineType(
                            this.code,
                            null,
                            this.technology);
                },
                "Vaccine Type cannot have a null description"
        );
    }

    @Test
    public void createVaccineTypeWithBlankDescription()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineType(
                            this.code,
                            "",
                            this.technology);
                },
                "Vaccine Type cannot have a blank description"
        );
    }

    @Test
    public void createVaccineTypeWithNullVaccineTechnology()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineType(
                            this.code,
                            this.description,
                            null);
                },
                "Vaccine Type cannot have a null Vaccine Technology."
        );
    }

    @Test
    public void createVaccineTypeWithNonexistingVaccineTechnology()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineType(
                            this.code,
                            this.description,
                            VaccineTechnology.valueOf("TTT"));
                },
                "Vaccine Type cannot have a nonexisting Vaccine Technology."
        );
    }

    @Test
    public void createVaccineTypeWithBlankVaccineTechnology()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineType(
                            this.code,
                            this.description,
                            VaccineTechnology.valueOf(""));
                },
                "Vaccine Type cannot have a blank Vaccine Technology."
        );
    }

    @Test
    public void createVaccineTypeWithNoAlphaNumericCode()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    new VaccineType(
                            "12345",
                            this.description,
                            this.technology);
                },
                "Vaccine Type Code cannot have only numbers."
        );
    }

}
