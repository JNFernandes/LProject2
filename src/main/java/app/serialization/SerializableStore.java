package app.serialization;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

//interface genérica cujo parametro genérico tem de extend/implement Serializable
//interface que define o comportamento das stores que serializam os dados
public interface SerializableStore<T extends Serializable> {

    /**
     * Define the data to save
     *
     * @return the data to save
     */
    Set<T> dataToSave();

    /**
     * Define the file name where the data will be saved
     *
     * @return file name where the data will be saved
     */
    String serializationFileName();

    /**
     * Define the serialization util instance associated to the store to serialize
     *
     * @return the serialization util instance associated to the store to serialize
     */
    SerializationUtil<T> serializationUtil();

    //para poder ter implementações de métodos em interfaces o método tem de ser default
    /**
     * Save the data
     */
    default void saveData()
    {
        this.serializationUtil().serialize(this.dataToSave(), this.serializationFileName());
    }

    /**
     * Load the data previously serialized
     *
     * @return new empty HashSet if there is no previously serialized data, otherwise it returns the serialized data from file
     */
    default Set<T> loadData()
    {
        if (this.serializationUtil().fileExists(this.serializationFileName()))
        {
            return this.serializationUtil().deserialize(this.serializationFileName());
        }
        return new HashSet<>();
    }

}
