package app.domain.store;

import app.domain.interfaces.CenterPerformanceCalculation;
import app.domain.model.*;
import app.domain.shared.Address;
import app.domain.shared.Constants;
import app.domain.shared.Role;
import app.externalmodule.BenchmarkAlgorithmAdapter;
import app.mappers.CommunityMassVaccinationCenterMapper;
import app.mappers.HealthCareVaccinationCenterMapper;
import app.mappers.dto.CommunityMassVaccinationCenterDTO;
import app.mappers.dto.HealthCareVaccinationCenterDTO;
import app.mappers.dto.VaccinationCenterDTO;
import app.mappers.dto.VaccineTypeDTO;
import app.serialization.SerializableStore;
import app.serialization.SerializationUtil;
import app.ui.console.LoadCsvFileUI;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VaccinationCenterStore implements SerializableStore<VaccinationCenter> {

    public static final String DEFAULT_SERIALIZATION_FILE = "vaccination_centers.dat";

    private String serializationFile;
    private Set<VaccinationCenter> store;
    private final SerializationUtil<VaccinationCenter> serializationUtil;
    private HealthCareVaccinationCenterMapper healthCareVaccinationCenterMapper;
    private CommunityMassVaccinationCenterMapper communityMassVaccinationCenterMapper;

    public VaccinationCenterStore()
    {
        this.serializationFile = DEFAULT_SERIALIZATION_FILE;
        this.serializationUtil = new SerializationUtil<>();
        this.store = this.loadData();
        this.healthCareVaccinationCenterMapper = new HealthCareVaccinationCenterMapper();
        this.communityMassVaccinationCenterMapper = new CommunityMassVaccinationCenterMapper();
    }

    public VaccinationCenterStore(String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>();
        this.store = this.loadData();
        this.healthCareVaccinationCenterMapper = new HealthCareVaccinationCenterMapper();
        this.communityMassVaccinationCenterMapper = new CommunityMassVaccinationCenterMapper();
    }

    public VaccinationCenterStore(String baseSerializationFolder,
                                  String serializationFile)
    {
        this.serializationFile = serializationFile;
        this.serializationUtil = new SerializationUtil<>(baseSerializationFolder);
        this.store = this.loadData();
        this.healthCareVaccinationCenterMapper = new HealthCareVaccinationCenterMapper();
        this.communityMassVaccinationCenterMapper = new CommunityMassVaccinationCenterMapper();
    }

    /**
     * Transform DTO into Model with Mapper
     *
     * @param healthCareVaccinationCenterDTO
     * @return Model
     */
    public HealthcareVaccinationCenter createHealthcareVaccinationCenter(HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO)
    {
        HealthCareVaccinationCenterMapper healthCareVaccinationCenterMapper = new HealthCareVaccinationCenterMapper();
        return healthCareVaccinationCenterMapper.toModel(healthCareVaccinationCenterDTO);
    }

    /**
     * Transform DTO into Model with Mapper
     *
     * @param communityMassVaccinationCenterDTO
     * @return Model
     */
    public CommunityMassVaccinationCenter createCommunityMassVaccinationCenter(CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO)
    {
        CommunityMassVaccinationCenterMapper communityMassVaccinationCenterMapper = new CommunityMassVaccinationCenterMapper();
        return communityMassVaccinationCenterMapper.toModel(communityMassVaccinationCenterDTO);
    }

    /**
     * Add VC to Collection in Store
     *
     * @param vaccinationCenter
     * @return true if operation successful, false if not
     */
    public boolean addVaccinationCenter(VaccinationCenter vaccinationCenter)
    {
        boolean addedSuccessfully = vaccinationCenter != null
                && !this.exists(vaccinationCenter.getVaccinationCenterEmailAddress())
                ? this.store.add(vaccinationCenter)
                : false;

/*        if (addedSuccessfully){
            this.saveData();
        }*/

        return addedSuccessfully;
    }

    public Optional<VaccinationCenter> getByEmail(String vaccinationCenterEmailAddress)
    {
        Iterator var2 = this.store.iterator();

        VaccinationCenter vaccinationCenter;
        do
        {
            if (!var2.hasNext())
            {
                return Optional.empty();
            }

            vaccinationCenter = (VaccinationCenter) var2.next();
        } while (!vaccinationCenter.hasEmail(vaccinationCenterEmailAddress));

        return Optional.of(vaccinationCenter);
    }

    public Set<VaccinationCenter> getAll()
    {
        return Collections.unmodifiableSet(this.store);
    }

    public Set<VaccinationCenter> getAllVaccinationCenter()
    {
        return this.store;
    }

    public List<VaccinationCenterDTO> getAllDTO()
    {
        List<VaccinationCenterDTO> vaccinationCenters = new ArrayList<>();

        for (VaccinationCenter vaccinationCenter : this.store)
        {
            if (vaccinationCenter instanceof HealthcareVaccinationCenter)
            {
                    vaccinationCenters.add(
                            this.healthCareVaccinationCenterMapper.toDTO((HealthcareVaccinationCenter) vaccinationCenter)
                    );
            }

            if (vaccinationCenter instanceof CommunityMassVaccinationCenter)
            {
                    vaccinationCenters.add(
                            this.communityMassVaccinationCenterMapper.toDTO((CommunityMassVaccinationCenter) vaccinationCenter)
                    );
            }
        }

        return vaccinationCenters;
    }

    public VaccinationCenterDTO getVaccinationCenterDTOByEmail(String vaccinationCenterEmail)
    {
        VaccinationCenter vaccinationCenter = getByEmail(vaccinationCenterEmail).get();
        VaccinationCenterDTO vaccinationCenterDTO = null;
        if (vaccinationCenter != null)
        {
            if (vaccinationCenter instanceof HealthcareVaccinationCenter)
            {
                vaccinationCenterDTO = healthCareVaccinationCenterMapper.toDTO((HealthcareVaccinationCenter) vaccinationCenter);
            }
            if (vaccinationCenter instanceof CommunityMassVaccinationCenter)
            {
                vaccinationCenterDTO = communityMassVaccinationCenterMapper.toDTO((CommunityMassVaccinationCenter) vaccinationCenter);
            }
        }
        return vaccinationCenterDTO;
    }

    public List<VaccinationCenterDTO> getAllDTOByVaccineType(VaccineTypeDTO vaccineType)
    {
        List<VaccinationCenterDTO> vaccinationCenters = new ArrayList<>();

        for (VaccinationCenter vaccinationCenter : this.store)
        {
            if (vaccinationCenter instanceof HealthcareVaccinationCenter)
            {
                if (((HealthcareVaccinationCenter) vaccinationCenter).supportsVaccineType(vaccineType.getCode()))
                    vaccinationCenters.add(
                            this.healthCareVaccinationCenterMapper.toDTO((HealthcareVaccinationCenter) vaccinationCenter)
                    );
            }

            if (vaccinationCenter instanceof CommunityMassVaccinationCenter)
            {
                if (((CommunityMassVaccinationCenter) vaccinationCenter).getVaccineType().getCode().equalsIgnoreCase(vaccineType.getCode()))
                {
                    vaccinationCenters.add(
                            this.communityMassVaccinationCenterMapper.toDTO((CommunityMassVaccinationCenter) vaccinationCenter)
                    );
                }
            }
        }

        return vaccinationCenters;
    }

    /**
     * Validates if a VC already exists, using the email (unique field)
     *
     * @param vaccinationCenterEmailAddress
     * @return boolean, if the VC with the same email is already registered
     */
    public boolean exists(Email vaccinationCenterEmailAddress)
    {
        Optional<VaccinationCenter> result = this.getByEmail(vaccinationCenterEmailAddress.getEmail());
        return result.isPresent();
    }

    /**
     * Get Vaccination Center with name as a parameter
     *
     * @param name
     * @return object VaccinationCenter
     */
    public Optional<VaccinationCenter> getVaccinationCenterByName(String name)
    {
        Iterator<VaccinationCenter> iterator = this.store.iterator();

        VaccinationCenter vaccinationCenter;
        do
        {
            if (!iterator.hasNext())
            {
                return Optional.empty();
            }

            vaccinationCenter = iterator.next();
        } while (!vaccinationCenter.hasName(name));

        return Optional.of(vaccinationCenter);
    }

    /**
     * Validate if VaccinationCenter with specific name already exists
     *
     * @param vaccinationCenterName
     * @return boolean
     */
    public boolean existsCommunityMassVaccinationCenter(String vaccinationCenterName)
    {
        Optional<VaccinationCenter> result = this.getVaccinationCenterByName(vaccinationCenterName);
        return result.isPresent();
    }

    /**
     * Validates if Community Mass Vaccination Center exists
     *
     * @param communityMassVaccinationCenterDTO
     * @return boolean
     */
    public boolean existsCommunityMassVaccinationCenter(CommunityMassVaccinationCenterDTO communityMassVaccinationCenterDTO)
    {
        return this.existsCommunityMassVaccinationCenter(communityMassVaccinationCenterDTO.getVaccinationCenterName());

    }

    /**
     * Validates if Health Care Vaccination Center exists
     *
     * @param healthCareVaccinationCenterDTO
     * @return boolean
     */
    public boolean existsHealthCareVaccinationCenter(HealthCareVaccinationCenterDTO healthCareVaccinationCenterDTO)
    {
        return this.existsCommunityMassVaccinationCenter(healthCareVaccinationCenterDTO.getVaccinationCenterName());
    }

    public boolean checkIfScheduleDateIsBetweenVaccinationCenterOpeningHours(LocalDateTime vaccinationScheduleDate, String vaccinationCenterID)
    {
        VaccinationCenter vaccinationCenter = getByEmail(vaccinationCenterID).get();

        LocalTime openingHour = this.convertStringToTime(vaccinationCenter.getOpeningHours());
        LocalTime closingHour = this.convertStringToTime(vaccinationCenter.getClosingHours());
        LocalTime vaccinationScheduleTime = vaccinationScheduleDate.toLocalTime();

        if (vaccinationScheduleTime.isBefore(openingHour)
                || vaccinationScheduleTime.isAfter(closingHour))
        {
            System.out.println(" Schedule hour is out of vaccination center working hours");
            return false;
        }

        return true;
    }


    public boolean checkIfUserArrivedWithinVaccinationCenterWorkingHours(LocalDateTime arrivalTime, String vaccinationCenterOfArrival)
    {
        VaccinationCenter vaccinationCenter = getVaccinationCenterByName(vaccinationCenterOfArrival).get();
        LocalTime openingHours = this.convertStringToTime(vaccinationCenter.getOpeningHours());
        LocalTime closingHours = this.convertStringToTime(vaccinationCenter.getClosingHours());
        LocalTime arrivalHours = arrivalTime.toLocalTime();

        if(arrivalHours.isBefore(openingHours)
                || arrivalHours.isAfter(closingHours))
        {
            System.out.printf("Sorry we are not able to vaccinate you now, the working hours of this Vaccination Center are between %s and %s", openingHours, closingHours);
            return false;
        }
        return true;
    }

    private LocalTime convertStringToTime(String time)
    {
        DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

        LocalTime time12 = LocalTime.parse(time, formatter12);

        return time12;

    }

    public long obtainWorkingPeriodMinutes(String vaccinationCenterEmail) {
        DateTimeFormatter formatter12 = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);

        VaccinationCenter vaccinationCenter = getByEmail(vaccinationCenterEmail).get();
        LocalTime timeOpen = LocalTime.parse(vaccinationCenter.getOpeningHours(), formatter12);
        LocalTime timeClose = LocalTime.parse(vaccinationCenter.getClosingHours(), formatter12);

        Duration duration = Duration.between(timeOpen, timeClose);

        return duration.toMinutes();
    }


    @Override
    public Set<VaccinationCenter> dataToSave() {
        return this.store;
    }

    @Override
    public String serializationFileName() {
        return this.serializationFile;
    }

    @Override
    public SerializationUtil<VaccinationCenter> serializationUtil() {
        return this.serializationUtil;
    }

}
