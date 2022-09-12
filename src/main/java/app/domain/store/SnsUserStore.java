package app.domain.store;

import app.domain.interfaces.LoadDataFromExternalSource;
import app.domain.model.SnsUser;
import app.domain.shared.Constants;
import app.domain.shared.PasswordGenerator;
import app.domain.store.exception.SnsUserAlreadyExistsInStoreWithAttributeException;
import app.externalmodule.InvalidDelimiterCsvFileException;
import app.mappers.SnsUserMapper;
import app.mappers.dto.SnsUserDTO;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;
import app.ui.console.LoadCsvFileUI;
import pt.isep.lei.esoft.auth.AuthFacade;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Store class responsible for creating and storing the SNS User.
 */
public class SnsUserStore implements SerializableStore<SnsUser> {

    public static final String DEFAULT_SERIALIZATION_FILE = "sns_users.dat";//nome por omissão do ficheiro para guardar dos dados serializar

    private final AuthFacade authFacade;
    private final Set<SnsUser> snsUserStore;
    private final SnsUserMapper snsUserMapper;
    private final String serializationFile; //este atributo permite que seja dado um nome diferente ao nome do ficheiro dado por omissão (p.e. não queremos serializar os ojectos criados nos testes, logo temos de ter outro ficheiro)
    private final PasswordGenerator passwordGenerator;
    private final SerializationUtil<SnsUser> serializationUtil; //instancia da classe generica usada para garantir a serialização

    public SnsUserStore(AuthFacade authFacade)
    {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.authFacade = authFacade;
        this.serializationUtil = new SerializationUtil<>(); // instanciação de um SerializationUtil
        this.snsUserStore = this.loadData(); //em vez de new hashSet carrega o ficheiro com os dados serializados anteriormente(caso exista, senão manter new hashSet)
        this.snsUserMapper = new SnsUserMapper();
        this.passwordGenerator = new PasswordGenerator();
    }

    public SnsUserStore(AuthFacade authFacade,
                        String serializationFile)
    {
        this.authFacade = authFacade;
        this.serializationUtil = new SerializationUtil<>(); // instanciação de um SerializationUtil
        this.snsUserStore = this.loadData();
        this.snsUserMapper = new SnsUserMapper();
        this.passwordGenerator = new PasswordGenerator();
        this.serializationFile = serializationFile;
    }

