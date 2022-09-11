package app.ui.console;

import app.controller.LoadCsvFileController;
import app.mappers.dto.SnsUserDTO;
import app.ui.console.utils.Utils;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class LoadCsvFileUI implements Runnable {

    private LoadCsvFileController loadCsvFileController;

    public LoadCsvFileUI() {
        this.loadCsvFileController = new LoadCsvFileController();
    }

    /**
     * method to run the load of a csv file. This method is responsible for interaction with the user (admin)
     * treats the fileNotFoundExecption in case the path is incorrect
     */
    @Override
    public void run() {
        boolean exit = false;
        while(!exit) {
            boolean validPathFile;
            boolean askAgain;
            do {
                System.out.println("Insert the path of the external csv file: ");
                Scanner in = new Scanner(System.in);
                String pathFile = in.nextLine();
                try {
                    List<SnsUserDTO> dtoList = this.loadCsvFileController.getListFromExternalModule(pathFile);
                    List<SnsUserDTO> finalSnsUserList = this.loadCsvFileController.registerSnsUsersFromExternalModule(dtoList);
                    System.out.println("Registration of users done with success");
                    String headerSnsUserList = "List of User Registed in the platform: ";
                    Utils.showListWithoutCancel(finalSnsUserList, headerSnsUserList);
                    if (finalSnsUserList.size() == 0) {
                        System.out.println("There are not new users to register in the application");
                    }
                    validPathFile = true;
                    askAgain = false;
                    exit = true;
                }catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                    System.out.println("File was not found with sucess");
                    askAgain = Utils.confirm("Do you want to load again? (s/n)");
                    if (!askAgain) {
                        exit = true;
                    }
                   validPathFile = false;
                }
            } while((!validPathFile && askAgain));
        }
    }

    /**
     * method to close the application in case the program encounters an exception
     */

    public static void closeApplication() {
        System.exit(1);
    }
}
