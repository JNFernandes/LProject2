package app.domain.model;

import app.domain.shared.Address;
import app.domain.shared.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.domain.model.Email;

public class EmployeeTest
{

    private Email email;
    private Address address;
    private CitizenCard citizenCard;
    private PhoneNumber phoneNumber;
    private Role role;
    private String name;

    @BeforeEach
    public void setUp()
    {
        // Setup test employee attributes
        name = "Zé Tugão";
        email = new Email("employee@email.com");
        address = new Address("Rua do Olival", 164, new PostalCode("1234-234"), "Fafe", "Portugal");
        citizenCard = new CitizenCard("00000000");
        role = Role.CENTER_COORDINATOR;

    }

    @AfterEach
    public void dumpMemory()
    {
        name = null;
        email = null;
        address = null;
        citizenCard = null;
        role = null;
    }

    @Test
    public void createValidEmployee()
    {
        // Create instance of employee
        Employee employee = new Employee(name, email, address, phoneNumber, citizenCard, role);
        Assertions.assertNotNull(employee);
    }

    @Test
    public void givenNullName_whenCreatingEmployee_thenExceptionIsReturned()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Employee(null, email, address, phoneNumber, citizenCard, role);
        });
    }

    @Test
    public void givenBlankName_whenCreatingEmployee_thenExceptionIsReturned()
    {
        Assertions.assertThrows(
                IllegalArgumentException.class,
                ()->{
                    new Employee(" ", email, address, phoneNumber, citizenCard, role);
                    },
                "Invalid name");
    }

    @Test
    public void givenNullEmail_whenCreatingEmployee_thenExceptionIsReturned()
    {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            new Employee(name, new Email(null), address, phoneNumber, citizenCard, role);
        });
    }

    @Test
    public void givenNullAddress_whenCreatingEmployee_thenExceptionIsReturned()
    {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            new Employee(name, email, new Address(null), phoneNumber, citizenCard, role);
        });
    }

    @Test
    public void givenInvalidPhoneNumber_whenCreatingEmployee_thenExceptionIsReturned()
    {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            new Employee(name, email, address, new PhoneNumber(0L), citizenCard, role);
        });
    }

    @Test
    public void givenNullCitizenCard_whenCreatingEmployee_thenExceptionIsReturned()
    {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            new Employee(name, email, address, phoneNumber, new CitizenCard((String) null), role);
        });
    }

    @Test
    public void givenNullRole_whenCreatingEmployee_thenExceptionIsReturned()
    {
        Assertions.assertThrows(IllegalArgumentException.class, ()->{
            new Employee(name, email, address, phoneNumber, citizenCard, null);
        });
    }

}