    public SnsUserStore(AuthFacade authFacade,
                        String baseSerializationFolder,
                        String serializationFile)
    {
        this.authFacade = authFacade;
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);// instanciação de um SerializationUtil com nome da pasta passada por parâmetro
        this.snsUserStore = this.loadData();
        this.snsUserMapper = new SnsUserMapper();
        this.passwordGenerator = new PasswordGenerator();
        this.serializationFile = serializationFile;
    }

    /**
     * Creates a new SNS User
     *
     * @param snsUserDTO encapsulates the SNS user inputed information
     * @return SnsUser
     */
    public SnsUser createSnsUser(SnsUserDTO snsUserDTO)
    {
        return this.snsUserMapper.toModel(snsUserDTO);
    }

    /**
     * Adds a Sns User to the SNS user store
     *
     * @param snsUser the SNS User that will be added to the store
     * @return true if the SNS User is added successfully to the store, otherwise, returns false
     */
    public boolean addSnsUser(SnsUser snsUser)
    {
        boolean addedSuccessfully = snsUser != null
                && !this.existsSnsUser(snsUser.getId(), snsUser.getSnsNumber().getNumber(), snsUser.getPhoneNumber().getNumber(), snsUser.getCitizenCard().getNumber())
                ? this.snsUserStore.add(snsUser)
                : false;

/*        if (addedSuccessfully)
        {
            this.saveData(); //serializa em caso de add com sucesso à store
        }*/

        return addedSuccessfully;
    }

    /**
     * Checks if SNS User exists by id
     *
     * @param id identifier of the SNS User
     * @return true if a SNS User is present, otherwise, returns false
     */
    public boolean existsSnsUser(String id)
    {
        Optional<SnsUser> result = this.getSnsUserById(id);
        return result.isPresent();
    }

    public boolean existsSnsUser(long snsNumber)
    {
        Optional<SnsUser> result = this.getSnsUserBySnsNumber(snsNumber);
        return result.isPresent();
    }

    /**
     * Checks if SNS User exists by his unique atributes: e-mail, SNS Number, phone number and citizen card
     * <ul>
     *  <li>checks if already exists a <strong>SNS user</strong> with given email</li>
     *  <li>checks if already exists a <strong>SNS user</strong> with given SNS number</li>
     *  <li>checks if already exists a <strong>SNS user</strong> with given phone number</li>
     *  <li>checks if already exists a <strong>SNS user</strong> with given citizen card number</li>
     * </ul>
     *
     * @param email             email of the SNS User (required,unique)
     * @param snsNumber         SNS Number of the SNS User (required,unique)
     * @param phoneNumber       phone number of the SNS User (required,unique)
     * @param citizenCardNumber citizen card of the SNS User (required,unique)
     * @return false if a SNS User with one of parameters exists, otherwise, throw an exception
     */
    public boolean existsSnsUser(String email, Long snsNumber, Long phoneNumber, String citizenCardNumber)
    {
        Optional<SnsUser> snsUserOptional;
        snsUserOptional = this.getSnsUserById(email);
        if (snsUserOptional.isPresent())
        {
            throw new SnsUserAlreadyExistsInStoreWithAttributeException("Email", String.valueOf(email));
        }
        snsUserOptional = this.getSnsUserBySnsNumber(snsNumber);
        if (snsUserOptional.isPresent())
        {
            throw new SnsUserAlreadyExistsInStoreWithAttributeException("SNS number", String.valueOf(snsNumber));
        }
        snsUserOptional = this.getSnsUserByPhoneNumber(phoneNumber);
        if (snsUserOptional.isPresent())
        {
            throw new SnsUserAlreadyExistsInStoreWithAttributeException("Phone number", String.valueOf(phoneNumber));
        }
        snsUserOptional = this.getSnsUserByCitizenCardNumber(citizenCardNumber);
        if (snsUserOptional.isPresent())
        {
            throw new SnsUserAlreadyExistsInStoreWithAttributeException("Citizen card number", String.valueOf(citizenCardNumber));
        }
        return false;
    }

    /**
     * Checks if SNS User exists
     * <ul>
     *  <li>checks if already exists a <strong>SNS user</strong> with given email</li>
     *  <li>checks if already exists a <strong>SNS user</strong> with given SNS number</li>
     *  <li>checks if already exists a <strong>SNS user</strong> with given phone number</li>
     *  <li>checks if already exists a <strong>SNS user</strong> with given citizen card number</li>
     * </ul>
     *
     * @param snsUserDTO encapsulates the SNS user inputed information
     * @return false if a SNS User with one of parameters exists, otherwise, throw an exception
     */
    public boolean existsSnsUser(SnsUserDTO snsUserDTO)
    {
        return this.existsSnsUser(snsUserDTO.getEmail(), snsUserDTO.getSnsNumber(), snsUserDTO.getPhoneNumber(), snsUserDTO.getCitizenCardNumber());
    }

    /**
     * Returns the SNS User by id
     *
     * @param email identifier of the SNS User - adopted the email for this purpose (required,unique)
     * @return Optional of SNS User
     */
    public Optional<SnsUser> getSnsUserById(String email)
    {
        Iterator<SnsUser> iterator = this.snsUserStore.iterator();
        SnsUser user;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }
            user = iterator.next();
        } while (!user.getId().equals(email));
        return Optional.of(user);
    }

    /**
     * Returns the SNS User by SNS Number
     *
     * @param snsNumber SNS Number of the SNS User (required,unique)
     * @return Optional of SNS User
     */
    public Optional<SnsUser> getSnsUserBySnsNumber(long snsNumber)
    {
        Iterator<SnsUser> iterator = this.snsUserStore.iterator();
        SnsUser user;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }
            user = iterator.next();
        } while (user.getSnsNumber().getNumber() != snsNumber);
        return Optional.of(user);
    }

    /**
     * Returns the SNS User by phone number
     *
     * @param phoneNumber phone number of the SNS User (required,unique)
     * @return Optional of SNS User
     */
    public Optional<SnsUser> getSnsUserByPhoneNumber(long phoneNumber)
    {
        Iterator<SnsUser> iterator = this.snsUserStore.iterator();
        SnsUser user;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }
            user = iterator.next();
        } while (user.getPhoneNumber().getNumber() != phoneNumber);
        return Optional.of(user);
    }

    /**
     * Returns the SNS User by citizen card number
     *
     * @param citizenCardNumber citizen card number of the SNS User (required,unique)
     * @return Optional of SNS User
     */
    public Optional<SnsUser> getSnsUserByCitizenCardNumber(String citizenCardNumber)
    {
        Iterator<SnsUser> iterator = this.snsUserStore.iterator();
        SnsUser user;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }
            user = iterator.next();
        } while (!user.getCitizenCard().getNumber().equals(citizenCardNumber));

        return Optional.of(user);
    }

    /**
     * Validates if the inputed user information is valid:
     * <ul>
     *  <li>System user:
     *      <ul>
     *          <li>checks if already exists a <strong>system user</strong> with given email</li>
     *      </ul>
     *  </li>
     *  <li>SNS user:
     *      <ul>
     *          <li>checks if already exists a <strong>SNS user</strong> with given email</li>
     *          <li>checks if already exists a <strong>SNS user</strong> with given SNS number</li>
     *          <li>checks if already exists a <strong>SNS user</strong> with given phone number</li>
     *          <li>checks if already exists a <strong>SNS user</strong> with given citizen card number</li>
     *      </ul>
     *  </li>
     * </ul>
     *
     * @param snsUserDTO encapsulates the SNS user inputed information
     * @return true if all the verifications passes, otherwise, returns false
     */
    public boolean checkIfUserAlreadyExists(SnsUserDTO snsUserDTO)
    {
        boolean userExists = this.authFacade.existsUser(snsUserDTO.getEmail());
        if (!userExists)
        {
            boolean snsUserExists;
            try
            {
                snsUserExists = existsSnsUser(snsUserDTO);
            } catch (SnsUserAlreadyExistsInStoreWithAttributeException exception)
            {
                // System.out.println(exception.getMessage());
                snsUserExists = true;
            }
            return snsUserExists;
        } else
        {
            return true;
        }
    }

    /**
     * Registers a new SNS user in the system and adds it to the SNS user store
     *
     * @param snsUserDTO encapsulates the SNS user inputed information
     * @return true if registration is successful, otherwise, returns false
     */
    public boolean registerSnsUser(SnsUserDTO snsUserDTO)
    {
        String password = this.passwordGenerator.generatePassword();
        //add system user
        boolean userAddedSuccessfully = this.authFacade.addUserWithRole(snsUserDTO.getName(), snsUserDTO.getEmail(), password, Constants.ROLE_SNS_USER);
        if (userAddedSuccessfully)
        {
            SnsUser snsUser = createSnsUser(snsUserDTO);
            boolean addedSuccessfully = addSnsUser(snsUser);
            return addedSuccessfully;
        } else
        {
            return false;
        }
    }

    /**
     * method that aims to get the list of sns users from external module. The class is accessed throgh java reflection
     * the classes name of the adapters are in the config.properties.
     *
     * @param fileName - the filePath
     * @return dtoList of objects that come from the external module
     * @throws FileNotFoundException exception
     */
    public List<SnsUserDTO> getListFromExternalModule(String fileName) throws FileNotFoundException
    {
        List<SnsUserDTO> dtoList = new ArrayList<>();
        try
        {
            String className = getClassName(fileName);
            Class<?> reflectionClass = Class.forName(className);
            LoadDataFromExternalSource instance = (LoadDataFromExternalSource) reflectionClass.getDeclaredConstructor().newInstance();
            dtoList = instance.readFileFromExternalSource(fileName);
            return dtoList;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException |
                 InstantiationException e)
        {
            System.out.println(e.getMessage());
            LoadCsvFileUI.closeApplication();
            return dtoList;
        }
    }

    /**
     * this method was implemented to obtain the class name that will trigger the external module adapter based
     * weather it is comma or semi colon file
     *
     * @param filePath - the parameter is the file path
     * @return a string with the name of the class
     * @throws FileNotFoundException that will be treated in the UI
     */
    public static String getClassName(String filePath) throws FileNotFoundException
    {
        try
        {
            Properties props = new Properties();
            File file = new File(filePath);
            Scanner read = new Scanner(file);
            String line = read.nextLine();
            read.close();
            String className;
            if (line.split(";").length > 1)
            {
                props.setProperty(Constants.PARAMS_EXTERNAL_MODULE1, "app.externalmodule.CsvFileReaderAPISemiColonAdapter");
                className = props.getProperty(Constants.PARAMS_EXTERNAL_MODULE1);
            } else if (line.split(",").length > 1)
            {
                props.setProperty(Constants.PARAMS_EXTERNAL_MODULE2, "app.externalmodule.CsvFileReaderAPICommaAdapter");
                className = props.getProperty(Constants.PARAMS_EXTERNAL_MODULE2);
            } else
            {
                throw new InvalidDelimiterCsvFileException("Delimiter is not valid");
            }
            try
            {
                InputStream in = new FileInputStream(Constants.PARAMS_FILENAME);
                props.load(in);
                in.close();
            } catch (IOException ex)
            {
                System.out.println(ex.getMessage());
                LoadCsvFileUI.closeApplication();
            }
            return className;
        } catch (InvalidDelimiterCsvFileException e)
        {
            System.out.println(e.getMessage());
            LoadCsvFileUI.closeApplication();
            return null;
        }
    }

    /**
     * method to register the sns users on the system from external module
     *
     * @param snsUserDTOList - list of DTOs comming from external module
     * @return dtoList to deliver to the controller to display on the UI
     */
    public List<SnsUserDTO> registerSnsUsersFromExternalModule(List<SnsUserDTO> snsUserDTOList)
    {
        List<SnsUserDTO> snsUserFinalList = new ArrayList<>();
        for (SnsUserDTO snsUserDTO : snsUserDTOList)
        {
            try
            {
                if (!checkIfUserAlreadyExists(snsUserDTO))
                {
                    registerSnsUser(snsUserDTO);
                    snsUserFinalList.add(snsUserDTO);
                }
            } catch (IllegalArgumentException e)
            {
                // System.out.println(e.getMessage());
            }
        }
        return snsUserFinalList;
    }

    @Override
    public Set<SnsUser> dataToSave()
    {
        return this.snsUserStore;
    }

    @Override
    public String serializationFileName()
    {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<SnsUser> serializationUtil()
    {
        return this.serializationUtil;
    }

}
