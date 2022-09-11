package app.externalmodule;

import app.mappers.dto.LegacyDataDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.sql.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReaderLegacyDataAdapteeTest
{

    private final String[] FILE_HEADER = {
            "SNSUserNumber", "VaccineName", "Dose", "LotNumber", "ScheduledDateTime",
            "ArrivalDateTime", "NurseAdministrationDateTime", "LeavingDateTime"
    };

    private DateTimeFormatter formatter;

    private CsvFileReaderLegacyDataAdaptee csvReader;

    @BeforeEach
    public void setUp()
    {
        csvReader = new CsvFileReaderLegacyDataAdaptee();
        formatter = new DateTimeFormatterBuilder()
                .appendPattern("[M/d/yyyy H:m]")
                .appendPattern("[M-d-yyyy H:m]")
                .toFormatter();
    }


    @Test
    public void givenValidHeader_ReturnsTrue()
    {
        String[] testHeader = {"SNSUserNumber", "VaccineName", "Dose", "LotNumber", "ScheduledDateTime",
                "ArrivalDateTime", "NurseAdministrationDateTime", "LeavingDateTime"};

        Assertions.assertTrue(csvReader.validateHeader(testHeader));

    }

    @Test
    public void givenHeaderWithInvalidLength_ReturnsFalse()
    {
        String[] testHeader = {"SNSUserNumber", "VaccineName", "Dose", "LotNumber"};

        Assertions.assertThrows(HeaderNotMatchException.class,
                () -> {csvReader.validateHeader(testHeader);}
        );

    }

    @Test
    public void givenHeaderWithWrongOrder_ReturnsFalse()
    {
        String[] testHeader = {"SNSUserNumber", "VaccineName", "Dose", "LotNumber", "ScheduledDateTime",
                "ArrivalDateTime", "NurseAdministrationDateTime", "LeavingDateTime"};

        Assertions.assertTrue(csvReader.validateHeader(testHeader));
    }

    @Test
    public void givenEmptyHeader_ReturnsHeaderNotMatchException()
    {
        String[] testHeader = {};

        Assertions.assertThrows(HeaderNotMatchException.class,
                () -> {csvReader.validateHeader(testHeader);}
        );

    }

    @Test
    public void givenLegacyDataItems_ReturnsValidLegacyDataDTO()
    {

        LocalDateTime scheduleDateTime = LocalDateTime.parse("5/30/2022 8:00", formatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse("5/30/2022 8:24", formatter);
        LocalDateTime administrationDateTime = LocalDateTime.parse("5/30/2022 9:11", formatter);
        LocalDateTime leavingDateTime = LocalDateTime.parse("5/30/2022 9:43", formatter);

        LegacyDataDTO expectedDTO = new LegacyDataDTO(
                (long) 161593120, "Spikevax", 1, "21C16-05",
                scheduleDateTime, arrivalDateTime, administrationDateTime, leavingDateTime
        );

        String[] inputLine = {
                "161593120", "Spikevax", "Primeira", "21C16-05", "5/30/2022 8:00",
                "5/30/2022 8:24", "5/30/2022 9:11", "5/30/2022 9:43"
        };

        LegacyDataDTO actualDTO = csvReader.createLegacyDataDTOFromCsvLine(inputLine);

        Assertions.assertEquals(expectedDTO.getSnsNumber(), actualDTO.getSnsNumber());
        Assertions.assertEquals(expectedDTO.getVaccineName(), actualDTO.getVaccineName());
        Assertions.assertEquals(expectedDTO.getDose(), actualDTO.getDose());
        Assertions.assertEquals(expectedDTO.getLotNumber(), actualDTO.getLotNumber());
        Assertions.assertTrue(expectedDTO.getScheduledDateTime().isEqual(actualDTO.getScheduledDateTime()));
        Assertions.assertTrue(expectedDTO.getArrivalDateTime().isEqual(actualDTO.getArrivalDateTime()));
        Assertions.assertTrue(expectedDTO.getNurseAdministrationDateTime().isEqual(actualDTO.getNurseAdministrationDateTime()));
        Assertions.assertTrue(expectedDTO.getLeavingDateTime().isEqual(actualDTO.getLeavingDateTime()));

    }

    @Test
    public void givenInvalidDateTimeFormat_ReturnsException()
    {
        String invalidDateTime = "30/1/2022 10:05";
        Assertions.assertThrows(DateTimeParseException.class,
                () -> {csvReader.readDateTime(invalidDateTime);}
        );
    }

    @Test
    public void givenValidDateString_ReturnsValidDateTime()
    {
        String validDateTimeString = "1/5/2022 08:05";
        LocalDateTime expectedDateTime = LocalDateTime.parse(validDateTimeString, formatter);

        Assertions.assertTrue(expectedDateTime.isEqual(csvReader.readDateTime(validDateTimeString)));

    }

    @Test
    public void givenNonExistentFile_ReturnsFileNotFoundException()
    {
        String filePath = "file.csv";
        Assertions.assertThrows(FileNotFoundException.class, ()->{csvReader.importDataFromCsvFile(filePath);});

    }

    @Test
    public void givenFileWithInvalidHeader_ReturnsHeaderNotMatchException()
    {
        String filePath = "wrongHeaderLegacyDataCsvFile.csv";
        Assertions.assertThrows(HeaderNotMatchException.class, ()->{csvReader.importDataFromCsvFile(filePath);});

    }

    @Test
    public void givenIncorrectFileExtension_ReturnsInvalidTypeFileExtension()
    {
        String filePath = "wrongExtension.dat";
        Assertions.assertThrows(InvalidTypeFileException.class, ()->{csvReader.importDataFromCsvFile(filePath);});

    }

    @Test
    public void givenValidFile_ReturnsListOfValidDTOS()
    {
        String filePath = "validLegacyDataFile.csv";

        // expected dto1
        LocalDateTime scheduleDTO1 = LocalDateTime.parse("5/30/2022 8:00", formatter);
        LocalDateTime arrivalDTO1 = LocalDateTime.parse("5/30/2022 8:24", formatter);
        LocalDateTime administrationDTO1 = LocalDateTime.parse("5/30/2022 9:11", formatter);
        LocalDateTime leavingDTO1 = LocalDateTime.parse("5/30/2022 9:43", formatter);
        LegacyDataDTO expectedDTO1 = new LegacyDataDTO(
                (long) 11111111, "Spikevax", 1, "21C16-05",
                scheduleDTO1, arrivalDTO1, administrationDTO1, leavingDTO1
        );

        // expected dto2
        LocalDateTime scheduleDTO2 = LocalDateTime.parse("5/30/2022 8:00", formatter);
        LocalDateTime arrivalDTO2 = LocalDateTime.parse("5/30/2022 8:10", formatter);
        LocalDateTime administrationDTO2 = LocalDateTime.parse("5/30/2022 8:17", formatter);
        LocalDateTime leavingDTO2 = LocalDateTime.parse("5/30/2022 8:51", formatter);
        LegacyDataDTO expectedDTO2 = new LegacyDataDTO(
                (long) 161593121, "Spikevax", 1, "21C16-05",
                scheduleDTO2, arrivalDTO2, administrationDTO2, leavingDTO2
        );

        List<LegacyDataDTO> expectedDTOList = new ArrayList<>();
        expectedDTOList.add(expectedDTO1);
        expectedDTOList.add(expectedDTO2);

        try
        {
            List<LegacyDataDTO> actualDTOList = csvReader.importDataFromCsvFile(filePath);
            LegacyDataDTO actualDTO1 = actualDTOList.get(0);
            LegacyDataDTO actualDTO2 = actualDTOList.get(1);
            Assertions.assertEquals(expectedDTO1.getSnsNumber(), actualDTO1.getSnsNumber());
            Assertions.assertEquals(expectedDTO1.getVaccineName(), actualDTO1.getVaccineName());
            Assertions.assertEquals(expectedDTO1.getDose(), actualDTO1.getDose());
            Assertions.assertEquals(expectedDTO1.getLotNumber(), actualDTO1.getLotNumber());
            Assertions.assertTrue(expectedDTO1.getScheduledDateTime().isEqual(actualDTO1.getScheduledDateTime()));
            Assertions.assertTrue(expectedDTO1.getArrivalDateTime().isEqual(actualDTO1.getArrivalDateTime()));
            Assertions.assertTrue(expectedDTO1.getNurseAdministrationDateTime().isEqual(actualDTO1.getNurseAdministrationDateTime()));
            Assertions.assertTrue(expectedDTO1.getLeavingDateTime().isEqual(actualDTO1.getLeavingDateTime()));
        }
        catch(FileNotFoundException e)
        {

        }

    }


}
