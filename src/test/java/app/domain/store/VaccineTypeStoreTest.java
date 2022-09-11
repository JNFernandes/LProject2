package app.domain.store;

import app.domain.model.VaccineTechnology;
import app.domain.model.VaccineType;
import app.domain.store.VaccineTypeStore;
import app.mappers.dto.VaccineTypeDTO;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

@DisplayName("Test VaccineStore class")
public class VaccineTypeStoreTest {

    private String code;
    private String description;
    private VaccineTechnology technology;
    private VaccineTypeDTO vaccineType;
    private VaccineTypeStore vaccineTypeStore;
    private String serializationFileName;

    @BeforeEach
    void setUp() throws ParseException
    {
        this.serializationFileName = "vaccine_types_tests.dat";
        this.code = "123TT";
        this.description = "teste";
        this.technology = VaccineTechnology.LA;

        this.vaccineType = new VaccineTypeDTO(
                this.code,
                this.description,
                this.technology);
        this.vaccineTypeStore = new VaccineTypeStore(this.serializationFileName);
    }

    @AfterEach
    public void dumpMemory() throws IOException {
        this.code = null;
        this.description = null;
        this.technology = null;
        this.vaccineTypeStore = null;
        FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,  this.serializationFileName);
    }

    @Test
    public void createVaccineTypeSucessfully()
    {
        VaccineTypeDTO vaccineTypeDTO = this.vaccineType;
        VaccineType vaccineType = this.vaccineTypeStore.createVaccineType(vaccineTypeDTO);
        Assertions.assertNotNull(vaccineType);
        Assertions.assertEquals(this.code, vaccineType.getCode());
        Assertions.assertEquals(this.description, vaccineType.getDescription());
        Assertions.assertEquals(this.technology, vaccineType.getTechnology());
    }

    @Test
    public void addVaccineTypeSucessfully()
    {
        VaccineTypeDTO vaccineTypeDTO = this.vaccineType;
        VaccineType vaccineType = this.vaccineTypeStore.createVaccineType(vaccineTypeDTO);
        boolean addedToStore = this.vaccineTypeStore.addVaccineType(vaccineType);
        Assertions.assertTrue(addedToStore);
    }

    @Test
    public void existsVaccineTypeSucessfully()
    {
        VaccineTypeDTO vaccineTypeDTO = this.vaccineType;
        VaccineType vaccineType = this.vaccineTypeStore.createVaccineType(vaccineTypeDTO);
        boolean addedToStore = this.vaccineTypeStore.addVaccineType(vaccineType);
        boolean vaccineTypeRetrieved = this.vaccineTypeStore.existsVaccineType(vaccineType.getCode());
        Assertions.assertTrue(vaccineTypeRetrieved);
    }

    @Test
    public void notExistsVaccineType()
    {
        VaccineTypeDTO vaccineTypeDTO = this.vaccineType;
        VaccineType vaccineType = this.vaccineTypeStore.createVaccineType(vaccineTypeDTO);
        boolean addedToStore = this.vaccineTypeStore.addVaccineType(vaccineType);
        boolean vaccineTypeRetrieved = this.vaccineTypeStore.existsVaccineType("12345");
        Assertions.assertFalse(vaccineTypeRetrieved);
    }

    @Test
    public void getVaccineTypeByCodeSucessfully()
    {
        VaccineTypeDTO vaccineTypeDTO = this.vaccineType;
        VaccineType vaccineType = this.vaccineTypeStore.createVaccineType(vaccineTypeDTO);
        boolean addedToStore = this.vaccineTypeStore.addVaccineType(vaccineType);
        Optional<VaccineType> vaccineTypeRetrieved = this.vaccineTypeStore.getVaccineTypeByCode(vaccineType.getCode());
        if (vaccineTypeRetrieved.isPresent())
        {
            Assertions.assertEquals(vaccineType, vaccineTypeRetrieved.get());
        }
    }

    @Test
    public void checkVaccineTypeCodeUniqueness()
    {
        VaccineTypeDTO vaccineTypeDTO = new VaccineTypeDTO(
                "123AB",
                this.description,
                this.technology);
        VaccineType vaccineType = this.vaccineTypeStore.createVaccineType(this.vaccineType);
        boolean addedToStore = this.vaccineTypeStore.addVaccineType(vaccineType);
        // Assert
        Assertions.assertDoesNotThrow(
                () -> {
                    this.vaccineTypeStore.existsVaccineType(vaccineTypeDTO);
                }
        );
    }
}

