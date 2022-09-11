package app.controller;

import app.domain.model.Company;
import app.domain.model.VaccinationCenter;
import app.domain.store.VaccinationCenterStore;
import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.ArrayList;
import java.util.List;

public class CenterCoordinatorController
{
    private final App app;
    private final Company company;
    private final AuthFacade authFacade;
    private VaccinationCenterStore vaccinationCenterStore;

    public CenterCoordinatorController()
    {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.authFacade = this.company.getAuthFacade();
        this.vaccinationCenterStore = this.company.getVaccinationCenterStore();
    }


    public List<VaccinationCenter> getAllVaccinationCentersAssignedById(Email coordinatorEmail)
    {
        List<VaccinationCenter> listOfVaccinationCenter =
                (List<VaccinationCenter>) vaccinationCenterStore.getAll();
        String coordinatorEmailString = coordinatorEmail.toString();
        List<VaccinationCenter> listOfVaccinationCenterForCoordinator = new ArrayList<>();

        for (VaccinationCenter vaccinationCenter : listOfVaccinationCenter)
        {
            if (vaccinationCenter.getCenterCoordinator().getEmail().equalsIgnoreCase(coordinatorEmailString))
            {
                listOfVaccinationCenterForCoordinator.add(vaccinationCenter);
            }
        }

        return listOfVaccinationCenterForCoordinator;

    }
}
