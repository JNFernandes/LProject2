package app.domain.store;

import app.domain.model.CitizenCard;
import app.domain.model.Employee;
import app.domain.model.PhoneNumber;
import app.domain.shared.Address;
import app.domain.shared.NotificationHandler;
import app.domain.shared.PasswordGenerator;
import app.domain.shared.Role;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.*;

/**
 * Store class responsible for creating and storing the different Employees and Employees by a specific role.
 * ISEP'S Integrative Project 2021-2022, second semester.
 * @author João Fernandes <1210860@isep.ipp.pt> and Luis Matos <1211804@isep.ipp.pt></>
 */
public class EmployeeStore implements SerializableStore<Employee> {

    public static final String DEFAULT_SERIALIZATION_FILE = "employees.dat";

    private String serializationFile;
    private AuthFacade authFacade;
    private PasswordGenerator passwordGenerator;
    private SerializationUtil<Employee> serializationUtil;

    /**
     * list attribute for employeeList
     */
    private List<Employee> employeeList;

    /**
     * list atribute for roleList
     */
    private List<Role> roleList;

    /**
     * constructor of EmployeeStore that receives AuthFace as argument
     * @param authFacade
     */
    public EmployeeStore(AuthFacade authFacade)
    {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.authFacade = authFacade;
        this.serializationUtil = new SerializationUtil<>();
        this.passwordGenerator = new PasswordGenerator();
        this.employeeList =  new ArrayList<>(this.loadData());
        this.roleList = new ArrayList<>(Arrays.asList(Role.values()));
    }

    public EmployeeStore(AuthFacade authFacade,
                         String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.authFacade = authFacade;
        this.serializationUtil = new SerializationUtil<>();
        this.passwordGenerator = new PasswordGenerator();
        this.employeeList =  new ArrayList<>(this.loadData());
        this.roleList = new ArrayList<>(Arrays.asList(Role.values()));
    }

    public EmployeeStore(AuthFacade authFacade,
                         String baseSerializationFolder,
                         String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.authFacade = authFacade;
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);
        this.passwordGenerator = new PasswordGenerator();
        this.employeeList =  new ArrayList<>(this.loadData());
        this.roleList = new ArrayList<>(Arrays.asList(Role.values()));
    }

    /**
     * method for getting roleList
     * @return roleList
     */
    public List<Role> getRoleList()
    {
        List<Role> newRoleList = new ArrayList<>();
        for(Role role : roleList)
        {
            newRoleList.add(role);
        }
        return newRoleList;
    }

    /**
     * method for getting Employee List
     * @return employeeList
     */
    public List<Employee> getEmployeeList()
    {
        List<Employee> newEmployeeList = new ArrayList<>();
        for (Employee emp: employeeList)
        {
            newEmployeeList.add(emp);
        }
        return newEmployeeList;
    }

    /**
     * method for getting a list of Employees by Role
     * @param roleOption - it is a String parameter that results on the option given by the admin in the UI
     * @return a list of employees by a specific role specified by the admin
     */
    public List<Employee> getListEmployeeByRole(String roleOption) {
        List<Employee> ler = new ArrayList<>();
        for (Employee e: employeeList) {
            if (e.getRole().equalsIgnoreCase(roleOption)) {
                ler.add(e);
            }
        }
        return ler;
    }

    /**
     * Creates a new employee
     * @param name - of the employee
     * @param email - of the employee
     * @param address - of the employee
     * @param phoneNumber - of the employee
     * @param citizenCardNumber - of the employee
     * @param role - of the employee
     * @return new created object
     */
    public Employee createEmployee(String name, Email email, Address address, PhoneNumber phoneNumber, CitizenCard citizenCardNumber, Role role)
    {
        return new Employee(name, email, address, phoneNumber, citizenCardNumber, role);
    }

    /**
     * Adds employee to the user list of the application and to the list of employees
     *
     * @param emp  employee instance to be added
     * @return  true if successfully added to the user list and employee list or false otherwise
     */
    public boolean saveEmployee(Employee emp)
    {
        boolean addedUser = false;
        boolean addedEmployee = false;
        if (!existsEmployee(emp))
        {
            String pwd = passwordGenerator.generatePassword();
            String name = emp.getName();
            String email = emp.getEmail();
            String role = emp.getRole();

            addedUser = authFacade.addUserWithRole(name, email, pwd, role);

            addedEmployee = addEmployee(emp);

            NotificationHandler.sendEmployeeEmail("employees.txt","Access credentials", String.format("%nemail: %s%npwd:%s%n",emp.getEmail(), pwd));
        }

        boolean addedSuccessfully = (addedUser && addedEmployee);

/*        if (addedSuccessfully){
            this.saveData();
        }*/

        return addedSuccessfully;
    }

    /**
     * method for adding an Employee
     *
     * @param emp  employee object to be added to the list
     * @return  true if successfully added to the list or false otherwise
     */
    public boolean addEmployee(Employee emp)
    {
        return employeeList.add(emp);
    }

    public Optional<Employee> getEmployeeByEmail(String email)
    {
        Iterator<Employee> iterator = this.employeeList.iterator();
        Employee employee;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }
            employee = iterator.next();
        } while (!employee.getEmail().equals(email));

        return Optional.of(employee);
    }

    /**
     * Checks if an employee already exists in the list of the application users
     *
     * @param emp  employee instance to be checked
     * @return  true if already exists, false otherwise
     */
    public boolean existsEmployee(Employee emp)
    {
        return authFacade.existsUser(emp.getEmail());
    }

    @Override
    public Set<Employee> dataToSave() {
        return new HashSet<>(this.employeeList);
    }

    @Override
    public String serializationFileName() {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<Employee> serializationUtil() {
        return this.serializationUtil;
    }

}
