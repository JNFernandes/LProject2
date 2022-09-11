package app.ui.console;

import app.controller.GetListEmployeeController;
import app.domain.model.Employee;
import app.domain.shared.Role;
import app.ui.console.utils.Utils;
import java.util.List;

/**
 * UI class responsible for the dealing with user's input.
 * ISEP'S Integrative Project 2021-2022, second semester.
 * @author João Fernandes <1210853@isep.ipp.pt>
 */

public class GetListEmployeeUI implements Runnable {

    private GetListEmployeeController controller;

    public GetListEmployeeUI() {
        this.controller = new GetListEmployeeController();
    }


    @Override
    public void run() {
        boolean exit = false;
        while (!exit) {
            List<Role> roleOptions = controller.getRoleList();
            int option = 0;
            do {
                boolean askAgain = true;
                while (askAgain && option >=0 ) {
                    String headerOption = "Select the number of the role to get: ";
                    option = Utils.showAndSelectIndex(roleOptions, headerOption);
                        if ((option >= 0) && (option < roleOptions.size())) {
                            List<Employee> listEmployeesByRole = controller.getListEmployeeByRole(roleOptions.get(option).getDescription());
                            String headerListRole = String.format("List of Employees with role %s: ", roleOptions.get(option).getDescription());
                            showList(listEmployeesByRole, headerListRole);
                            if (listEmployeesByRole.isEmpty()) {
                                System.out.println("This list is currently empty!");
                            }
                            askAgain = Utils.confirm("Do you want to get another role list? (s/n)");
                            if (!askAgain) {
                                option = -1;
                            }
                        }
                    }
            } while (option != -1);
            exit = Utils.confirm("Exit to Main menu?(s/n)");
        }
}

    /**
     * method responsible for showing list of objects
     * @param list of objects (in this case Employees by Role)
     * @param header of the list
     */

    static public void showList(List list, String header)
    {
        System.out.println(header);

        int index = 0;
        for (Object o : list)
        {
            index++;

            System.out.println(index + ". " + o.toString());
        }
    }
}
