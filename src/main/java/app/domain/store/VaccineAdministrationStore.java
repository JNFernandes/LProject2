package app.domain.store;

import app.controller.App;
import app.domain.model.*;
import app.mappers.VaccineAdministrationMapper;
import app.mappers.VaccineTypeMapper;
import app.mappers.dto.VaccineAdministrationDTO;
import app.mappers.dto.VaccineTypeDTO;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;
import app.ui.console.utils.Utils;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Store for the VaccinationSchedule class instances.
 */
public class VaccineAdministrationStore implements SerializableStore<VaccineAdministration>
{
    private Set<VaccineAdministration> vaccineAdministrationStore;
    public static final String DEFAULT_SERIALIZATION_FILE = "vaccineAdministration_schedules.dat";
    private VaccineAdministrationMapper vaccineAdministrationMapper;

    private String serializationFile;
    private Set<VaccineAdministration> vaccinationScheduleStore;
    private SerializationUtil<VaccineAdministration> serializationUtil;

    public VaccineAdministrationStore()
    {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.serializationUtil = new SerializationUtil<>();
        this.vaccineAdministrationStore = this.loadData();
        this.vaccineAdministrationMapper = new VaccineAdministrationMapper();
    }

    public Set<VaccineAdministration> getVaccineAdministrationStore()
    {
        return this.vaccineAdministrationStore;
    }

    public VaccineAdministrationStore(String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>();
        this.vaccineAdministrationStore = this.loadData();
        this.vaccineAdministrationMapper = new VaccineAdministrationMapper();
    }


