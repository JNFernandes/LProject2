package app.serialization;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializationUtil<T extends Serializable> {

    private static final Logger LOGGER = Logger.getLogger(SerializationUtil.class.getName());
    public static final String DEFAULT_BASE_SERIALIZATION_FOLDER = "./data"; //path por omissão da pasta onde guarda os ficheiros de serialização

    private final String baseSerializationFolder; //nome da pasta onde guarda os ficheiros de serialização

    //construtor por omissão
    public SerializationUtil()
    {
        this.baseSerializationFolder = DEFAULT_BASE_SERIALIZATION_FOLDER;
    }

    //construtor com nome da pasta passada por parâmetro
    public SerializationUtil(String baseSerializationFolder)
    {
        this.baseSerializationFolder = baseSerializationFolder;
    }

    /** Serialization method
     *
     * @param data List of data to serialize
     * @param fileName file name where data will be saved
     */
    public void serialize(Set<T> data, String fileName)// foi usado Set por ser a coleção mais usada nas stores
    {
        try
        {
            this.createBaseSerializationFolderIfNotExists(); // cria a pasta se não existir
        } catch (IOException exception)
        {
            LOGGER.log(Level.SEVERE, "Unable to create base folder.", exception.getCause()); //TODO: throw exception or keep log?
        }
        try (FileOutputStream fileStream = new FileOutputStream(this.baseSerializationFolder + "/" + fileName);
             ObjectOutputStream objectStream = new ObjectOutputStream(fileStream)) //ao criar as instâncias dos Streams dentro dos () do try as streams são automaticamente fechadas (try with resources - https://www.baeldung.com/java-try-with-resources)
        {
            objectStream.writeObject(data);
        } catch (IOException exception)
        {
            LOGGER.log(Level.SEVERE, "Exception occurred while serializing.", exception.getCause()); //TODO: throw exception?
        }
    }

    /**
     * Deserialize data
     * @param fileName file name where data will be saved
     * @return Set with data deserialized
     */
    public Set<T> deserialize(String fileName)
    {
        return this.deserialize(this.baseSerializationFolder, fileName);
    }

    public Set<T> deserialize(String baseSerializationFolder, String fileName)
    {
        Set<T> objectList = null;
        try (FileInputStream fileStream = new FileInputStream(baseSerializationFolder + "/" + fileName);
             ObjectInputStream objectStream = new ObjectInputStream(fileStream))
        {
            objectList = (Set<T>) objectStream.readObject();
        } catch (IOException | ClassNotFoundException exception)
        {
            LOGGER.log(Level.SEVERE, "Exception occurred while serializing.", exception.getCause()); //TODO: throw exception?
        }
        return objectList;
    }

    /**
     * Checks if file where serialization data will be stored exists.
     * @param fileName
     * @return true if file exists otherwise false
     */
    public boolean fileExists(String fileName)
    {
        return Files.exists(Path.of(this.baseSerializationFolder + "/" + fileName));
    }

    /**
     * Create the folder where serialization files will be stored if it not exists.
     * @throws IOException
     */
    private void createBaseSerializationFolderIfNotExists() throws IOException
    {
        Path baseFolderPath = Path.of(this.baseSerializationFolder);
        if (!Files.exists(baseFolderPath))
        {
            Files.createDirectories(baseFolderPath);
        }
    }

}
