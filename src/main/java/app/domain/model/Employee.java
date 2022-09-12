package app.domain.model;

import app.domain.shared.Address;
import app.domain.shared.Role;
import org.apache.commons.lang3.StringUtils;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.IOException;
import java.io.Serializable;

/**
 * Models an Employee
 */
public class Employee implements Serializable
{

    private static final long serialVersionUID = -15300500366702715L;

    /**
     * Employee name (required)
     */
    private String name;

    /**
     * Employee email (required)
     */
    private Email email;

    /**
     * Employee address (required)
     */
    private Address address;

    /**
     * Employee phone number (required)
     */
    private PhoneNumber phoneNumber;

    /**
     * Employee citizen card number (required)
     */
    private CitizenCard citizenCardNumber;

    /**
     * Employee role (required)
     */
    private Role role;

    /**
     * Employee id
     */
    private String id;

    /**
     * Number of created employees
     */
    private static int employeeCount = 0 ;

    /**
     * Template of the employee id
     */
    private static final String ID_TEMPLATE = "EMP-";

    private VaccinationCenter vaccinationCenter;

    /**
     * Constructs an instance of Employee
     *
     * @param name  name of the employee
     * @param email  email of the employee
     * @param address  address of the employee
     * @param phoneNumber  phone number of the employee
     * @param citizenCardNumber  citizen card number of the employee
     * @param role  role of the employee (CenterCoordinator, Nurse, Receptionist)
     */
    public Employee(String name, Email email, Address address, PhoneNumber phoneNumber, CitizenCard citizenCardNumber, Role role)
    {
        if (!isValidName(name))
        {
            throw new IllegalArgumentException("Invalid name");
        }
        this.name = name;

        if (!isValidRole(role))
        {
            throw new IllegalArgumentException("Invalid role");
        }

        this.role = role;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.citizenCardNumber = citizenCardNumber;
        employeeCount++;
        this.id = String.format("%s%d", ID_TEMPLATE, employeeCount);

    }

    /**
     * Constructs an Employee instance from another Employee instance
     *
     * @param employee  an employee object
     */
    public Employee(Employee employee)
    {
        if (employee == null)
        {
            throw new IllegalArgumentException("Employee object cannot be null");
        }
        this.name = employee.name;
        this.address = new Address(employee.address);
        this.phoneNumber = new PhoneNumber(employee.phoneNumber);
        this.citizenCardNumber = new CitizenCard(employee.citizenCardNumber);
        this.role = employee.role;
        employeeCount++;
        this.id = String.format("%s%d", ID_TEMPLATE, employeeCount);
    }

    /**
     * Returns the name of the employee
     *
     * @return  name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the email of the employee
     *
     * @return email
     */
    public String getEmail()
    {
        return email.getEmail();
    }

    /**
     * Returns the address of the employee
     *
     * @return  address
     */
    public String getAddress()
    {
        return address.toString();
    }

    /**
     * Returns the phone number of the employee
     *
     * @return  phone number
     */
    public long getPhoneNumber()
    {
        return phoneNumber.getNumber();
    }

    /**
     * Returns the citizen card number of the employee
     *
     * @return  citizen card number
     */
    public String getCitizenCardNumber()
    {
        return citizenCardNumber.getNumber();
    }

    /**
     * Returns the role description of the employee
     *
     * @return  role description
     */
    public String getRole()
    {
        return role.getDescription();
    }

    /**
     * Returns the id the employee
     *
     * @return id
     */

    public String getId()
    {
        return id;
    }
    /**
     * Returns the total count of employees
     *
     * @return  employee count
     */
    public static int getEmployeeCount()
    {
        return employeeCount;
    }

    public void setVaccinationCenter(VaccinationCenter vaccinationCenter)
    {
        this.vaccinationCenter = vaccinationCenter;
    }

    public VaccinationCenter getVaccinationCenter()
    {
        return vaccinationCenter;
    }

    /**
     * Validates the name of the employee
     *
     * @param name  employee name
     * @return  true if valid, false otherwise
     */

    private boolean isValidName(String name)
    {
        // check if string is null
        if (name == null)
        {
            return false;
        }
        // check if name field is a string with spaces only
        if (StringUtils.isBlank(name))
        {
            return false;
        }
        return true;
    }

    /**
     * Validates employee role
     *
     * @param role  employee role (CenterCoordinator, Nurse, Receptionist)
     * @return  true if valid, false otherwise
     */
    private boolean isValidRole(Role role)
    {
        if (role == null)
        {
            return false;
        }
        return true;
    }


    @Override
    public String toString()
    {
        return String.format("[%s] Employee %s is a %s, with the email %s, %s, %d, with citizen card " +
                "number: %s",id, name, role.getDescription(), email.getEmail(), address.toString(), phoneNumber.getNumber(), citizenCardNumber.getNumber());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || (this.getClass() != o.getClass()) )
        {
            return false;
        }
        Employee emp = (Employee) o;
        return this.getEmail().equalsIgnoreCase(emp.getEmail());
    }

    private void writeObject(java.io.ObjectOutputStream stream) throws IOException
    {
        stream.writeObject(this.id);
        stream.writeObject(this.role);
        stream.writeObject(this.name);
        stream.writeObject(this.email.getEmail());
        stream.writeObject(this.address);
        stream.writeObject(this.phoneNumber);
        stream.writeObject(this.citizenCardNumber);
    }

    private void readObject(java.io.ObjectInputStream stream) throws IOException, ClassNotFoundException
    {
        this.id = (String) stream.readObject();
        this.role = (Role) stream.readObject();
        this.name = (String) stream.readObject();
        this.email = new Email((String) stream.readObject());
        this.address = (Address) stream.readObject();
        this.phoneNumber = (PhoneNumber) stream.readObject();
        this.citizenCardNumber = (CitizenCard) stream.readObject();
    }

}