    public VaccineAdministrationStore(String baseSerializationFolder,
                                      String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);
        this.vaccineAdministrationStore = this.loadData();
        this.vaccineAdministrationMapper = new VaccineAdministrationMapper();
    }

    public Set<VaccineAdministration> getListOfAllFullyVaccinatedUsersBetweenDates(Date startDate, Date endDate)
    {

        LocalDateTime startDateLocalDate = Utils.convertToLocalDateTimeViaInstant(startDate);
        LocalDateTime endDateLocalDate = Utils.convertToLocalDateTimeViaInstant(endDate);

        Set<VaccineAdministration> listOfFullyVaccinatedUsersBetweenDates = new HashSet<>();

        Iterator<VaccineAdministration> iterator = this.vaccineAdministrationStore.iterator();
        VaccineAdministration vaccineAdministration;


        while (iterator.hasNext())
        {
            vaccineAdministration = iterator.next();
            if (vaccineAdministration.getVaccineAdministrationState().equals(VaccineAdministrationState.FULLYVACCINATED)
                    && vaccineAdministration.getLeavingDate().isAfter(startDateLocalDate)
                    && vaccineAdministration.getLeavingDate().isBefore(endDateLocalDate))
            {
                listOfFullyVaccinatedUsersBetweenDates.add(vaccineAdministration);
            }
        }

        return listOfFullyVaccinatedUsersBetweenDates;
    }


    /**
     * Creates a vaccine administration for a SNS User
     * @param vaccineAdministrationDTO encapsulates the vaccine adminstration inputed information
     * @return VaccineAdministration
     */
    public VaccineAdministration createVaccineAdministration(VaccineAdministrationDTO vaccineAdministrationDTO)
    {
        return this.vaccineAdministrationMapper.toModel(vaccineAdministrationDTO);
    }


    /**
     * Adds a new vaccine administration to the vaccine adminstration store
     * @param vaccineAdministration the vaccine administration that will be added to the store
     * @return true if vaccine administration is added successfully, false if not
     */
    public boolean addVaccineAdministration(VaccineAdministration vaccineAdministration)
    {
        boolean addedSuccessfully = vaccineAdministration != null
                && this.vaccineAdministrationStore.add(vaccineAdministration);

//        if (addedSuccessfully)
//        {
//            this.saveData();
//        }

       return addedSuccessfully;
    }

    /**
     * Changes the vaccine adminstration state to fully vaccinated when user took all the doses needed
     * @param vaccineAdministrationDTO  encapsulates the vaccine administration inputed information
     * @return true if successfully, false if not
     */
    public boolean changeVaccinationState(VaccineAdministrationDTO vaccineAdministrationDTO)
    {
        VaccineAdministration vaccineAdministration = vaccineAdministrationMapper.toModel(vaccineAdministrationDTO);
        vaccineAdministration.setVaccineAdministrationState(VaccineAdministrationState.FULLYVACCINATED);
        return true;
    }

    /**
     * Changes the vaccination schedule to finalized when administration process is complete
     * @param vaccineAdministrationDTO encapsulates the vaccine administration inputed information
     * @return true if successfully, false if not
     */
    public boolean changeVaccinationScheduleState(VaccineAdministrationDTO vaccineAdministrationDTO)
    {
       VaccineAdministration vaccineAdministration = this.vaccineAdministrationMapper.toModel(vaccineAdministrationDTO);
       VaccinationSchedule vaccinationSchedule = vaccineAdministration.getSnsUserArrival().getVaccineSchedule();
        System.out.println(vaccinationSchedule.getState());
       vaccinationSchedule.setState(VaccinationScheduleState.FINALIZED);
        if(vaccinationSchedule.getState() == VaccinationScheduleState.FINALIZED){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method to get a list with the user that took vaccine
     * @param vaccineType vaccine type administered to the user
     * @param snsNumber sns user number of the user who took the vaccine
     * @return list of sns users
     */
    public List<VaccineAdministration> getSnsUserAdministrationsList(VaccineType vaccineType, long snsNumber)
    {
        List<VaccineAdministration> userAdministeredVaccines = new ArrayList<>();
        for (VaccineAdministration vaccineAdministration : this.vaccineAdministrationStore)
        {
            if (!vaccineAdministration.getSnsUserArrival().getVaccineSchedule().getVaccineType().equals(vaccineType) &&
                    vaccineAdministration.getSnsUserArrival().getSnsUserNumber().getNumber() != snsNumber)
            {
                userAdministeredVaccines.add(vaccineAdministration);
            }
        }
        return userAdministeredVaccines;
    }

    /**
     * returns the number of doses of a vaccine that the user took so far
     * @param vaccineTypeDTO encapsulated vaccine type
     * @param snsNumber sns user number
     * @return number of doses of a vaccine the sns user took so far
     */
    public int getNumberOfDosesTakenBySnsUser(VaccineTypeDTO vaccineTypeDTO, long snsNumber)
    {
        VaccineTypeMapper vaccineTypeMapper = new VaccineTypeMapper();
        VaccineType vaccineType = vaccineTypeMapper.toModel(vaccineTypeDTO);
        List<VaccineAdministration> userAdministeredVaccines = getSnsUserAdministrationsList(vaccineType,snsNumber);
        return userAdministeredVaccines.size();
    }

    /**
     * Method to get the list of the leaving dates from vaccination center
     * @param vaccinationCenterEmail email of vaccination center, unique identifier
     * @return list of leaving dates
     */
    public List<LocalDateTime> getListLeavings(String vaccinationCenterEmail) {
    List <LocalDateTime> listLeavings = new ArrayList<>();
    for (VaccineAdministration vaccineAdministration : vaccineAdministrationStore) {
        if (vaccineAdministration.getSnsUserArrival().getVaccineSchedule().getVaccinationCenter().hasEmail(vaccinationCenterEmail)) {
            listLeavings.add(vaccineAdministration.getLeavingDate());
        }
    }
    return listLeavings;
}





    @Override
    public Set<VaccineAdministration> dataToSave()
    {
        return this.vaccineAdministrationStore;
    }

    @Override
    public String serializationFileName()
    {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<VaccineAdministration> serializationUtil()
    {
        return this.serializationUtil;
    }

    /**
     * Method to get a list of fully vaccinated sns users between two dates
     * @param startDate start date of the period we want to get the data from
     * @param endDate end date of the period we want to get the data from
     * @return list of vaccine administration
     */
    public Set<VaccineAdministration> getListFullyVaccinatedUsersBetweenDatesOfUserCenter(Date startDate, Date endDate)
    {

        String currentCoordinatorId = App.getInstance().getCurrentUserSession().getUserId().getEmail();

        LocalDateTime startDateLocalDate = Utils.convertToLocalDateTimeViaInstant(startDate);
        LocalDateTime endDateLocalDate = Utils.convertToLocalDateTimeViaInstant(endDate);

        Set<VaccineAdministration> listOfFullyVaccinatedUsersBetweenDates = new HashSet<>();

        Iterator<VaccineAdministration> iterator = this.vaccineAdministrationStore.iterator();
        VaccineAdministration vaccineAdministration;

        while (iterator.hasNext())
        {
            vaccineAdministration = iterator.next();
            if (vaccineAdministration.getVaccinationCenter().getCenterCoordinator().getEmail().equalsIgnoreCase(currentCoordinatorId) &&
                    vaccineAdministration.getVaccineAdministrationState().equals(VaccineAdministrationState.FULLYVACCINATED) &&
                    vaccineAdministration.getLeavingDate().isAfter(startDateLocalDate) &&
                    vaccineAdministration.getLeavingDate().isBefore(endDateLocalDate))
            {
                listOfFullyVaccinatedUsersBetweenDates.add(vaccineAdministration);
            }
        }
        return listOfFullyVaccinatedUsersBetweenDates;
    }
}


