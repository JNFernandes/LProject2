package app.domain.store;

import app.domain.model.*;
import app.domain.shared.Address;
import app.domain.shared.Gender;
import app.domain.shared.Role;
import app.mappers.dto.*;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

public class VaccineAdministrationStoreTest {

    private SnsUserArrivalDTO snsUserArrival;
    private SnsUserDTO snsUser;
    private VaccineDTO vaccine;
    private int doseNumber;
    private String lotNumber;
    private LocalDateTime administrationDate;
    private LocalDateTime leavingDate;

    private String serializationFileName;
    private VaccineAdministrationDTO vaccineAdministrationDTO;
    private VaccineAdministrationStore vaccineAdministrationStore;



    @BeforeEach
    public void setupObjects() throws ParseException {
        this.serializationFileName = "vaccination_process_register_tests.dat";
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        this.snsUserArrival = new SnsUserArrivalDTO(
                LocalDateTime.of(2020, 03, 28, 14, 33, 48),
                1345678L,
                new VaccinationScheduleDTO(
                        new SnsUserDTO(
                                "Antonio Joaquim",
                                "user@email.com",
                                Gender.Female,
                                df.parse("05-11-1998"),
                                1345678L,
                                new AddressDTO(
                                        "street",
                                        123,
                                        "1234-123",
                                        "city",
                                        "country"
                                ),
                                921234567L,
                                "00000000"),
                        LocalDateTime.of(2020, 03, 28, 14, 33, 48),
                        new VaccineTypeDTO("code1",
                                "description1",
                                VaccineTechnology.LA
                        ),
                        new CommunityMassVaccinationCenterDTO(
                                "ISEP Vaccination Center",
                                921234567L,
                                new AddressDTO(
                                        "street",
                                        123,
                                        "1234-123",
                                        "city",
                                        "country"),
                                "vaccination@Braga.com",
                                921234567L,
                                "vaccinationPT.com",
                                "8:00 AM",
                                "8:00 PM",
                                2,
                                10,
                                new Employee(
                                        "Employee Name",
                                        new Email("centerCoordinator@gmail.com"),
                                        new Address("Rua x 125 Porto", 125, new PostalCode("1234-123"), "Porto", "Portugal"),
                                        new PhoneNumber(921234567L),
                                        new CitizenCard("00000000"),
                                        Role.CENTER_COORDINATOR
                                ),
                                new VaccineType(
                                        "code1",
                                        "Description",
                                        VaccineTechnology.LA
                                )
                        )
                )
        );
        this.snsUser = new SnsUserDTO("Antonio Joaquim",
                "user@email.com",
                Gender.Female,
                df.parse("05-11-1998"),
                1345678L,
                new AddressDTO(
                        "street",
                        123,
                        "1234-123",
                        "city",
                        "country"
                ),
                921234567L,
                "00000000");
        this.vaccine = new VaccineDTO(new VaccineType("xxx",
                "Covid",
                VaccineTechnology.LA),
                "Zier",
                "Toteil");
        this.doseNumber = 1;
        this.lotNumber = "456SSS";
        this.administrationDate = LocalDateTime.of(2020, 03, 28, 15, 33, 48);
        this.leavingDate = LocalDateTime.of(2020, 03, 28, 15, 53, 48);

        this.vaccineAdministrationDTO = new VaccineAdministrationDTO(
                this.snsUserArrival,
                this.snsUser,
                this.vaccine,
                this.doseNumber,
                this.lotNumber,
                this.administrationDate,
                this.leavingDate
        );

        this.vaccineAdministrationStore = new VaccineAdministrationStore(this.serializationFileName);


    }

    @AfterEach
    public void dumpMemory() throws IOException {
        this.snsUserArrival = null;
        this.snsUser = null;
        this.vaccine = null;
        this.doseNumber = 0;
        this.lotNumber = null;
        this.administrationDate = null;
        this.leavingDate = null;
        FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,  this.serializationFileName);

    }


    @Test
    public void addedSuccessfullyVaccineAdministration()
    {
        // Arrange + Act
        VaccineAdministrationDTO vaccineAdministrationDTO = this.vaccineAdministrationDTO;
        VaccineAdministration vaccineAdministration = this.vaccineAdministrationStore.createVaccineAdministration(vaccineAdministrationDTO);
        boolean addedToStoreSuccessfully = this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration);
        //Assert
        Assertions.assertTrue(addedToStoreSuccessfully);
    }


    @Test
    public void changeVaccineScheduleState()
    {
        // Arrange + Act
        VaccineAdministrationDTO vaccineAdministrationDTO = this.vaccineAdministrationDTO;
        VaccineAdministration vaccineAdministration = this.vaccineAdministrationStore.createVaccineAdministration(vaccineAdministrationDTO);
         this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration);
         boolean stateChanged = this.vaccineAdministrationStore.changeVaccinationScheduleState(vaccineAdministrationDTO);
        //Assert
        Assertions.assertTrue(stateChanged);
    }


    @Test
    public void changeVaccineAdministrationState()
    {
        // Arrange + Act
        VaccineAdministrationDTO vaccineAdministrationDTO = this.vaccineAdministrationDTO;
        VaccineAdministration vaccineAdministration = this.vaccineAdministrationStore.createVaccineAdministration(vaccineAdministrationDTO);
        this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration);
        boolean stateChanged = this.vaccineAdministrationStore.changeVaccinationState(vaccineAdministrationDTO);
        //Assert
        Assertions.assertTrue(stateChanged);
    }


    @Test
    public void getNumberOfDosesTakenByUser()
    {
        // Arrange + Act
        VaccineAdministrationDTO vaccineAdministrationDTO = this.vaccineAdministrationDTO;
        VaccineAdministration vaccineAdministration = this.vaccineAdministrationStore.createVaccineAdministration(vaccineAdministrationDTO);
        this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration);
        int doses = this.vaccineAdministrationStore.getNumberOfDosesTakenBySnsUser(vaccineAdministrationDTO.getSnsUserArrival().getVaccineSchedule().getVaccineType(), vaccineAdministrationDTO.getSnsUser().getSnsNumber());
        //Assert
        Assertions.assertEquals(0, doses);
    }




    @Test
    public void getListOfLeavingDates()
    {
        // Arrange + Act
        VaccineAdministrationDTO vaccineAdministrationDTO = this.vaccineAdministrationDTO;
        VaccineAdministration vaccineAdministration = this.vaccineAdministrationStore.createVaccineAdministration(vaccineAdministrationDTO);
        this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration);
        List<LocalDateTime> leavingDateList = this.vaccineAdministrationStore.getListLeavings(vaccineAdministration.getVaccinationCenter().getVaccinationCenterEmailAddress().getEmail());
        //Assert
        Assertions.assertNotNull(leavingDateList);
    }




    @Test
    public void getVaccineAdministrationList()
    {
        // Arrange + Act
        VaccineAdministrationDTO vaccineAdministrationDTO = this.vaccineAdministrationDTO;
        VaccineAdministration vaccineAdministration = this.vaccineAdministrationStore.createVaccineAdministration(vaccineAdministrationDTO);
        this.vaccineAdministrationStore.addVaccineAdministration(vaccineAdministration);
        List<VaccineAdministration> administrationList = this.vaccineAdministrationStore.getSnsUserAdministrationsList(vaccineAdministration.getSnsUserArrival().getVaccineSchedule().getVaccineType(), vaccineAdministration.getSnsUserArrival().getSnsUserNumber().getNumber());
        //Assert
        Assertions.assertNotNull(administrationList);
    }



}
