package app.domain.model;

import app.domain.shared.Address;
import app.domain.shared.Constants;
import app.domain.shared.Gender;
import app.domain.shared.Role;
import app.domain.store.*;
import app.domain.tasks.VaccinationCenterStatsRecorderRecurringTask;
import app.mappers.dto.*;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.apache.commons.lang3.StringUtils;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Company {

    private String designation;
    private AuthFacade authFacade;
    private VaccinationCenterStatsRecorderRecurringTask vaccinationCenterStatsRecorderRecurringTask;
    private SnsUserStore snsUserStore;
    private EmployeeStore employeeStore;
    private VaccineStore vaccineStore;
    private VaccinationCenterStore vaccinationCenterStore;
    private VaccineTypeStore vaccineTypeStore;
    private SnsUserArrivalStore snsUserArrivalStore;
    private VaccinationScheduleStore vaccinationScheduleStore;
    private VaccineAdministrationStore vaccineAdministrationStore;
    private AdverseReactionStore adverseReactionStore;
    private LegacyDataStore legacyDataStore;
    private VaccinationScheduleState state;

    public Company(String designation)
    {
        if (StringUtils.isBlank(designation))
            throw new IllegalArgumentException("Designation cannot be blank.");
        this.designation = designation;
        this.authFacade = new AuthFacade();
        this.snsUserStore = new SnsUserStore(authFacade);
        this.employeeStore = new EmployeeStore(authFacade);
        this.vaccineStore = new VaccineStore();
        this.vaccinationCenterStore = new VaccinationCenterStore();
        this.vaccineTypeStore = new VaccineTypeStore();
        this.snsUserArrivalStore = new SnsUserArrivalStore();
        this.vaccinationScheduleStore = new VaccinationScheduleStore();
        this.adverseReactionStore = new AdverseReactionStore();
        this.vaccineAdministrationStore = new VaccineAdministrationStore();
        this.legacyDataStore = new LegacyDataStore();

        this.authFacade.addUserRole(Constants.ROLE_ADMIN, Constants.ROLE_ADMIN);
        this.authFacade.addUserRole(Constants.ROLE_RECEPTIONIST, Constants.ROLE_RECEPTIONIST);
        this.authFacade.addUserRole(Constants.ROLE_SNS_USER, Constants.ROLE_SNS_USER);
        this.authFacade.addUserRole(Constants.ROLE_CENTER_COORDINATOR, Constants.ROLE_CENTER_COORDINATOR);
        this.authFacade.addUserRole(Constants.ROLE_NURSE, Constants.ROLE_NURSE);

        this.authFacade.addUserWithRole("Main Administrator", "admin@lei.sem2.pt", "123456", Constants.ROLE_ADMIN);
        this.authFacade.addUserWithRole("Adolfo Dias", "snsuser@lei.sem2.pt", "123456", Constants.ROLE_SNS_USER);
        this.authFacade.addUserWithRole("Albano", "snsuser2@lei.sem2.pt", "123456", Constants.ROLE_SNS_USER);
        this.authFacade.addUserWithRole("Asdrubal", "snsuser3@lei.sem2.pt", "123456", Constants.ROLE_SNS_USER);

        this.bootstrapSnsUsers();
        this.bootstrapEmployees();
        this.boostrapVaccinesTypes();
        this.bootstrapVaccines();
        this.bootstrapVaccinationCenters();
        this.bootstrapVaccinationSchedules();
        this.bootstrapSnsUsersArrivals();
        this.bootstrapVaccineAdministration();

        this.vaccinationCenterStatsRecorderRecurringTask = new VaccinationCenterStatsRecorderRecurringTask();
    }

    public String getDesignation()
    {
        return designation;
    }

    public AuthFacade getAuthFacade()
    {
        return authFacade;
    }

    public VaccineTypeStore vaccineTypeStore()
    {
        return vaccineTypeStore;
    }

    public SnsUserArrivalStore snsUserArrivalStore()
    {
        return snsUserArrivalStore;
    }

    public VaccinationScheduleStore getVaccinationScheduleStore()
    {
        return this.vaccinationScheduleStore;
    }

    /**
     * method responsible for accesing to the employee store
     *
     * @return empStore
     */
    public EmployeeStore getEmployeeStore()
    {
        return employeeStore;
    }

    public SnsUserStore getSnsUserStore()
    {
        return snsUserStore;
    }

    public VaccinationCenterStore getVaccinationCenterStore()
    {
        return vaccinationCenterStore;
    }

    public VaccineStore getVaccineStore()
    {
        return vaccineStore;
    }

    public VaccineTypeStore getVaccineTypeStore()
    {
        return vaccineTypeStore;
    }

    public SnsUserArrivalStore getSnsUserArrivalStore()
    {
        return snsUserArrivalStore;
    }

    public VaccineAdministrationStore getVaccineAdministrationStore()
    {
        return this.vaccineAdministrationStore;
    }


    public AdverseReactionStore getAdverseReactionStore()
    {
        return this.adverseReactionStore;
    }

    /**
     * Verifies if HCVaccination Centers exists and if not registers and adds it to the store
     *
     * @param healthCareVaccinationCenterDTO encapsulates the HC Vaccination Center input information
     * @return true if registration is successful, otherwise, returns false
     */
    public boolean registerHealthcareVaccinationCenter(HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO)
    {
        Optional<VaccinationCenter> vaccinationCenterOptional = this.vaccinationCenterStore.getByEmail(healthCareVaccinationCenterDTO.getVaccinationCenterEmailAddress());
        if (!vaccinationCenterOptional.isPresent())
        {
            VaccinationCenter vaccinationCenter = this.vaccinationCenterStore.createHealthcareVaccinationCenter(healthCareVaccinationCenterDTO);
            boolean addedSuccessfully = this.vaccinationCenterStore.addVaccinationCenter(vaccinationCenter);
            return addedSuccessfully;
        } else
        {
            return false;
        }
    }

    /**
     * Verifies if CMVaccination Centers exists and if not registers and adds it to the store
     *
     * @param communityMassVaccinationCenterDTO encapsulates the CMVaccination Center input information
     * @return true if registration is successful, otherwise, returns false
     */
    public boolean registerCommunityMassVaccinationCenter(CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO)
    {
        Optional<VaccinationCenter> vaccinationCenterOptional = this.vaccinationCenterStore.getByEmail(communityMassVaccinationCenterDTO.getVaccinationCenterEmailAddress());
        if (!vaccinationCenterOptional.isPresent())
        {
            VaccinationCenter vaccinationCenter = this.vaccinationCenterStore.createCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO);
            boolean addedSuccessfully = this.vaccinationCenterStore.addVaccinationCenter(vaccinationCenter);
            return addedSuccessfully;
        } else
        {
            return false;
        }
    }

    /**
     * Creation of Vaccine Type in Store
     *
     * @param vaccineTypeDTO
     * @return boolean with sucess of adding to the collection the new Vaccine Type
     */
    public boolean specifyNewVaccineType(VaccineTypeDTO vaccineTypeDTO)
    {
        VaccineType vaccineType = this.vaccineTypeStore.createVaccineType(vaccineTypeDTO);
        return this.vaccineTypeStore.addVaccineType(vaccineType);
    }

    /**
     * Register of Sns User Arrival in Store
     *
     * @param snsUserArrivalDTO
     * @return boolean with sucess of adding to the collection the Sns User Arrival
     */
    public boolean registerNewSnsUserArrival(SnsUserArrivalDTO snsUserArrivalDTO)
    {
        SnsUserArrival snsUserArrival = this.snsUserArrivalStore.createSnsUserArrival(snsUserArrivalDTO);
        changeStateOnSnsUserArrival(snsUserArrival);
        return this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival);
    }

    /**
     * Changes the state of sns user arrival in store
     *
     * @param snsUserArrival
     * @return boolean
     */
    public boolean changeStateOnSnsUserArrival(SnsUserArrival snsUserArrival)
    {
        return this.snsUserArrivalStore.changeState(snsUserArrival);
    }



    public boolean registerVaccineAdministration(VaccineAdministrationDTO vaccineAdministrationDTO)
    {
        VaccineAdministration vaccineAdministration = this.vaccineAdministrationStore.createVaccineAdministration(vaccineAdministrationDTO);
        boolean addedSuccessfully =  this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration);
        if(addedSuccessfully){

        }
        return addedSuccessfully;
    }



    /**
     * Company's create vaccine method.
     *
     * @param vaccineType              vaccine type
     * @param vaccineName              vaccine identification name
     * @param vaccineManufacturer      vaccina manufacturer
     * @param vaccineNumberOfAgeGroups quantity of age groups for the vaccine administration process
     * @param minAge                   minimum age of an age group
     * @param maxAge                   maximum age of an age group
     * @param numberOfDoses            number of doses for each age group
     * @param dosage                   dosage per take for each age group
     * @param timeElapsed              time elapsed between vaccine takes
     * @return true if the vaccine creation is successful
     */
    public Vaccine createVaccine(VaccineType vaccineType, String vaccineName, String vaccineManufacturer,
                                 int vaccineNumberOfAgeGroups, List<Integer> minAge, List<Integer> maxAge,
                                 List<Integer> numberOfDoses, List<Double> dosage, List<Integer> timeElapsed)
    {
        return vaccineStore.createVaccine(vaccineType, vaccineName, vaccineManufacturer,
                vaccineNumberOfAgeGroups, minAge, maxAge, numberOfDoses, dosage, timeElapsed);
    }

    public Vaccine createVaccine(VaccineDTO vaccineDTO)
    {
        return vaccineStore.createVaccine(vaccineDTO);
    }

    /**
     * Company's save vaccine method.
     *
     * @param vc Vaccine instance
     * @return true if the vaccine saving is successful
     */
    public boolean saveVaccine(Vaccine vc)
    {
        return this.vaccineStore.saveVaccine(vc);
    }

    public void saveAllStores()
    {
        this.snsUserStore.saveData();
        this.employeeStore.saveData();
        this.vaccineStore.saveData();
        this.vaccinationCenterStore.saveData();
        this.vaccineTypeStore.saveData();
        this.snsUserArrivalStore.saveData();
        this.vaccinationScheduleStore.saveData();
        this.adverseReactionStore.saveData();
        this.vaccineAdministrationStore.saveData();
    }

    private void bootstrapSnsUsers()
    {
        if (!FileUtil.fileExists(
                SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,
                SnsUserStore.DEFAULT_SERIALIZATION_FILE))
        {
            Calendar birthdateCalendar1 = Calendar.getInstance();
            birthdateCalendar1.set(1985, Calendar.MARCH, 10);
            Date birthdate1 = birthdateCalendar1.getTime();
            SnsUser snsUser1 = buildSnsUser(
                    new Email("snsuser@lei.sem2.pt"),
                    "Adolfo Dias",
                    Gender.Female,
                    birthdate1,
                    new SnsNumber(11111111L),
                    new Address(
                            "Rua Um",
                            7,
                            new PostalCode("4425-205"),
                            "Braga",
                            "Portugal"
                    ),
                    new PhoneNumber(937654321L),
                    new CitizenCard("11111111"));

            Calendar birthdateCalendar2 = Calendar.getInstance();
            birthdateCalendar2.set(1990, Calendar.MARCH, 10);
            Date birthdate2 = birthdateCalendar2.getTime();
            SnsUser snsUser2 = buildSnsUser(
                    new Email("snsuser2@lei.sem2.pt"),
                    "Albano",
                    Gender.Male,
                    birthdate2,
                    new SnsNumber(22222222L),
                    new Address(
                            "Rua Dois",
                            7,
                            new PostalCode("4425-205"),
                            "Viseu",
                            "Portugal"
                    ),
                    new PhoneNumber(927654321L),
                    new CitizenCard("22222222"));

            Calendar birthdateCalendar3 = Calendar.getInstance();
            birthdateCalendar3.set(1990, Calendar.APRIL, 10);
            Date birthdate3 = birthdateCalendar3.getTime();
            SnsUser snsUser3 = buildSnsUser(
                    new Email("snsuser3@lei.sem2.pt"),
                    "Asdrubal",
                    Gender.Male,
                    birthdate3,
                    new SnsNumber(33333333L),
                    new Address(
                            "Rua Três",
                            7,
                            new PostalCode("4435-305"),
                            "Viseu",
                            "Portugal"
                    ),
                    new PhoneNumber(937654331L),
                    new CitizenCard("33333333"));

            this.snsUserStore.addSnsUser(snsUser1);
            this.snsUserStore.addSnsUser(snsUser2);
            this.snsUserStore.addSnsUser(snsUser3);
        }
    }

    private void bootstrapEmployees()
    {
        if (!FileUtil.fileExists(
                SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,
                EmployeeStore.DEFAULT_SERIALIZATION_FILE))
        {
            Address addresE1 = new Address("Rua das Flores", 111, new PostalCode("1234-234"), "Porto", "Portugal");
            Address addresE2 = new Address("Rua da Boavista", 222, new PostalCode("1234-234"), "Lisboa", "Portugal");
            Address addresE3 = new Address("Rua do Carmo", 111, new PostalCode("1234-234"), "Portimão", "Portugal");
            Address addresE4 = new Address("Rua da Foz", 111, new PostalCode("1234-234"), "Vila Flor", "Portugal");
            Address addresE5 = new Address("Rua das Antas", 111, new PostalCode("1234-234"), "Brangaça", "Portugal");
            Address addresE6 = new Address("Rua do Pelouro", 111, new PostalCode("1234-234"), "São João da Madeira", "Portugal");

            Employee e1 = new Employee("Joao",
                    new Email("joao@sns.pt"),
                    addresE1,
                    new PhoneNumber(915555421L),
                    new CitizenCard("11112222"),
                    Role.CENTER_COORDINATOR);

            Employee e2 = new Employee("Andre",
                    new Email("coordinator@lei.sem2.pt"),
                    addresE2,
                    new PhoneNumber(968885510L),
                    new CitizenCard("22223333"),
                    Role.CENTER_COORDINATOR);

            Employee e3 = new Employee("Diana",
                    new Email("diana@sns.pt"),
                    addresE3,
                    new PhoneNumber(968885511L),
                    new CitizenCard("33334444"),
                    Role.NURSE);

            Employee e4 = new Employee("Joana",
                    new Email("nurse@lei.sem2.pt"),
                    addresE4,
                    new PhoneNumber(968885512L),
                    new CitizenCard("44445555"),
                    Role.NURSE);

            Employee e5 = new Employee("Antonio",
                    new Email("antonio@sns.pt"),
                    addresE5,
                    new PhoneNumber(968885513L),
                    new CitizenCard("55556666"),
                    Role.RECEPTIONIST);

            Employee e6 = new Employee("Carolina",
                    new Email("recep@lei.sem2.pt"),
                    addresE6,
                    new PhoneNumber(968885514L),
                    new CitizenCard("66667777"),
                    Role.RECEPTIONIST);

            this.employeeStore.saveEmployee(e1);
            this.employeeStore.saveEmployee(e2);
            this.employeeStore.saveEmployee(e3);
            this.employeeStore.saveEmployee(e4);
            this.employeeStore.saveEmployee(e5);
            this.employeeStore.saveEmployee(e6);
        } else
        {
            this.authFacade.addUserWithRole("Joao", "joao@sns.pt", "123456", Constants.ROLE_CENTER_COORDINATOR);
            this.authFacade.addUserWithRole("Andre", "coordinator@lei.sem2.pt", "123456", Constants.ROLE_CENTER_COORDINATOR);

            this.authFacade.addUserWithRole("Diana", "diana@sns.pt", "123456", Constants.ROLE_NURSE);
            this.authFacade.addUserWithRole("Joana", "nurse@lei.sem2.pt", "123456", Constants.ROLE_NURSE);

            this.authFacade.addUserWithRole("Antonio", "antonio@sns.pt", "123456", Constants.ROLE_RECEPTIONIST);
            this.authFacade.addUserWithRole("Carolina", "recep@lei.sem2.pt", "123456", Constants.ROLE_RECEPTIONIST);
        }
    }

    private void boostrapVaccinesTypes()
    {
        if (!FileUtil.fileExists(
                SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,
                VaccineTypeStore.DEFAULT_SERIALIZATION_FILE))
        {
            this.vaccineTypeStore.addVaccineType(
                    new VaccineType(
                            "456PI",
                            "COVID",
                            VaccineTechnology.LA
                    )
            );

            this.vaccineTypeStore.addVaccineType(
                    new VaccineType(
                            "123LA",
                            "Monkeypox",
                            VaccineTechnology.LA
                    )
            );
        }
    }

    private void bootstrapVaccines()
    {
        if (!FileUtil.fileExists(
                SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,
                VaccineStore.DEFAULT_SERIALIZATION_FILE))
        {
            // 1 -------------------------------------------------------------------------------------------------------
            List<Integer> minAgeExample = new ArrayList<>();
            minAgeExample.add(10);
            minAgeExample.add(41);

            List<Integer> maxAgeExample = new ArrayList<>();
            maxAgeExample.add(40);
            maxAgeExample.add(70);

            List<Integer> numberOfDoses = new ArrayList<>();
            numberOfDoses.add(2);
            numberOfDoses.add(2);

            List<Double> dosage = new ArrayList<>();
            dosage.add(5.0);
            dosage.add(10.0);
            dosage.add(15.0);
            dosage.add(20.0);

            List<Integer> timeElapsed = new ArrayList<>();
            timeElapsed.add(7);
            timeElapsed.add(7);


            VaccineType vaccineType1 = this.vaccineTypeStore.getVaccineTypeByCode("456PI").get();

            Vaccine vaccine1 = new Vaccine(
                    vaccineType1,
                    "Cominarty",
                    "Pfizer",
                    2,
                    minAgeExample,
                    maxAgeExample,
                    numberOfDoses,
                    dosage,
                    timeElapsed);

            this.vaccineStore.saveVaccine(vaccine1);

            // 2 -------------------------------------------------------------------------------------------------------
            List<Integer> minAgeExample2 = new ArrayList<>();
            minAgeExample.add(10);

            List<Integer> maxAgeExample2 = new ArrayList<>();
            maxAgeExample.add(40);

            List<Integer> numberOfDoses2 = new ArrayList<>();
            numberOfDoses.add(1);

            List<Double> dosage2 = new ArrayList<>();
            dosage.add(5.0);
//            dosage.add(2.0);

            List<Integer> timeElapsed2 = new ArrayList<>();
//            timeElapsed.add(7);

            VaccineType vaccineType2 = this.vaccineTypeStore.getVaccineTypeByCode("123LA").get();

            Vaccine vaccine2 = new Vaccine(
                    vaccineType2,
                    "Gorillaz",
                    "Clint Eastwood",
                    1,
                    minAgeExample2,
                    maxAgeExample2,
                    numberOfDoses2,
                    dosage2,
                    timeElapsed2);

            this.vaccineStore.saveVaccine(vaccine2);

            // 3 ------------------------------------------------------------------------------------------------------
            List<Integer> minAgeExample3 = new ArrayList<>();
            minAgeExample.add(10);

            List<Integer> maxAgeExample3 = new ArrayList<>();
            maxAgeExample.add(40);

            List<Integer> numberOfDoses3= new ArrayList<>();
            numberOfDoses.add(1);

            List<Double> dosage3 = new ArrayList<>();
            dosage.add(10.0);
//            dosage.add(2.0);

            List<Integer> timeElapsed3 = new ArrayList<>();
//            timeElapsed.add(7);

            VaccineType vaccineType3 = this.vaccineTypeStore.getVaccineTypeByCode("123LA").get();

            Vaccine vaccine3 = new Vaccine(
                    vaccineType3,
                    "Spikevax",
                    "Pfizer",
                    1,
                    minAgeExample3,
                    maxAgeExample3,
                    numberOfDoses3,
                    dosage3,
                    timeElapsed
            );

            this.vaccineStore.saveVaccine(vaccine3);

        }
    }

    private void bootstrapVaccinationCenters()
    {
        if (!FileUtil.fileExists(
                SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,
                VaccinationCenterStore.DEFAULT_SERIALIZATION_FILE))
        {
            VaccineType vaccineTypeCMVC1 = this.vaccineTypeStore.getVaccineTypeByCode("456PI").get();
            Employee coordinatorCMVC1 = this.employeeStore.getEmployeeByEmail("joao@sns.pt").get();
            CommunityMassVaccinationCenter cmvc1 = new CommunityMassVaccinationCenter(
                    "Porto HCVC",
                    new PhoneNumber(921234567L),
                    new Address("street", 123, new PostalCode("4350-098"), "city", "country"),
                    new Email("vaccinationPorto@email.com"),
                    new PhoneNumber(921234567L),
                    "vaccinationPorto.com",
                    "00:01 AM",
                    "11:59 PM",
                    10,
                    2,
                    coordinatorCMVC1,
                    vaccineTypeCMVC1
            );

            VaccineType vaccineTypeCMVC2 = this.vaccineTypeStore.getVaccineTypeByCode("123LA").get();
            Employee coordinatorCMVC2 = this.employeeStore.getEmployeeByEmail("coordinator@lei.sem2.pt").get();
            CommunityMassVaccinationCenter cmvc2 = new CommunityMassVaccinationCenter(
                    "Braga CMVC",
                    new PhoneNumber(921234567L),
                    new Address("street", 123, new PostalCode("4350-098"), "city", "country"),
                    new Email("vaccinationBraga@email.com"),
                    new PhoneNumber(921234567L),
                    "vaccinationBraga.com",
                    "00:01 AM",
                    "11:59 PM",
                    10,
                    2,
                    coordinatorCMVC2,
                    vaccineTypeCMVC2
            );

            this.vaccinationCenterStore.addVaccinationCenter(cmvc1);
            this.vaccinationCenterStore.addVaccinationCenter(cmvc2);
        }
    }

    private void bootstrapVaccinationSchedules()
    {
        if (!FileUtil.fileExists(
                SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,
                VaccinationScheduleStore.DEFAULT_SERIALIZATION_FILE)) {
//            SnsUser snsUser1 = this.snsUserStore.getSnsUserById("snsuser@lei.sem2.pt").get();
//
//            Vaccine vaccine1 = this.vaccineStore.getVaccineByName("Cominarty").get();
//
//            VaccineType vaccineType1 = this.vaccineTypeStore.getVaccineTypeByCode("456PI").get();
//
//            VaccinationCenter vaccinationCenter1 = this.vaccinationCenterStore.getVaccinationCenterByName("Porto HCVC").get();
//
//            LocalDateTime time1 = LocalDateTime.of(2022, 6, 11, 10, 0);
//
//            VaccinationSchedule appointment1 = new VaccinationSchedule(snsUser1, time1, vaccineType1, vaccinationCenter1, vaccine1);
//
//            appointment1.setId("Appointment1");
//            appointment1.setState(VaccinationScheduleState.FINALIZED);
//
//            this.vaccinationScheduleStore.addVaccinationSchedule(appointment1);
        }
    }

    private void bootstrapSnsUsersArrivals()
    {
        if (!FileUtil.fileExists(
                SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,
                SnsUserArrivalStore.DEFAULT_SERIALIZATION_FILE)) {
//            SnsUser user1 = this.snsUserStore.getSnsUserById("snsuser@lei.sem2.pt").get();
//            SnsUser user2 = this.snsUserStore.getSnsUserById("snsuser2@lei.sem2.pt").get();
//            SnsUser user3 = this.snsUserStore.getSnsUserById("snsuser3@lei.sem2.pt").get();
//
//            VaccineType vaccineType = this.vaccineTypeStore.getVaccineTypeByCode("456PI").get();
//            VaccineType vaccineType2 = this.vaccineTypeStore.getVaccineTypeByCode("123LA").get();
//
//            LocalDateTime arrivalDate = LocalDateTime.of(2019, 3, 28, 14, 33, 48);
//            LocalDateTime arrivalDate2 = LocalDateTime.of(2020, 3, 28, 14, 33, 48);
//
//            VaccinationCenter vaccinationCenter = this.vaccinationCenterStore.getVaccinationCenterByName("Porto HCVC").get();
//
//            VaccinationSchedule vaccineSchedule = new VaccinationSchedule(
//                    user1,
//                    arrivalDate,
//                    vaccineType,
//                    vaccinationCenter
//            );
//
//            VaccinationSchedule vaccineSchedule2 = new VaccinationSchedule(
//                    user2,
//                    arrivalDate.plusDays(10),
//                    vaccineType,
//                    vaccinationCenter
//            );
//
//            VaccinationSchedule vaccineSchedule3 = new VaccinationSchedule(
//                    user3,
//                    arrivalDate.plusDays(20),
//                    vaccineType,
//                    vaccinationCenter
//            );
//
//            VaccinationSchedule vaccineSchedule4 = new VaccinationSchedule(
//                    user1,
//                    arrivalDate2,
//                    vaccineType2,
//                    vaccinationCenter
//            );
//
//            VaccinationSchedule vaccineSchedule5 = new VaccinationSchedule(
//                    user2,
//                    arrivalDate2.plusDays(10),
//                    vaccineType2,
//                    vaccinationCenter
//            );
//
//            VaccinationSchedule vaccineSchedule6 = new VaccinationSchedule(
//                    user3,
//                    arrivalDate2.plusDays(20),
//                    vaccineType2,
//                    vaccinationCenter
//            );
//
//            this.vaccinationScheduleStore.addVaccinationSchedule(vaccineSchedule);
//            this.vaccinationScheduleStore.addVaccinationSchedule(vaccineSchedule2);
//            this.vaccinationScheduleStore.addVaccinationSchedule(vaccineSchedule3);
//            this.vaccinationScheduleStore.addVaccinationSchedule(vaccineSchedule4);
//            this.vaccinationScheduleStore.addVaccinationSchedule(vaccineSchedule5);
//            this.vaccinationScheduleStore.addVaccinationSchedule(vaccineSchedule6);
//
//            SnsUserArrival snsUserArrival = new SnsUserArrival(
//                    arrivalDate.plusHours(9),
//                    user1.getSnsNumber(),
//                    vaccineSchedule
//            );
//
//            SnsUserArrival snsUserArrival2 = new SnsUserArrival(
//                    arrivalDate.plusHours(1),
//                    user2.getSnsNumber(),
//                    vaccineSchedule2
//            );
//
//            SnsUserArrival snsUserArrival3 = new SnsUserArrival(
//                    arrivalDate.plusHours(6),
//                    user3.getSnsNumber(),
//                    vaccineSchedule3
//            );
//
//            SnsUserArrival snsUserArrival4 = new SnsUserArrival(
//                    arrivalDate2.plusHours(9),
//                    user1.getSnsNumber(),
//                    vaccineSchedule
//            );
//
//            SnsUserArrival snsUserArrival5 = new SnsUserArrival(
//                    arrivalDate2.plusHours(1),
//                    user2.getSnsNumber(),
//                    vaccineSchedule2
//            );
//
//            SnsUserArrival snsUserArrival6 = new SnsUserArrival(
//                    arrivalDate2.plusHours(6),
//                    user3.getSnsNumber(),
//                    vaccineSchedule3
//            );
//
//
//            this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival);
//            this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival2);
//            this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival3);
//            this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival4);
//            this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival5);
//            this.snsUserArrivalStore.addSnsUserArrival(snsUserArrival6);
        }
    }

    private void bootstrapVaccineAdministration()
    {
        if (!FileUtil.fileExists(
                SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,
                VaccineAdministrationStore.DEFAULT_SERIALIZATION_FILE)) {
            // 1 -------------------------------------------------------------------------------------------------------
//            SnsUserArrival snsUserArrival1 = this.snsUserArrivalStore.getSnsUserArrivalsBySnsNumber(11111111L).get();
//            int doseNumber = 1;
//            LocalDateTime arrivalDate = LocalDateTime.of(2019, 3, 28, 14, 33, 48);
//
//            LocalDateTime administrationDate = arrivalDate.plusHours(10);
//            LocalDateTime leavingDate = arrivalDate.plusHours(11);
//
//            VaccineAdministration vaccineAdministration1 =
//                    new VaccineAdministration(snsUserArrival1, doseNumber, administrationDate, leavingDate, VaccineAdministrationState.FULLYVACCINATED);
//
//
//            this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration1);
//
//            // 2 -------------------------------------------------------------------------------------------------------
//            SnsUserArrival snsUserArrival2 = this.snsUserArrivalStore.getSnsUserArrivalsBySnsNumber(22222222L).get();
//
//            LocalDateTime administrationDate2 = arrivalDate.plusHours(1);
//            LocalDateTime leavingDate2 = arrivalDate.plusHours(2);
//
//            VaccineAdministration vaccineAdministration2 =
//                    new VaccineAdministration(snsUserArrival2, doseNumber, administrationDate2, leavingDate2, VaccineAdministrationState.FULLYVACCINATED);
//
//            this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration2);
//
//            // 3 -------------------------------------------------------------------------------------------------------
//            SnsUserArrival snsUserArrival3 = this.snsUserArrivalStore.getSnsUserArrivalsBySnsNumber(33333333L).get();
//
//            LocalDateTime administrationDate3 = arrivalDate.plusHours(6);
//            LocalDateTime leavingDate3 = arrivalDate.plusHours(7);
//
//            VaccineAdministration vaccineAdministration3 =
//                    new VaccineAdministration(snsUserArrival3, doseNumber, administrationDate3, leavingDate3, VaccineAdministrationState.FULLYVACCINATED);
//
//            this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration3);
//
//            // 4 -------------------------------------------------------------------------------------------------------
//            LocalDateTime arrivalDate2 = LocalDateTime.of(2020, 3, 28, 14, 33, 48);
//
//            SnsUserArrival snsUserArrival4 = this.snsUserArrivalStore.getSnsUserArrivalsBySnsNumberAndArrivalDate(11111111L, arrivalDate2).get();
//            LocalDateTime administrationDate4 = arrivalDate2.plusHours(10);
//            LocalDateTime leavingDate4 = arrivalDate2.plusHours(11);
//
//            VaccineAdministration vaccineAdministration4 =
//                    new VaccineAdministration(snsUserArrival4, doseNumber, administrationDate4, leavingDate4, VaccineAdministrationState.FULLYVACCINATED);
//
//            this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration4);
//
//            // 5 -------------------------------------------------------------------------------------------------------
//            SnsUserArrival snsUserArrival5 = this.snsUserArrivalStore.getSnsUserArrivalsBySnsNumberAndArrivalDate(22222222L, arrivalDate2).get();
//
//            LocalDateTime administrationDate5 = arrivalDate2.plusHours(1);
//            LocalDateTime leavingDate5 = arrivalDate2.plusHours(2);
//
//            VaccineAdministration vaccineAdministration5 =
//                    new VaccineAdministration(snsUserArrival5, doseNumber, administrationDate5, leavingDate5, VaccineAdministrationState.FULLYVACCINATED);
//
//            this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration5);
//
//            // 6 -------------------------------------------------------------------------------------------------------
//            SnsUserArrival snsUserArrival6 = this.snsUserArrivalStore.getSnsUserArrivalsBySnsNumberAndArrivalDate(33333333L, arrivalDate2).get();
//
//            LocalDateTime administrationDate6 = administrationDate2.plusHours(6);
//            LocalDateTime leavingDate6 = administrationDate2.plusHours(7);
//
//            VaccineAdministration vaccineAdministration6 =
//                    new VaccineAdministration(snsUserArrival6, doseNumber, administrationDate6, leavingDate6, VaccineAdministrationState.FULLYVACCINATED);
//
//            this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration6);
        }
    }

    private SnsUser buildSnsUser(Email email,
                                 String name,
                                 Gender gender,
                                 Date birthdate,
                                 SnsNumber snsNumber,
                                 Address address,
                                 PhoneNumber phoneNumber,
                                 CitizenCard citizenCard)
    {
        return new SnsUser(
                email.getEmail(),
                name,
                email,
                gender,
                birthdate,
                snsNumber,
                address,
                phoneNumber,
                citizenCard
        );
    }

    public LegacyDataStore getLegacyDataStore() {return legacyDataStore;}

}