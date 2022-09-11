package app.domain.store;

import app.domain.model.AdverseReaction;
import app.domain.model.VaccineTechnology;
import app.domain.model.VaccineType;
import app.domain.shared.Gender;
import app.mappers.dto.*;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

public class AdverseReactionStoreTest {

    private SnsUserDTO snsUserDTO;
    private VaccineDTO vaccineDTO;
    private String adverseReaction;
    private VaccineType vaccineType;
    private AdverseReactionDTO adverseReactionDTO;
    private AdverseReactionStore adverseReactionStore;

    @BeforeEach
    void setUp() throws ParseException
    {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        this.adverseReaction = "He's dead!";
        this.snsUserDTO = new SnsUserDTO(
                "User Name",
                "user@email.com",
                Gender.Female,
                df.parse("01-09-1986"),
                12345678L,
                new AddressDTO(
                        "street",
                        123,
                        "1234-123",
                        "city",
                        "country"),
                921234567L,
                "00000000"
        );
        this.vaccineType = new VaccineType(
                "code1",
                "Description",
                VaccineTechnology.LA
        );
        this.vaccineDTO = new VaccineDTO(
                vaccineType,
                "Vaccine name",
                "Vaccine manufacturer",
                2,
                List.of(18),
                List.of(65),
                List.of(2),
                List.of(0.6),
                List.of(8)
        );
        this.adverseReactionDTO = new AdverseReactionDTO(
                this.adverseReaction,
                this.snsUserDTO,
                this.vaccineDTO);

        this.adverseReactionStore = new AdverseReactionStore("adverse_reactions_tests.dat");
    }

    @AfterEach
    public void dumpMemory() throws IOException
    {
        this.snsUserDTO = null;
        this.vaccineDTO = null;
        this.adverseReaction = null;
        this.vaccineType = null;
        this.adverseReactionDTO = null;
        FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER, "adverse_reactions_tests.dat");
    }

    @Test
    public void addAdverseReactionSucessfully()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(this.adverseReactionDTO);
        boolean addedToStore = this.adverseReactionStore.addAdverseReaction(adverseReaction);
        // Assert
        Assertions.assertTrue(addedToStore);
    }

    @Test
    public void addAdverseReaction_withMissingAdverseReactionDescription()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    this.adverseReactionDTO = new AdverseReactionDTO(
                            "",
                            this.snsUserDTO,
                            this.vaccineDTO);
                }
        );
    }

    @Test
    public void addAdverseReaction_withMissingSnsUser()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    this.adverseReactionDTO = new AdverseReactionDTO(
                            "He's dead!",
                            null,
                            this.vaccineDTO);
                }
        );
    }

    @Test
    public void addAdverseReaction_withMissingVaccine()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    this.adverseReactionDTO = new AdverseReactionDTO(
                            "He's dead!",
                            this.snsUserDTO,
                            null);
                }
        );
    }

    @Test
    public void addAdverseReaction_withNullAdverseReactionDescription()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    this.adverseReactionDTO = new AdverseReactionDTO(
                            null,
                            this.snsUserDTO,
                            this.vaccineDTO);
                }
        );
    }

    @Test
    public void addAdverseReaction_withAnAdverseReactionDescriptionWithMoreThan300Chars()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> {
                    this.adverseReactionDTO = new AdverseReactionDTO(
                            "Lorem ipsum dolor sit amet, consectetuer adipiscing elit. " +
                                    "Aenean commodo ligula eget dolor. Aenean massa. " +
                                    "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. " +
                                    "Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. " +
                                    "Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. " +
                                    "In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. " +
                                    "Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibu",
                            this.snsUserDTO,
                            this.vaccineDTO);
                }
        );
    }

    @Test
    public void getAllAdverseReactionsBySnsNumber_forExistinUser()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumber(12345678L);
        // Assert
        Assertions.assertTrue(snsUserAdverseReactionsDTO.isPresent());
    }

    @Test
    public void getAllAdverseReactionBySnsNumber_forUnexistingUser()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumber(87654321L);
        // Assert
        Assertions.assertFalse(snsUserAdverseReactionsDTO.isPresent());
    }

    @Test
    public void getAllAdverseReactionBySnsNumberAndVaccineName_forExistingUserAndVaccineName()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumberAndVaccineName(
                12345678L,
                "Vaccine name"
        );
        // Assert
        Assertions.assertTrue(snsUserAdverseReactionsDTO.isPresent());
    }

    @Test
    public void getAllAdverseReactionBySnsNumberAndVaccineName_forUnexistingUserAndExistingVaccineName()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumberAndVaccineName(
                87654321L,
                "Vaccine name"
        );
        // Assert
        Assertions.assertFalse(snsUserAdverseReactionsDTO.isPresent());
    }

    @Test
    public void getAllAdverseReactionBySnsNumberAndVaccineName_forExistingUserAndUnexistingVaccineName()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumberAndVaccineName(
                12345678L,
                "Vaccine name 1"
        );
        // Assert
        Assertions.assertFalse(snsUserAdverseReactionsDTO.isPresent());
    }

    @Test
    public void getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName_forExistingUserAndVaccineTypeAndVaccineName()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName(
                12345678L,
                "code1",
                "Vaccine name"
        );
        // Assert
        Assertions.assertTrue(snsUserAdverseReactionsDTO.isPresent());
    }

    @Test
    public void getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName_forUnexistingUserAndExistingVaccineTypeAndExistingVaccineName()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName(
                87654321L,
                "code1",
                "Vaccine name"
        );
        // Assert
        Assertions.assertFalse(snsUserAdverseReactionsDTO.isPresent());
    }

    @Test
    public void getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName_forExistingUserAndExistingVaccineTypeAndUnexistingVaccineName()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName(
                12345678L,
                "code1",
                "Vaccine name 1"
        );
        // Assert
        Assertions.assertFalse(snsUserAdverseReactionsDTO.isPresent());
    }

    @Test
    public void getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName_forExistingUserAndUnexistingVaccineTypeAndExistingVaccineName()
    {
        // Arrange + Act
        AdverseReaction adverseReaction = this.adverseReactionStore.createAdverseReaction(adverseReactionDTO);
        this.adverseReactionStore.addAdverseReaction(adverseReaction);
        Optional<SnsUserAdverseReactionsDTO> snsUserAdverseReactionsDTO = this.adverseReactionStore.getAllAdverseReactionBySnsNumberAndVaccineTypeAndVaccineName(
                12345678L,
                "code2",
                "Vaccine name 1"
        );
        // Assert
        Assertions.assertFalse(snsUserAdverseReactionsDTO.isPresent());
    }

}
