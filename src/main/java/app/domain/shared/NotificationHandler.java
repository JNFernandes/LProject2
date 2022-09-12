package app.domain.shared;

import app.ui.console.utils.Utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationHandler {

    public static void sendEmail(String to, String subject, String content)
    {
        System.out.println("To: " + to + "\n");
        System.out.println("Subject: " + subject + "\n");
        System.out.println("Body: " + content + "\n");
    }

    // https://www.baeldung.com/java-append-to-file
    public static void sendSMS(long phoneNumber, String message)
    {
        String fileName = "SMS.txt";
        try
        {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(phoneNumber + ": " + message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendEmployeeEmail(String fileName, String email, String message)
    {
        try
        {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(email + ": " + message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    public static void sendSMSForLeavingVaccinationCenter(long phoneNumber, String message)
    {
        String fileName = "SMS_Leave_VaccinationCenter.txt";
        try
        {
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(phoneNumber + ": " + message);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception ex)
        {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}
