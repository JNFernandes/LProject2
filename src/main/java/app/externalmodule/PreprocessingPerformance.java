package app.externalmodule;

import app.domain.store.SnsUserArrivalStore;
import app.domain.store.VaccineAdministrationStore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PreprocessingPerformance {

    /**
     * constructor
     */
    public PreprocessingPerformance() {

    }

    /**
     * getHoursList, receives the list in local date time, checks if it bellongs to the day option, takes the part of hours and minutes and add to a string list
     * for preprocessing analysis
     * @param dateOption
     * @param timeList
     * @return list of strings
     */

    public List<String> getHoursList(LocalDate dateOption, List<LocalDateTime> timeList) {
        List<String> hours = new ArrayList<>();
        for (LocalDateTime ldt : timeList) {
            LocalDate conversion = ldt.toLocalDate();
            if (conversion.equals(dateOption)) {
                String time = String.format(ldt.getHour() + ":" + ldt.getMinute());
                hours.add(time);
            }
        }
        return hours;
    }

    /**
     * method to convert list to array
     * @param hoursList
     * @return array of hours in String
     */

    public String [] convertFromListToArray(List<String> hoursList) {
        String [] hoursArr = new String[hoursList.size()];
        hoursList.toArray(hoursArr);
        return hoursArr;
    }

    /**
     * method that receives the array of strings and converts to minutes
     * @param arr
     * @return minutes array int
     */

    public int [] transformMinutesOnly(String [] arr) {
        int [] min = new int [arr.length];
        for (int i = 0; i < arr.length; i++) {
            String [] elements = arr[i].split(":");
            int hours = Integer.parseInt(elements[0]);
            int minutes = Integer.parseInt(elements[1]);
            int finalMinutes = (hours - 8) * 60 + minutes;
            min[i] = finalMinutes;
        }
        return min;
    }

    /**
     * method to obtain the performance array for each interval that will be used as input for the brute force or benchmark algorithm
     * @param minHoursArrival
     * @param minHoursLeaving
     * @param interval
     * @param workingPeriod
     * @return array of ints
     */

    public int [] obtainPerformanceIntervalArray(int [] minHoursArrival, int [] minHoursLeaving, int interval, int workingPeriod) {
        try {
            int steps = workingPeriod / interval;
            int[] arr = new int[steps];
            for (int i = 0; i < steps; i++) {
                int countArrival = 0;
                int countLeaving = 0;
                int difference;
                for (int j = 0; j < minHoursArrival.length; j++) {
                    if (minHoursArrival[j] >= i * interval && minHoursArrival[j] < (i + 1) * interval) {
                        countArrival++;
                    }
                    if (minHoursLeaving[j] >= i * interval && minHoursLeaving[j] < (i + 1) * interval) {
                        countLeaving++;
                    }
                }
                difference = countArrival - countLeaving;
                arr[i] = difference;
            }
            return arr;
        } catch (ArrayIndexOutOfBoundsException | ArithmeticException e) {
            System.out.println(e.getMessage());
            return new int [] {-1};
        }
    }
}
