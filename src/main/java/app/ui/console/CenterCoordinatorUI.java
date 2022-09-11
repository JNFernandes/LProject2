package app.ui.console;

import app.controller.App;
import app.mappers.dto.VaccinationCenterDTO;
import app.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;

public class CenterCoordinatorUI implements Runnable
{

    public CenterCoordinatorUI()
    {

    }

    @Override

    public void run()
    {
        ChooseVaccinationCenterForCoordinatorUI chooseVaccinationCenterForCoordinatorUI
                = new ChooseVaccinationCenterForCoordinatorUI();

        boolean isCoordinatorAssignedToCenter = chooseVaccinationCenterForCoordinatorUI.isCoordinatorAssigned();


        if (isCoordinatorAssignedToCenter)
        {
            List<MenuItem> options = new ArrayList<MenuItem>();
            options.add(new MenuItem("Export the total number of fully vaccinated users", new ExportVaccinationStatisticsUI()));
            options.add(new MenuItem("Obtain Center Performance of a Vacination Center for a specific day", new CenterPerformanceUI()));
            options.add(new MenuItem("Import data from legacy system", new ImportLegacyDataUI()));
            int option = 0;
            do
            {
                option = Utils.showAndSelectIndex(options, "\n\nCenter Coordinator Menu:");

                if ((option >= 0) && (option < options.size()))
                {
                    options.get(option).run();
                }
            }
            while (option != -1);

        }else
        {
            System.out.println("The current coordinator has no center defined.");
        }

    }

}
