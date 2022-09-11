package app.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;

@DisplayName("Test AdministrationProcess class")
public class AdministrationProcessTest
{
   private AdministrationProcess administrationProcessDefault1;
   private AdministrationProcess administrationProcessDefault2;

   private ArrayList<Integer> minAge;
   private ArrayList<Integer> maxAge;
   private ArrayList<Integer> numberOfDoses;
   private ArrayList<Double> dosage;
   private ArrayList<Integer> timeElapsed;

   private AdministrationProcess administrationProcess3;

   @BeforeEach
   public void initTest()
   {
      administrationProcessDefault1 = new AdministrationProcess();
      administrationProcessDefault2 = new AdministrationProcess();

      minAge = new ArrayList<Integer>();
      minAge.add(1);
      minAge.add(20);

      maxAge = new ArrayList<Integer>();
      maxAge.add(16);
      maxAge.add(30);

      numberOfDoses = new ArrayList<Integer>();
      numberOfDoses.add(1);
      numberOfDoses.add(1);

      dosage = new ArrayList<Double>();
      dosage.add(10.0);
      dosage.add(10.0);

      timeElapsed = new ArrayList<Integer>();

      administrationProcess3 = new AdministrationProcess(2,
              minAge, maxAge, numberOfDoses, dosage, timeElapsed);
   }

   @AfterEach
   public void dumpMemory()
   {
      administrationProcessDefault1 = null;
      administrationProcessDefault2 = null;
      administrationProcess3 = null;
   }

   @Test
   @DisplayName("Test default constructor")
   public void createSimpleAdministrationProcess()
   {
      assertEquals(1, administrationProcessDefault1.getVaccineNumberOfAgeGroups());
   }

   @Test
   @DisplayName("Test if objects are null")
   public void testNullObjects()
   {
      assertNotNull(administrationProcessDefault1.getMinAge());
      assertNotNull(administrationProcessDefault1.getMaxAge());
      assertNotNull(administrationProcessDefault1.getNumberOfDoses());
      assertNotNull(administrationProcessDefault1.getDosage());
      assertNotNull(administrationProcessDefault1.getTimeElapsed());
   }


   @Test
   @DisplayName("Test the number of age groups")
   public void testNumberOfAdministrationProcess()
   {
      assertEquals(2, administrationProcess3.getVaccineNumberOfAgeGroups());
   }
}


