package app.domain.store;

import static org.junit.jupiter.api.Assertions.*;
import app.domain.model.Vaccine;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.*;

import java.io.IOException;

@DisplayName("Test VaccineStore class")
public class VaccineStoreTest
{
   private Vaccine vaccine1;
   private VaccineStore vcStore;
   private String serializationFileName;

   @BeforeEach
   public void initTest()
   {
      this.serializationFileName = "vaccines_tests.dat";
      vaccine1 = new Vaccine();
      vcStore = new VaccineStore(serializationFileName);
   }

   @AfterEach
   public void clean() throws IOException {
      vaccine1 = null;
      FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,  this.serializationFileName);
   }

   @Test
   @DisplayName("Validate vaccine behavior")
   public void validateVaccineTest()
   {
      assertTrue(vcStore.validateVaccine(vaccine1));
   }

   @Test
   @DisplayName("Validate vaccine saving")
   public void savaVaccineTest()
   {
      assertTrue(vcStore.saveVaccine(vaccine1));
   }

   @Test
   @DisplayName("Add Vaccine to Store")
   public void addVaccineTest()
   {
      boolean added = this.vcStore.saveVaccine(vaccine1);
      assertTrue(added);
   }

   @Test
   @DisplayName("List if not null after added")
   public void existVaccineInStoreTest()
   {
      boolean added = this.vcStore.saveVaccine(vaccine1);
      boolean nonNull = this.vcStore.getAll().isEmpty();
      assertFalse(nonNull);
   }

}
