package app.ui.console;

import app.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class AdminUI implements Runnable {

    public AdminUI()
    {
    }

    public void run()
    {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create new Vaccination Center ", new NewVaccinationCenterUI()));
        options.add(new MenuItem("Types of Vaccine ", new SpecifyNewVaccineTypeUI()));
        options.add(new MenuItem("Get List of Employees by Role ", new GetListEmployeeUI()));
        options.add(new MenuItem("Register employee", new RegisterEmployeeUI()));
        options.add(new MenuItem("Specify a New Vaccine and its Administration Process", new SpecifyNewVaccineUI()));
        options.add(new MenuItem("Load a csv file of users", new LoadCsvFileUI()));

        int option = 0;
        do
        {
            option = Utils.showAndSelectIndex(options, "\n\nAdmin Menu:");

            if ((option >= 0) && (option < options.size()))
            {
                options.get(option).run();
            }
        } while (option != -1);
    }

}
