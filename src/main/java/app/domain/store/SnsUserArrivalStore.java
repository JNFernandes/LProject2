package app.domain.store;

import app.domain.model.SnsUserArrival;
import app.domain.model.VaccinationSchedule;
import app.domain.model.VaccinationScheduleState;
import app.domain.store.exception.SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion;
import app.mappers.SnsUserArrivalMapper;
import app.mappers.VaccinationScheduleMapper;
import app.mappers.dto.SnsUserArrivalDTO;
import app.mappers.dto.VaccinationScheduleDTO;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class SnsUserArrivalStore implements SerializableStore<SnsUserArrival> {

    public static final String DEFAULT_SERIALIZATION_FILE = "sns_users_arrivals.dat";

    private String serializationFile;
    private Set<SnsUserArrival> snsUserArrivalStore;
    private SnsUserArrivalMapper snsUserArrivalMapper;
    private SerializationUtil<SnsUserArrival> serializationUtil;
    private VaccinationScheduleMapper vaccinationScheduleMapper;

    public SnsUserArrivalStore()
    {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.serializationUtil = new SerializationUtil<>();
        this.snsUserArrivalStore = this.loadData();
        this.snsUserArrivalMapper = new SnsUserArrivalMapper();
        this.vaccinationScheduleMapper = new VaccinationScheduleMapper();
    }

    public SnsUserArrivalStore(String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>();
        this.snsUserArrivalStore = this.loadData();
        this.snsUserArrivalMapper = new SnsUserArrivalMapper();
        this.vaccinationScheduleMapper = new VaccinationScheduleMapper();
    }

    public SnsUserArrivalStore(String baseSerializationFolder,
                               String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);
        this.snsUserArrivalStore = this.loadData();
        this.snsUserArrivalMapper = new SnsUserArrivalMapper();
        this.vaccinationScheduleMapper = new VaccinationScheduleMapper();
    }

    /**
     * Created an SNS User arrival to a vaccination center
     * @param snsUserArrivalDTO encapsulates the SNS user arrival information
     * @return snsUserArrival
     */
    public SnsUserArrival createSnsUserArrival(SnsUserArrivalDTO snsUserArrivalDTO)
    {
        return this.snsUserArrivalMapper.toModel(snsUserArrivalDTO);
    }

    /**
     * Add to the store the sns user arrival at the vaccination center if the user has not registered an arrival for that day
     * @param snsUserArrival sns user arrival record
     * @return boolean
     */
    public boolean addSnsUserArrival(SnsUserArrival snsUserArrival)
    {
        changeStateOnSnsUserArrival(snsUserArrival);
        boolean addedSuccessfully = snsUserArrival != null && !this.snsUserArrivalStore.add(snsUserArrival);

/*        if (!addedSuccessfully){
            this.saveData();
        }*/

        return addedSuccessfully;
    }

    public boolean changeStateOnSnsUserArrival(SnsUserArrival snsUserArrival)
    {
        return this.changeState(snsUserArrival);
    }

    /**
     * Method that validates if the user has already registered an arrival to the vaccination center today, in order to avoid duplicate entries
     * @param snsNumber
     * @param currentDate
     * @return
     */
    public boolean existsSnsUserArrival(long snsNumber, LocalDateTime currentDate) {
        try {
            Optional<SnsUserArrival> snsUserArrivalOptional;

            snsUserArrivalOptional = this.getSnsUserArrivalsBySnsNumberAndArrivalDate(snsNumber, currentDate);
            if (snsUserArrivalOptional.isPresent()) {
                throw new SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion("Sns Number", String.valueOf(snsNumber), "arrival time", String.valueOf(currentDate));
            }
            // return true;
        }
        catch(SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * Method that checks if the user is at the right vaccination center, it checks the vaccination center where receptionist is currently working against the VC on the vaccination shedule for the user
     * @param vaccinationSchedule vaccination schedule (info for the vaccination schedule)
     * @param vaccinationCenterName vaccination center where receptionist is working
     * @return
     */
    public boolean checkIfVaccinationCenterIsTheOneOnTheVaccinationSchedule(VaccinationScheduleDTO vaccinationSchedule, String vaccinationCenterName)
    {
        return vaccinationSchedule.getVaccinationCenter().getVaccinationCenterName().equals(vaccinationCenterName);
    }

    /**
     * Returns SnsUserArrival considering the SNS Number (this attribute is unique and mandatory)
     * @param snsNumber unique and allow to identify a person
     * @return Optional of SnsUserArrival
     */
    public Optional<SnsUserArrival> getSnsUserArrivalsBySnsNumber(long snsNumber)
    {
        Iterator<SnsUserArrival> iterator = this.snsUserArrivalStore.iterator();

        SnsUserArrival arrival;
        do
        {
           if(!iterator.hasNext())
           {
            return Optional.empty();
           }
            arrival = iterator.next();
        } while (!(arrival.getSnsUserNumber().getNumber() ==snsNumber));

        return Optional.of(arrival);
    }

    /**
     * Returns SnsUserArrival considering the SNS Number (this attribute is unique and mandatory)
     *
     * @param snsNumber unique and allow to identify a person
     * @return Optional of SnsUserArrival
     */
    public Optional<SnsUserArrival> getSnsUserArrivalsBySnsNumberAndArrivalDate(long snsNumber, LocalDateTime arrivalDate)
    {
        Iterator<SnsUserArrival> iterator = this.snsUserArrivalStore.iterator();

        for (SnsUserArrival snsUserArrival : this.snsUserArrivalStore)
        {
            if ((snsUserArrival.getSnsUserNumber().getNumber() == snsNumber)
                    && snsUserArrival.getArrivalDate().getDayOfYear() == arrivalDate.getDayOfYear())
            {
                return Optional.of(snsUserArrival);
            }
        }

        return Optional.empty();
    }

  /*  public List<SnsUserArrival> getSnsUserArrivalsByVaccinationCenter(String vaccinationCenterEmail)
    {
        List<SnsUserArrival> arrivalsFilteredByVaccinationCenter = new ArrayList<>();

        for (SnsUserArrival arrival : snsUserArrivalStore)
        {
            if (arrival.getVaccineSchedule().getVaccinationCenter().hasEmail(vaccinationCenterEmail))
            {
                arrivalsFilteredByVaccinationCenter.add(arrival);
            }
        }

        return arrivalsFilteredByVaccinationCenter;
    }
*/

    /**
     * method to get the list days of performance of a given vaccination center , the objective is to obtain the list of
     * local date only with the dates
     * @param vaccinationCenterEmail
     * @return list of local dates
     */
    public List<LocalDate> getListDaysForPerformance(String vaccinationCenterEmail) {
        List<LocalDate> listAvailableDaysForPerformance = new ArrayList<>();
        List<LocalDateTime> listArrivals = getListArrivals(vaccinationCenterEmail);
        for (LocalDateTime arrivalDateTime : listArrivals) {
            LocalDate localDate = arrivalDateTime.toLocalDate();
            if (!listAvailableDaysForPerformance.contains(localDate)) {
                listAvailableDaysForPerformance.add(localDate);
            }
        }
        return listAvailableDaysForPerformance;
    }

    /**
     * get list of arrivals to enter in the method of get days list for performance
     * @param vaccinationCenterEmail
     * @return list of arrivals for all days
     */

    public List<LocalDateTime> getListArrivals(String vaccinationCenterEmail) {
        List <LocalDateTime> listArrivals = new ArrayList<>();
        for (SnsUserArrival snsUserArrival : snsUserArrivalStore) {
            if (snsUserArrival.getVaccineSchedule().getVaccinationCenter().hasEmail(vaccinationCenterEmail)) {
                listArrivals.add(snsUserArrival.getArrivalDate());
            }
        }
        return listArrivals;
    }




    /**
     * Method to update the 'State' of the vaccination schedule of the SNS User, after receptionist successfully registers his/her arrival to the vaccination center
     * @param snsUserArrival  of sns user arrival
     * @return boolean
     */
    public boolean changeState(SnsUserArrival snsUserArrival) {

        VaccinationSchedule vaccinationSchedule = snsUserArrival.getVaccineSchedule();
        vaccinationSchedule.setState(VaccinationScheduleState.IN_PROGRESS);
        if(vaccinationSchedule.getState() == VaccinationScheduleState.IN_PROGRESS){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Set<SnsUserArrival> dataToSave() {
        return this.snsUserArrivalStore;
    }

    @Override
    public String serializationFileName() {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<SnsUserArrival> serializationUtil() {
        return this.serializationUtil;
    }


    public List<SnsUserArrival> getSnsUserArrivalsByVaccinationCenter(String vaccinationCenterEmail) {
        List<SnsUserArrival> arrivalsFilteredByVaccinationCenter = new ArrayList<>();

        for (SnsUserArrival arrival : snsUserArrivalStore) {
            if (arrival.getVaccineSchedule().getVaccinationCenter().hasEmail(vaccinationCenterEmail)
                && arrival.getVaccineSchedule().getState() == VaccinationScheduleState.IN_PROGRESS) {
                        arrivalsFilteredByVaccinationCenter.add(arrival);
            }
        }
        return arrivalsFilteredByVaccinationCenter;
    }

    public Set<SnsUserArrival> getSnsUserArrivalStore()
    {
        return snsUserArrivalStore;
    }
}
