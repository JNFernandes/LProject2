package app.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VaccineAdministration implements Serializable
{

    private SnsUserArrival snsUserArrival;
    private SnsUser snsUser;
    private Vaccine vaccine;
    private int doseNumber;
    private String lotNumber;
    private LocalDateTime administrationDate;
    private LocalDateTime leavingDate;
    private VaccineAdministrationState vaccineAdministrationState;

    /**
     * Constructor for vaccine administration
     * @param snsUserArrival
     * @param doseNumber
     * @param administrationDate
     * @param leavingDate
     * @param vaccineAdministrationState
     */
    public VaccineAdministration(SnsUserArrival snsUserArrival,
                                 int doseNumber,
                                 LocalDateTime administrationDate,
                                 LocalDateTime leavingDate,
                                 VaccineAdministrationState vaccineAdministrationState)
    {
        this.snsUserArrival = snsUserArrival;
        this.doseNumber = doseNumber;
        this.administrationDate = administrationDate;
        this.leavingDate = leavingDate;
        this.vaccineAdministrationState = vaccineAdministrationState;
    }

    /**
     * Constructor for vaccine admnistration
     * @param snsUserArrival
     * @param snsUser
     * @param vaccine
     * @param doseNumber
     * @param lotNumber
     * @param administrationDate
     * @param leavingDate
     */
    public VaccineAdministration(SnsUserArrival snsUserArrival,
                                 SnsUser snsUser,
                                 Vaccine vaccine,
                                 int doseNumber,
                                 String lotNumber,
                                 LocalDateTime administrationDate,
                                 LocalDateTime leavingDate)
    {
        this.snsUserArrival = snsUserArrival;
        this.snsUser = snsUser;
        this.vaccine = vaccine;
        this.doseNumber = doseNumber;
        this.lotNumber = lotNumber;
        this.administrationDate = administrationDate;
        this.leavingDate = leavingDate;
        this.vaccineAdministrationState = VaccineAdministrationState.IN_PROGRESS;
    }

    public VaccineAdministration()
    {
    }

    public VaccineAdministrationState getVaccineAdministrationState()
    {
        return this.vaccineAdministrationState;
    }

    public void setVaccineAdministrationState(VaccineAdministrationState vaccineAdministrationState)
    {
        this.vaccineAdministrationState = vaccineAdministrationState;
    }


    public LocalDateTime getVaccinationScheduleDate()
    {
        return this.snsUserArrival.getArrivalDate();
    }

    public LocalDateTime getLeavingDate()
    {
        return leavingDate;
    }

    public LocalDateTime getAdministrationDate()
    {
        return administrationDate;
    }

    public String getLotNumber()
    {
        return lotNumber;
    }

    public int getDoseNumber()
    {
        return doseNumber;
    }


    public Vaccine getVaccine()
    {
        return vaccine;
    }

    public SnsUser getSnsUser()
    {
        return snsUser;
    }

    public SnsUserArrival getSnsUserArrival()
    {
        return snsUserArrival;
    }

    @Override
    public String toString()
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return "Administration Date:" + this.administrationDate.format(formatter)+ ", state=" + this.vaccineAdministrationState;

    }

    public VaccinationCenter getVaccinationCenter()
    {
        return snsUserArrival.getVaccineSchedule().getVaccinationCenter();
    }
}
