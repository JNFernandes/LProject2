package app.serialization;

import app.domain.model.*;
import app.domain.shared.Address;
import app.domain.shared.Gender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Set;

public class SerializationUtilTest {

    private static final String BASE_SERIALIZATION_FOLDER = "./test";
    private static final String TEST_DAT = "test.dat";

    private SnsUser snsUser;
    private SerializationUtil serializationUtil;

    @BeforeEach
    void setUp() throws ParseException
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        this.snsUser = new SnsUser(
                "user@email.com",
                "User Name",
                new Email("user@email.com"),
                Gender.Female,
                dateFormat.parse("01-09-1986"),
                new SnsNumber(12345678L),
                new Address(
                        "street",
                        123,
                        new PostalCode("1234-123"),
                        "city",
                        "country"),
                new PhoneNumber(921234567L),
                new CitizenCard("00000000"));
    }

    @AfterEach
    public void dumpMemory() throws IOException
    {
        this.snsUser = null;
        FileUtil.deleteFile(BASE_SERIALIZATION_FOLDER, TEST_DAT);
    }

    @Test
    public void snsUserSerializationAndDeSerialization()
    {
        // Arrange + Act
        this.serializationUtil = new SerializationUtil<SnsUser>(BASE_SERIALIZATION_FOLDER);
        this.serializationUtil.serialize(Set.of(this.snsUser), TEST_DAT);
        Set<SnsUser> snsUsers = this.serializationUtil.deserialize(BASE_SERIALIZATION_FOLDER, TEST_DAT);
        // Assert
        Assertions.assertEquals(1, snsUsers.size());

        SnsUser serializedSnsUser = snsUsers.iterator().next();
        Assertions.assertEquals(this.snsUser.getId(), serializedSnsUser.getId());
        Assertions.assertEquals(this.snsUser.getName(), serializedSnsUser.getName());
        Assertions.assertEquals(this.snsUser.getEmail(), serializedSnsUser.getEmail());
        Assertions.assertEquals(this.snsUser.getGender(), serializedSnsUser.getGender());
        Assertions.assertEquals(this.snsUser.getBirthdate(), serializedSnsUser.getBirthdate());
        Assertions.assertEquals(this.snsUser.getSnsNumber().getNumber(), serializedSnsUser.getSnsNumber().getNumber());
        Assertions.assertEquals(this.snsUser.getPhoneNumber().getNumber(), serializedSnsUser.getPhoneNumber().getNumber());
        Assertions.assertEquals(this.snsUser.getCitizenCard().getNumber(), serializedSnsUser.getCitizenCard().getNumber());
    }

}
