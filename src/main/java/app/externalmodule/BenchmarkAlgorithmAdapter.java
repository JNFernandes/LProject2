package app.externalmodule;

import app.domain.interfaces.CenterPerformanceCalculation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class BenchmarkAlgorithmAdapter implements CenterPerformanceCalculation {
    /**
     * preprocessingPerformance attribute of class PreprocessingPerformance
     */
    private PreprocessingPerformance preprocessingPerformance;

    /**
     * constructor
     */
    public BenchmarkAlgorithmAdapter() {
        this.preprocessingPerformance = new PreprocessingPerformance();
    }

    /**
     * performance analysis of the algorithm for worst case performance center
     * @param arr of the intervals
     * @return intial position and final position for the worst case performance and the maximum sum, all in an array
     */

    public int [] performanceAlgorithm(int [] arr) {
        int [] arrPositionAndMax = new int[3];
        int maxSoFar = 0;
        int maxEndingHere = 0;
        int startMaxSoFar = 0;
        int endMaxSoFar = 0;
        int startMaxEndingHere = 0;

        for(int i = 0; i < arr.length; ++i) {
            int elem = arr[i];
            int endMaxEndingHere = i + 1;
            if (maxEndingHere + elem < 0) {
                maxEndingHere = 0;
                startMaxEndingHere = i + 1;
            } else {
                maxEndingHere += elem;
            }

            if (maxSoFar < maxEndingHere) {
                maxSoFar = maxEndingHere;
                startMaxSoFar = startMaxEndingHere;
                endMaxSoFar = endMaxEndingHere;
            }
        }
        arrPositionAndMax[0] = startMaxSoFar; arrPositionAndMax[1] = endMaxSoFar; arrPositionAndMax[2] = maxSoFar;
        return arrPositionAndMax;
    }

    /**
     * method to obtain the final array, method implemented for tests
     * @param arr
     * @param initial
     * @param ending
     * @return final array only for the worst case
     */

    public int [] obtainFinalArray(int [] arr, int initial, int ending) {
        int [] finalArray = new int[ending - initial];
        for (int i = initial; i<ending; i++) {
            finalArray[i-initial] = arr[i];
        }
        return  finalArray;
    }

    /**
     * mehtod just for displaying the info for the worst case performance center
     * @param arr
     * @param step
     * @param initial
     * @param ending
     * @param day
     * @param max
     */

    public void displayMessage(int [] arr, int step, int initial, int ending, LocalDate day, int max) {

        System.out.println("Input List:");
        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, 0, arr.length)));

        System.out.println("Contiguos sublist of maximum sum: ");
        int [] finalArr = obtainFinalArray(arr, initial, ending);
        System.out.println(Arrays.toString(Arrays.copyOfRange(finalArr, 0, finalArr.length)));
        System.out.printf("Maximum contiguous sum is %d%n", max);

        System.out.printf("Time interval: [%s %02d:%02d, %s %02d:%02d]%n", day, (initial*step)/60 + 8,(initial*step)%60, day, ((ending)*step)/60 + 8,((ending) * step)%60);
        // System.out.printf("The Vaccination Center is less responsive from %d:%02d to %d:%02d%n", (initial*step)/60 + 8,(initial*step)%60, (ending*step)/60 + 8,(ending*step)%60);
    }

    /**
     * receives the timeResoulution, day option, working period , list of arrivals and leavings to do pre processing analysis
     * that comes from the Preprocessing class and displays message of performance
     * @param timeResolution
     * @param day
     * @param workingPeriod
     * @param listArrivals
     * @param listLeavings
     */
    @Override
    public void calculatePerformance(int timeResolution, LocalDate day, int workingPeriod,  List<LocalDateTime> listArrivals, List<LocalDateTime> listLeavings ) {


        // Obtain the hours list and convert to string for further preprocessing
        List<String> hoursArrivalList = preprocessingPerformance.getHoursList(day,listArrivals);
        List<String> hoursLeavingList = preprocessingPerformance.getHoursList(day, listLeavings);

        // Convert list to array for further analysis of the center performance
        String [] hoursArrivalArr  = preprocessingPerformance.convertFromListToArray(hoursArrivalList);
        String [] hoursLeavingArr = preprocessingPerformance.convertFromListToArray(hoursLeavingList);

        // Convert hours only to minutes and to integer for center performance analysis
        int [] hoursToMinArrival = preprocessingPerformance.transformMinutesOnly(hoursArrivalArr);
        int [] hoursToMinLeaving = preprocessingPerformance.transformMinutesOnly(hoursLeavingArr);
        int [] performanceIntervalArr = preprocessingPerformance.obtainPerformanceIntervalArray(hoursToMinArrival, hoursToMinLeaving,
                timeResolution, workingPeriod);

        if (performanceIntervalArr.length == 1 || performanceIntervalArr.length == 0) {
            System.out.println("Some error has occured");
        }
        else {
            // calculate in nanoSeconds performance run time
            long startTime = System.nanoTime();
            int [] positionOfArrayForWorstScenarioPerformanceAndMax = performanceAlgorithm(performanceIntervalArr);
            long endTime   = System.nanoTime();
            long totalTime = endTime - startTime;
            System.out.printf("Total time run was %d ns %n", totalTime);

            // displays the message of the worst case performance of the center
            displayMessage(performanceIntervalArr, timeResolution,
                    positionOfArrayForWorstScenarioPerformanceAndMax[0], positionOfArrayForWorstScenarioPerformanceAndMax[1],
                    day, positionOfArrayForWorstScenarioPerformanceAndMax[2]);
        }
    }
}
