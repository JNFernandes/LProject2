package app.domain.store;

import app.domain.model.SnsUser;
import app.domain.shared.Gender;
import app.domain.store.exception.SnsUserAlreadyExistsInStoreWithAttributeException;
import app.mappers.dto.AddressDTO;
import app.mappers.dto.SnsUserDTO;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.AuthFacade;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class SnsUserStoreTest {

    private String name;
    private String email;
    private Date birthDate;
    private SnsUserDTO snsUser;
    private AddressDTO address;
    private SnsUserStore snsUserStore;
    private Long snsNumber;
    private Long phoneNumber;
    private String citizenCard;
    private AuthFacade authFacade;
    private List<SnsUserDTO> snsUserDTOTestList;
    private List<SnsUserDTO> actualList;
    private List<SnsUserDTO> actualListFromExternalFile;
    private List<SnsUserDTO> expectedList;
    private List<SnsUserDTO> expectedListFromExternalFile;
    private String filePath;
    private String expectedClassName;
    private String actualClassName;
    private String serializationFileName;

    @BeforeEach
    void setUp() throws ParseException, FileNotFoundException
    {
        this.authFacade = new AuthFacade();
        this.serializationFileName = "sns_users_tests.dat";

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.name = "User Name";
        this.email = "user@email.com";
        this.birthDate = df.parse("01-09-1986");
        this.snsNumber = 12345678L;
        this.phoneNumber = 921234567L;
        this.citizenCard = "00000002";
        this.address = new AddressDTO(
                "street",
                123,
                "1234-123",
                "city",
                "country");
        this.snsUser = new SnsUserDTO(
                this.name,
                "user@email.com",
                Gender.Female,
                this.birthDate,
                this.snsNumber,
                this.address,
                this.phoneNumber,
                this.citizenCard);
        this.snsUserStore = new SnsUserStore(authFacade,  this.serializationFileName);
        this.filePath = "ForTesting.csv";

        // seting up list of users for testing
        AddressDTO a1 = new AddressDTO("Rua das Flores", 111, "1234-234", "Porto", "Portugal");
        AddressDTO a2 = new AddressDTO("Rua da Sorte", 32, "4200-123", "Aveiro", "Portugal");
        SnsUserDTO s1 = new SnsUserDTO("Joao",
                "joao@joao.pt",
                Gender.Male,
                df.parse("14-12-1999"),
                275341394L,
                a1,
                968885510L,
                "13999985");

        SnsUserDTO s2 = new SnsUserDTO("Ze Portuga",
                "ze@gmail.com",
                Gender.Male,
                df.parse("30-03-1974"),
                275341391L,
                a2,
                967569742L,
                "00000001");

        snsUserDTOTestList = new ArrayList<>();
        snsUserDTOTestList.add(s1);
        snsUserDTOTestList.add(s2);
        expectedList = new ArrayList<>();
        expectedList.add(s1);
        expectedList.add(s2);

        expectedListFromExternalFile = new ArrayList<>();
        expectedListFromExternalFile.add(s1);
        expectedListFromExternalFile.add(s1);
        expectedListFromExternalFile.add(s1);
        expectedListFromExternalFile.add(s1);

        //actualListFromExternalFile = snsUserStore.getListFromExternalModule(filePath);
        //actualList = snsUserStore.registerSnsUsersFromExternalModule(expectedList);

        actualClassName = SnsUserStore.getClassName(filePath);
        expectedClassName = "app.externalmodule.CsvFileReaderAPICommaAdapter";
    }

    @AfterEach
    public void dumpMemory() throws IOException
    {
        this.birthDate = null;
        this.snsUser = null;
        this.address = null;
        this.snsUserStore = null;
        this.snsUserDTOTestList = null;
        FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,  this.serializationFileName);
    }

    @Test
    public void createSnsUserSuccessfully()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = this.snsUser;
        SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
        // Assert
        Assertions.assertNotNull(snsUser);
        Assertions.assertEquals("user@email.com", snsUser.getId());
        Assertions.assertEquals("User Name", snsUser.getName());
        Assertions.assertEquals("user@email.com", snsUser.getEmail().getEmail());
        Assertions.assertEquals(Gender.Female, snsUser.getGender());
        Assertions.assertEquals(this.birthDate, snsUser.getBirthdate());
        Assertions.assertEquals(this.snsNumber, snsUser.getSnsNumber().getNumber());
        Assertions.assertEquals(this.address.getCity(), snsUser.getAddress().getCity());
        Assertions.assertEquals(this.address.getCountry(), snsUser.getAddress().getCountry());
        Assertions.assertEquals(this.address.getStreet(), snsUser.getAddress().getStreet());
        Assertions.assertEquals(this.address.getDoorNumber(), snsUser.getAddress().getDoorNumber());
        Assertions.assertEquals(this.address.getPostalCode(), snsUser.getAddress().getPostalCode().getNumber());
        Assertions.assertEquals(this.phoneNumber, snsUser.getPhoneNumber().getNumber());
        Assertions.assertEquals(this.citizenCard, snsUser.getCitizenCard().getNumber());
    }

    @Test
    public void addSnsUserSucessfully()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = this.snsUser;
        SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        // Assert
        Assertions.assertTrue(addedToStore);
    }

    @Test
    public void existsSnsUserSucessfully()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = this.snsUser;
        SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        boolean snsUserRetrieved = this.snsUserStore.existsSnsUser(snsUser.getEmail().getEmail());
        // Assert
        Assertions.assertTrue(snsUserRetrieved);
    }

    @Test
    public void notExistsSnsUser()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = this.snsUser;
        SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        boolean snsUserRetrieved = this.snsUserStore.existsSnsUser("email@notexists.com");
        // Assert
        Assertions.assertFalse(snsUserRetrieved);
    }

    @Test
    public void getSnsUserByIdSucessfully()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = this.snsUser;
        SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        Optional<SnsUser> snsUserRetrieved = this.snsUserStore.getSnsUserById(snsUser.getEmail().getEmail());
        // Assert
        if (snsUserRetrieved.isPresent())
        {
            Assertions.assertEquals(snsUser, snsUserRetrieved.get());
        }
    }

    @Test
    public void getSnsUserBySnsNumberSucessfully()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = this.snsUser;
        SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        Optional<SnsUser> snsUserRetrieved = this.snsUserStore.getSnsUserBySnsNumber(snsUser.getSnsNumber().getNumber());
        // Assert
        if (snsUserRetrieved.isPresent())
        {
            Assertions.assertEquals(snsUser, snsUserRetrieved.get());
        }
    }

    @Test
    public void getSnsUserByPhoneNumberSucessfully()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = this.snsUser;
        SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        Optional<SnsUser> snsUserRetrieved = this.snsUserStore.getSnsUserByPhoneNumber(snsUser.getPhoneNumber().getNumber());
        // Assert
        if (snsUserRetrieved.isPresent())
        {
            Assertions.assertEquals(snsUser, snsUserRetrieved.get());
        }
    }

    @Test
    public void getSnsUserByCitizenCardNumberSucessfully()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = this.snsUser;
        SnsUser snsUser = this.snsUserStore.createSnsUser(snsUserDTO);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        Optional<SnsUser> snsUserRetrieved = this.snsUserStore.getSnsUserByCitizenCardNumber(snsUser.getCitizenCard().getNumber());
        // Assert
        if (snsUserRetrieved.isPresent())
        {
            Assertions.assertEquals(snsUser, snsUserRetrieved.get());
        }
    }

    @Test
    public void checkSnsUserAttributesUniqueness()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = new SnsUserDTO(
                "User Name",
                "user2@email.com",
                Gender.Female,
                this.birthDate,
                123456782L,
                this.address,
                961234568L,
                "00000004");
        SnsUser snsUser = this.snsUserStore.createSnsUser(this.snsUser);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        // Assert
        Assertions.assertDoesNotThrow(
                () -> {
                    this.snsUserStore.existsSnsUser(snsUserDTO);
                }
        );
    }

    @Test
    public void checkSnsUserAttributesUniquenessWithExistingSnsNumber()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = new SnsUserDTO(
                "User Name",
                "user2@email.com",
                Gender.Female,
                this.birthDate,
                this.snsNumber,
                this.address,
                9612345672L,
                "00000003");
        SnsUser snsUser = this.snsUserStore.createSnsUser(this.snsUser);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        // Assert
        Exception exception = Assertions.assertThrows(
                SnsUserAlreadyExistsInStoreWithAttributeException.class,
                () -> {
                    this.snsUserStore.existsSnsUser(snsUserDTO);
                }
        );

        Assertions.assertEquals("A SNS user already exists with attribute SNS number equals to 12345678", exception.getMessage());
    }

    @Test
    public void checkSnsUserAttributesUniquenessWithExistingPhoneNumber()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = new SnsUserDTO(
                "User Name",
                "user2@email.com",
                Gender.Female,
                this.birthDate,
                123456782L,
                this.address,
                this.phoneNumber,
                "00000007");
        SnsUser snsUser = this.snsUserStore.createSnsUser(this.snsUser);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        // Assert
        Exception exception = Assertions.assertThrows(
                SnsUserAlreadyExistsInStoreWithAttributeException.class,
                () -> {
                    this.snsUserStore.existsSnsUser(snsUserDTO);
                }
        );

        Assertions.assertEquals("A SNS user already exists with attribute Phone number equals to 921234567", exception.getMessage());
    }

    @Test
    public void checkSnsUserAttributesUniquenessWithExistingCitizenCardNumber()
    {
        // Arrange + Act
        SnsUserDTO snsUserDTO = new SnsUserDTO(
                "User Name",
                "user2@email.com",
                Gender.Female,
                this.birthDate,
                123456782L,
                this.address,
                961234568L,
                this.citizenCard);
        SnsUser snsUser = this.snsUserStore.createSnsUser(this.snsUser);
        boolean addedToStore = this.snsUserStore.addSnsUser(snsUser);
        // Assert
        Exception exception = Assertions.assertThrows(
                SnsUserAlreadyExistsInStoreWithAttributeException.class,
                () -> {
                    this.snsUserStore.existsSnsUser(snsUserDTO);
                }
        );

        Assertions.assertEquals("A SNS user already exists with attribute Citizen card number equals to 00000002", exception.getMessage());
    }

    /*
    @Test
    public void registerSnsUsersFromExternalModuleTest()
    {
        Assertions.assertEquals(expectedList, actualList);
    }
    */

    /*
    @Test
    public void getListFromExternalModuleTest()
    {
        Assertions.assertEquals(expectedListFromExternalFile, actualListFromExternalFile);
    }
    */

    /*
    @Test
    public void getClassNameTest()
    {
        Assertions.assertEquals(expectedClassName, actualClassName);
    }
    */

}
