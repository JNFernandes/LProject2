package app.controller;

import app.domain.model.Company;
import app.domain.model.Employee;
import app.domain.shared.Role;
import app.domain.store.EmployeeStore;

import java.util.List;
/**
 * Controller class responsible for establishing the layer between the domain model and the UI
 * ISEP'S Integrative Project 2021-2022, second semester.
 * @author João Fernandes <1210853@isep.ipp.pt>
 */

public class GetListEmployeeController {
    /**
     * atribute of App type
     */
    private final App app;
    /**
     * atribute of Company type
     */
    private Company company;
    /**
     * atribute of EmployeeStore type
     */
    private EmployeeStore empStore;


    /**
     * Constructor this is instanciated on the UI layer to establish the acess between UI and domain model
     */
    public GetListEmployeeController() {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.empStore = this.company.getEmployeeStore();
    }

    /**
     * method getRoleList of type Role to obtain the list of roles on the empStore
     * @return roleList
     */
    public List<Role> getRoleList() {
        return this.empStore.getRoleList();
    }

    /**
     * method getListEmployeeByRole of type Employee to obtain the list of Employees by a given role
     * @param roleOption - String that results of the option chosen by the admin in the UI
     * @return list of employees by role
     */

    public List<Employee> getListEmployeeByRole(String roleOption) {
        return this.empStore.getListEmployeeByRole(roleOption);
    }
}
