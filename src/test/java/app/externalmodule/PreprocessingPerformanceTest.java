package app.externalmodule;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PreprocessingPerformanceTest {

    private PreprocessingPerformance preprocessingPerformance;

    public PreprocessingPerformanceTest() {
        this.preprocessingPerformance = new PreprocessingPerformance();
    }

    private List<String> actualHoursList;

    private List<String> expectedHoursList;

    private String [] actualConvertFromListToArray;

    private String [] expectedConvertFromListToArray;

    private int [] actualTransformMinutesOnly;

    private int [] expectedTransformMinutesOnly;

    private int [] actualObtainPerformanceIntervalArray;

    private int [] expectedObtainPerformanceIntervalArray;

    @BeforeEach
    void setUP() {

        // Test to run getHoursList() test
        String dayHour1 = "5/30/2022 8:24";
        String dayHour2 = "5/30/2022 9:24";
        String dayHour3 = "5/30/2022 10:24";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/dd/yyyy H:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(dayHour1, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(dayHour2, formatter);
        LocalDateTime dateTime3 = LocalDateTime.parse(dayHour3, formatter);

        String day = "5/30/2022";
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("M/dd/yyyy");
        LocalDate date = LocalDate.parse(day, formatterDate);

        List<LocalDateTime> listToTest = new ArrayList<>();
        listToTest.add(dateTime1);listToTest.add(dateTime2);listToTest.add(dateTime3);

        String hour1 = "8:24";
        String hour2 = "9:24";
        String hour3 = "10:24";

        expectedHoursList = new ArrayList<>();
        expectedHoursList.add(hour1);expectedHoursList.add(hour2);expectedHoursList.add(hour3);

        actualHoursList = new ArrayList<>();
        actualHoursList = preprocessingPerformance.getHoursList(date, listToTest);

        // Test to run convertFromListToArray()

        expectedConvertFromListToArray = new String[] {"8:24", "9:24", "10:24"};
        actualConvertFromListToArray = preprocessingPerformance.convertFromListToArray(actualHoursList);

        // Test to transform to minutes only
        expectedTransformMinutesOnly = new int[] {24, 84, 144};
        actualTransformMinutesOnly = preprocessingPerformance.transformMinutesOnly(actualConvertFromListToArray);

        // Test to obtain performanceArray

        String [] arrivals = new String[]{"8:24", "8:20", "8:00", "8:14", "8:50", "10:24", "10:40", "14:24", "13:24"};
        String [] leavings = new String []{"9:00", "9:00", "8:44", "9:00", "9:00", "11:00", "12:00", "15:00", "15:00"};
        int [] arrivalsMinutes = preprocessingPerformance.transformMinutesOnly(arrivals);
        int [] leavingMinutes = preprocessingPerformance.transformMinutesOnly(leavings);
        int interval = 30;
        int workingPeriod = 720;

        expectedObtainPerformanceIntervalArray = new int[] {4, 0, -4, 0, 1, 1, -1, 0, -1, 0, 1, 0, 1, 0, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        actualObtainPerformanceIntervalArray = preprocessingPerformance.obtainPerformanceIntervalArray(arrivalsMinutes,
                leavingMinutes, interval, workingPeriod);


    }

    @Test
    void getHoursList() {
        Assertions.assertEquals(expectedHoursList,actualHoursList);
    }

    @Test
    void convertFromListToArray() {
        Assertions.assertArrayEquals(expectedConvertFromListToArray, actualConvertFromListToArray);

    }

    @Test
    void transformMinutesOnly() {
        Assertions.assertArrayEquals(expectedTransformMinutesOnly, actualTransformMinutesOnly);

    }

    @Test
    void obtainPerformanceIntervalArray() {
        Assertions.assertArrayEquals(expectedObtainPerformanceIntervalArray,actualObtainPerformanceIntervalArray);

    }

    @AfterEach
    void dumpMemory() {
        this.actualHoursList = null;
        this.expectedHoursList = null;
        this.actualConvertFromListToArray = null;
        this.expectedConvertFromListToArray = null;
        this.actualTransformMinutesOnly = null;
        this.expectedTransformMinutesOnly = null;
        this.actualObtainPerformanceIntervalArray = null;
        this.expectedObtainPerformanceIntervalArray = null;
    }
}