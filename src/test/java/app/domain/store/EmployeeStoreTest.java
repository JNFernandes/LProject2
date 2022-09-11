package app.domain.store;

import app.domain.model.CitizenCard;
import app.domain.model.Employee;
import app.domain.model.PhoneNumber;
import app.domain.model.PostalCode;
import app.domain.shared.Address;
import app.domain.shared.Constants;
import app.domain.shared.PasswordGenerator;
import app.domain.shared.Role;
import app.domain.store.EmployeeStore;
import app.serialization.FileUtil;
import app.serialization.SerializationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class respnsible for testing the EmployeeStore methods
 * ISEP'S Integrative Project 2021-2022, second semester.
 * @author João Fernandes <1210860@isep.ipp.pt> and Luis Matos <121104@isep.ipp.pt></>
 */

public class EmployeeStoreTest
{

    /**
     * employee atribute of Employee type
     */
    private Employee employee;

    /**
     * expected employee list of coordinators of type List<Employee></>
     */
    private List<Employee> expectedEmployeeListCenterCoordinator;

    /**
     * expected employee list of nurses of type List<Employee></>
     */
    private List<Employee> expectedEmployeeListNurse;

    /**
     * expected employee list of receptionists of type List<Employee></>
     */
    private List<Employee> expectedEmployeeListReceptionist;

    /**
     * actual employee list of coordinators of type List<Employee></>
     */
    private List <Employee> actualEmployeeListCenterCoordinator;

    /**
     * actual employee list of nurses of type List<Employee></>
     */
    private List <Employee> actualEmployeeListNurse;

    /**
     * actual employee list of receptionists of type List<Employee></>
     */
    private List <Employee> actualEmployeeListReceptionist;

    /**
     * actual role list of type List<Role></>
     */
    private List <Role> actualRoleList;

    /**
     * expected role list of type List<Role></>
     */
    private List <Role> expectedRoleList;

    /**
     * expected employeeList of type List<Employee></>
     */
    private List<Employee> expectedEmployeeList;

    /**
     * EmployeeStore class
     */
    private EmployeeStore empStore;

    /**
     * AuthFace class
     */
    private AuthFacade authFacade;

    private String serializationFileName;

    /**
     * method to set up the variables for the test
     */
    @BeforeEach
    public void setUp() {
        authFacade = new AuthFacade();
        this.serializationFileName = "employees_tests.dat";
        authFacade.addUserRole(Constants.ROLE_CENTER_COORDINATOR, Constants.ROLE_CENTER_COORDINATOR);
        authFacade.addUserRole(Constants.ROLE_RECEPTIONIST, Constants.ROLE_RECEPTIONIST);
        authFacade.addUserRole(Constants.ROLE_NURSE, Constants.ROLE_NURSE);

        empStore = new EmployeeStore(authFacade, this.serializationFileName);

        // to avoid bootstrap() duplication conflict

        // Setup test employee attributes
        Address addresE1 = new Address("Rua das Flores", 111, new PostalCode("1234-234"), "Porto", "Portugal");
        Address addresE2 = new Address("Rua da Boavista", 222, new PostalCode("1234-234"), "Lisboa", "Portugal");
        Address addresE3 = new Address("Rua do Carmo", 111, new PostalCode("1234-234"), "Portimão", "Portugal");
        Address addresE4 = new Address("Rua da Foz", 111, new PostalCode("1234-234"), "Vila Flor", "Portugal");
        Address addresE5 = new Address("Rua das Antas", 111, new PostalCode("1234-234"), "Bragança", "Portugal");
        Address addresE6 = new Address("Rua do Pelouro", 111, new PostalCode("1234-234"), "São João da Madeira", "Portugal");

        Employee e1 = new Employee("Joao",
                new Email("joao@sns.pt"),
                addresE1,
                new PhoneNumber(915555421L),
                new CitizenCard("10000000"),
                Role.CENTER_COORDINATOR);

        Employee e2 = new Employee("Andre",
                new Email("andre@sns.pt"),
                addresE2,
                new PhoneNumber(968885510L),
                new CitizenCard("20000000"),
                Role.CENTER_COORDINATOR);

        Employee e3 = new Employee("Diana",
                new Email("diana@sns.pt"),
                addresE3,
                new PhoneNumber(968885511L),
                new CitizenCard("30000000"),
                Role.NURSE);

        Employee e4 = new Employee("Joana",
                new Email("joana@sns.pt"),
                addresE4,
                new PhoneNumber(968885512L),
                new CitizenCard("40000000"),
                Role.NURSE);

        Employee e5 = new Employee("Antonio",
                new Email("antonio@sns.pt"),
                addresE5,
                new PhoneNumber(968885513L),
                new CitizenCard("50000000"),
                Role.RECEPTIONIST);

        Employee e6 = new Employee("Carolina",
                new Email("carolina@sns.pt"),
                addresE6,
                new PhoneNumber(968885514L),
                new CitizenCard("60000000"),
                Role.RECEPTIONIST);

        if (empStore.getEmployeeList().isEmpty())
        {
            empStore.addEmployee(e1);
            empStore.addEmployee(e2);
            empStore.addEmployee(e3);
            empStore.addEmployee(e4);
            empStore.addEmployee(e5);
            empStore.addEmployee(e6);
        }

        actualEmployeeListCenterCoordinator = empStore.getListEmployeeByRole(Role.CENTER_COORDINATOR.getDescription());
        actualEmployeeListNurse = empStore.getListEmployeeByRole(Role.NURSE.getDescription());
        actualEmployeeListReceptionist = empStore.getListEmployeeByRole(Role.RECEPTIONIST.getDescription());

        expectedEmployeeListCenterCoordinator = new ArrayList<>();
        expectedEmployeeListCenterCoordinator.add(e1);
        expectedEmployeeListCenterCoordinator.add(e2);

        expectedEmployeeListNurse = new ArrayList<>();
        expectedEmployeeListNurse.add(e3);
        expectedEmployeeListNurse.add(e4);

        expectedEmployeeListReceptionist = new ArrayList<>();
        expectedEmployeeListReceptionist.add(e5);
        expectedEmployeeListReceptionist.add(e6);

        actualRoleList = empStore.getRoleList();
        expectedRoleList = new ArrayList<>();
        expectedRoleList.add(Role.CENTER_COORDINATOR);
        expectedRoleList.add(Role.RECEPTIONIST);
        expectedRoleList.add(Role.NURSE);

        expectedEmployeeList = new ArrayList<>();
        expectedEmployeeList.add(e1);
        expectedEmployeeList.add(e2);
        expectedEmployeeList.add(e3);
        expectedEmployeeList.add(e4);
        expectedEmployeeList.add(e5);
        expectedEmployeeList.add(e6);

        this.authFacade.addUserWithRole("Joao", "joao@sns.pt", "123456", Constants.ROLE_CENTER_COORDINATOR);
    }

