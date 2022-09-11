package app.mappers.dto;

import static org.junit.jupiter.api.Assertions.*;

import app.domain.model.VaccineTechnology;
import app.domain.model.VaccineType;
import org.junit.jupiter.api.*;


import java.util.List;

public class VaccineDTOTest
{
    private VaccineType vaccineType;
    private String vaccineName;
    private String vaccineManufacturer;

    private int numberOfAgeGroups;
    private List<Integer> minAge;
    private List<Integer> maxAge;
    private List<Integer> numberOfDoses;
    private List<Double> dosage;
    private List<Integer> timeElapsed;

    private VaccineDTO vaccineDTO;


    @BeforeEach
    public void setupObjects()
    {
        vaccineType = new VaccineType("xxx", "Covid",
                VaccineTechnology.LA);
        vaccineName = "Zier";
        vaccineManufacturer = "Toteil";
    }

    @AfterEach
    public void dumpMemory()
    {
        vaccineDTO = null;
    }

    @Test
    @DisplayName("Test if not null")
    public void createsSuccessfully()
    {
        vaccineDTO=new VaccineDTO(vaccineType, vaccineName, vaccineManufacturer);
        assertNotNull(vaccineDTO);
    }






}
