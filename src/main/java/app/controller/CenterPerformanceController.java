package app.controller;

import app.domain.model.Company;
import app.domain.model.VaccinationCenter;
import app.domain.store.SnsUserArrivalStore;
import app.domain.store.VaccinationCenterStore;
import app.domain.store.VaccineAdministrationStore;
import app.mappers.dto.VaccinationCenterDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CenterPerformanceController {

    private final App app;

    private final Company company;

    private SnsUserArrivalStore snsUserArrivalStore;

    private VaccinationCenterStore vaccinationCenterStore;

    private VaccineAdministrationStore vaccineAdministrationStore;



    public CenterPerformanceController() {
        this.app = App.getInstance();
        this.company = app.getCompany();
        this.snsUserArrivalStore = this.company.getSnsUserArrivalStore();
        this.vaccinationCenterStore = this.company.getVaccinationCenterStore();
        this.vaccineAdministrationStore = this.company.getVaccineAdministrationStore();
    }

    public List<LocalDate> getListDaysForPerformance(String vaccinationCenterEmail) {
        return this.snsUserArrivalStore.getListDaysForPerformance(vaccinationCenterEmail);
    }

    public long getVaccinationCenterWorkingPeriod(String vaccinationCenterEmail) {
        VaccinationCenter vaccinationCenter = this.vaccinationCenterStore.getByEmail(vaccinationCenterEmail).get();
        return vaccinationCenter.obtainWorkingPeriodMinutes();
    }

    public void calculatePerformanceBasedOnInterval(int timeResolution, LocalDate day, String vaccinationCenterEmail) {
        List<LocalDateTime> listArrivals = snsUserArrivalStore.getListArrivals(vaccinationCenterEmail);
        List<LocalDateTime> listLeavings = vaccineAdministrationStore.getListLeavings(vaccinationCenterEmail);
        VaccinationCenter vaccinationCenter = this.vaccinationCenterStore.getByEmail(vaccinationCenterEmail).get();
        vaccinationCenter.getAdapterForPerformanceAnalysis(timeResolution, day, listArrivals, listLeavings);
    }
}