    /**
     * method to dumpMemory after running the test
     */
    @AfterEach
    public void dumpMemory() throws IOException {
        this.expectedEmployeeListCenterCoordinator = null;
        this.expectedEmployeeListNurse = null;
        this.expectedEmployeeListReceptionist = null;
        this.actualEmployeeListCenterCoordinator = null;
        this.actualEmployeeListNurse = null;
        this.actualEmployeeListReceptionist = null;
        this.empStore = null;
        this.authFacade = null;
        FileUtil.deleteFile(SerializationUtil.DEFAULT_BASE_SERIALIZATION_FOLDER,  this.serializationFileName);
    }

    /**
     * test method to assert the list of employees by role
     */
    @Test
    public void getListEmployeeByRole() {

        Assertions.assertEquals(expectedEmployeeListCenterCoordinator, actualEmployeeListCenterCoordinator);
        Assertions.assertEquals(expectedEmployeeListNurse, actualEmployeeListNurse);
        Assertions.assertEquals(expectedEmployeeListReceptionist, actualEmployeeListReceptionist);
    }

    /**
     * test method to assert the list of roles available
     */
    @Test
    public void getRoleList() {
        Assertions.assertEquals(expectedRoleList, actualRoleList);
    }

    @Test
    public void getEmployeeList()
    {
        Assertions.assertEquals(expectedEmployeeList, empStore.getEmployeeList());
    }

    @Test
    public void givenEmployeeAttributes_whenCreatingEmployee_returnNotNull()
    {
        Assertions.assertNotNull(empStore.createEmployee("Toni dos Tratores",
                new Email("toni_trator@sns.pt"),
                new Address("Rua do Verde", 222, new PostalCode("1234-234"), "Óbidos", "Portugal"),
                new PhoneNumber(915555421L),
                new CitizenCard("70000000"),
                Role.CENTER_COORDINATOR));
    }

    @Test
    public void givenEmployee_whenSavingEmployee_returnTrue()
    {
        Employee emp = new Employee("Toni dos Tratores",
                new Email("toni_trator@sns.pt"),
                new Address("Rua do Verde", 222, new PostalCode("1234-234"), "Óbidos", "Portugal"),
                new PhoneNumber(915555421L),
                new CitizenCard("80000000"),
                Role.CENTER_COORDINATOR);

        Assertions.assertTrue(empStore.saveEmployee(emp));
    }

    @Test
    public void givenExistentEmployee_whenSavingEmployee_returnFalse()
    {
        Employee emp = new Employee("Joao",
                new Email("joao@sns.pt"),
                new Address("Rua das Flores", 111, new PostalCode("1234-234"), "Porto", "Portugal"),
                new PhoneNumber(915555421L),
                new CitizenCard("90000000"),
                Role.CENTER_COORDINATOR);

        Assertions.assertFalse(empStore.saveEmployee(emp));
    }

    @Test
    public void givenEmployee_whenAddingEmployee_returnTrue()
    {
        Employee emp = new Employee("Toni dos Tratores",
                new Email("toni_trator@sns.pt"),
                new Address("Rua do Verde", 222, new PostalCode("1234-234"), "Óbidos", "Portugal"),
                new PhoneNumber(915555421L),
                new CitizenCard("11000000"),
                Role.CENTER_COORDINATOR);
        Assertions.assertTrue(empStore.addEmployee(emp));
    }

    @Test
    public void givenEmployee_whenCheckingExistingEmployee_returnFalse()
    {
        Employee emp = new Employee("Manel da Maquina",
                new Email("manel_maquina123@sns.pt"),
                new Address("Rua do Tinto", 222, new PostalCode("1234-234"), "Óbidos", "Portugal"),
                new PhoneNumber(915555421L),
                new CitizenCard("12000000"),
                Role.CENTER_COORDINATOR);

        boolean exists = empStore.existsEmployee(emp);

        Assertions.assertFalse(exists);
    }

    @Test
    public void givenEmployee_whenCheckingExistingEmployee_returnTrue()
    {
        Employee emp = new Employee("Joao",
                new Email("joao@sns.pt"),
                new Address("Rua das Flores", 111, new PostalCode("1234-234"), "Porto", "Portugal"),
                new PhoneNumber(915555421L),
                new CitizenCard("13000000"),
                Role.CENTER_COORDINATOR);

        boolean exists = empStore.existsEmployee(emp);

        Assertions.assertTrue(exists);
    }

}
