package app.domain.store;

import app.mappers.dto.LegacyDataDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class LegacyDataStoreTest
{

    private LegacyDataDTO legacyDataDTO1;
    private LegacyDataDTO legacyDataDTO2;
    private DateTimeFormatter formatter;

    @BeforeAll
    public void setUp()
    {
        formatter = new DateTimeFormatterBuilder()
                .appendPattern("[M/d/yyyy H:m]")
                .appendPattern("[M-d-yyyy H:m]")
                .toFormatter();

        LocalDateTime scheduleDateTime = LocalDateTime.parse("5/30/2022 8:00", formatter);
        LocalDateTime arrivalDateTime = LocalDateTime.parse("5/30/2022 8:24", formatter);
        LocalDateTime administrationDateTime = LocalDateTime.parse("5/30/2022 9:11", formatter);
        LocalDateTime leavingDateTime = LocalDateTime.parse("5/30/2022 9:43", formatter);

        legacyDataDTO1 = new LegacyDataDTO(
                (long) 161593120, "Spikevax", 1, "21C16-05",
                scheduleDateTime, arrivalDateTime, administrationDateTime, leavingDateTime
        );


    }

}
