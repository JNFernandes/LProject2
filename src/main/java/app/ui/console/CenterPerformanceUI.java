package app.ui.console;

import app.controller.CenterPerformanceController;
import app.mappers.dto.VaccinationCenterDTO;
import app.ui.console.utils.Utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class CenterPerformanceUI implements Runnable{

    private CenterPerformanceController centerPerformanceController;

    public CenterPerformanceUI() {
        this.centerPerformanceController = new CenterPerformanceController();
    }


    @Override
    public void run() {
        String vaccinationCenterEmail = "vaccinationPorto@email.com";

        boolean exit = false;
        while (!exit) {
            List<LocalDate> daysList = centerPerformanceController.getListDaysForPerformance(vaccinationCenterEmail);
            String header = "List of available days: ";
            Object day =  Utils.showAndSelectOne(daysList, header);
            long workingPeriodMinutes = centerPerformanceController.getVaccinationCenterWorkingPeriod(vaccinationCenterEmail);
            Scanner in = new Scanner(System.in);
            boolean isValid;
            String period;
            int periodNumber;

            do {
                System.out.printf("Please chose an interval (min) for the working period of %dH(%d min)%n", workingPeriodMinutes/60, workingPeriodMinutes);
                System.out.println("Note that the chosen interval should be a divisor of the working period (e.g 30 is divisor of 720)");
                System.out.print("Your interval: ");
                try {
                    period = in.nextLine();
                    periodNumber = Integer.parseInt(period.trim());
                    if (workingPeriodMinutes%periodNumber != 0) {
                        throw new ArithmeticException("Inserted period is not valid!");
                    }
                    isValid = true;
                    centerPerformanceController.calculatePerformanceBasedOnInterval(periodNumber, (LocalDate) day, vaccinationCenterEmail);
                    exit = true;
                } catch (ArithmeticException e) {
                    System.out.println(e.getMessage());
                    isValid = false;
                } catch (NumberFormatException e) {
                    System.out.println("An integer was not provided!");
                    isValid = false;
                } catch (NullPointerException e) {
                    System.out.println(e.getMessage());
                    System.out.println("The app should be restard. Something went wrong");
                    isValid = false;
                    exit = true;
                }
            } while (!isValid);
        }
    }

}
