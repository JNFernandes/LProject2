package app.externalmodule;

import app.domain.shared.Gender;
import app.mappers.dto.AddressDTO;
import app.mappers.dto.SnsUserDTO;
import app.ui.console.LoadCsvFileUI;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CsvFileReaderSemiColonAdaptee {

    /**
     * snsUserDTOList atribute of List type
     */
    private List<SnsUserDTO> snsUserDTOList;

    /**
     * constant of header to further verification
     */

    private static final String [] HEADER = {"Sex",
            "Sex",
            "BirthDate",
            "Address",
            "PhoneNumber",
            "Email",
            "SNSUSerNumber",
            "CitizenCardNumber"};


    /**
     * constant to avoid literals
     */
    private static final String SEMI_COLON = ";";
    private static final String COUNTRY = "Portugal";


    /**
     * static Scanner
     */
    static Scanner in;

    /**
     * constructor CsvFileReaderSemiColonAdaptee
     */
    public CsvFileReaderSemiColonAdaptee() {

        snsUserDTOList = new ArrayList<>();
    }

    /**
     * method to check if header is valid
     * @param header
     * @param validHeader
     * @return boolean true or false
     */
    public static boolean checkHeader(String [] header, String [] validHeader) {
        int i = validHeader.length - 1;
        while (i > 0 )
        {
            if (!header[i].trim().equalsIgnoreCase(validHeader[i]))
            {
                throw new HeaderNotMatchException("Header does not match. Review the file for further upload");

            }
            i--;
        }
        return true;
    }

    /**
     * method that does not return anything to read a file. Throws ListLinesErrorsException in case file contains errors
     * Exception is treated and the application is closed for reinput the correct file without errors
     * @param filePath - receives as argument
     * @throws FileNotFoundException - to be treated in the UI
     */

    public void readFile(String filePath) throws FileNotFoundException {
        try {
            boolean verifyCsvFile = isCsvType(filePath);
            File file = new File(filePath);
            in = new Scanner(file);
            String firstLine = in.nextLine();
            String[] header = firstLine.split(SEMI_COLON);
            if (header.length == HEADER.length) {
                boolean isValidHeader = checkHeader(header, HEADER);
                if (isValidHeader) {
                    while (in.hasNextLine()) {
                        String line = in.nextLine();
                        String[] elements = line.split(SEMI_COLON);
                        if (elements.length == HEADER.length) {
                            readFileLine(elements);
                        }
                        else {
                            throw new ListLinesErrorsException("Lines of the file contains errors." +
                                    "Review the file for further upload");
                        }
                    }
                    in.close();
                }
            }
            else {
                throw new ListLinesErrorsException("Fields are not correct. " +
                        "You either have less or more fields in the file. Check also delimiters");
            }
            in.close();
        } catch (ListLinesErrorsException | HeaderNotMatchException |InvalidTypeFileException e) {
            System.out.println(e.getMessage());
            LoadCsvFileUI.closeApplication();
        }
    }

    /**
     * method to read a specific line of the file from a given user. Receives a splited line. Does not return anything,
     * creates a new snsUserDTO if the parameters are correct and add to the list. Throws specific exceptions that are
     * treated in case the line contains some errors
     * @param elements
     */
    public void readFileLine(String [] elements) {
        SnsUserDTO snsUserDTO;
        try {
            String nome = elements[0].trim();
            String genderStringFromFile = elements[1].trim();
            Gender gender;
            if (genderStringFromFile.equalsIgnoreCase("masculino")) {
                gender = Gender.Male;
            } else if (genderStringFromFile.equalsIgnoreCase("feminino")) {
                gender = Gender.Female;
            } else {
                gender = Gender.EMPTY;
            }
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(elements[2]);
            String [] rawAddress = readAddress(elements[3].trim());
            String street = rawAddress[0].trim();
            int doorNumber = Integer.parseInt(rawAddress[1].trim());
            String postalCode = rawAddress[2].trim();
            String city = rawAddress[3].trim();
            AddressDTO addressDTO = new AddressDTO(street, doorNumber, postalCode, city, COUNTRY);
            long phoneNumber = Integer.parseInt(elements[4].trim());
            String email = elements[5].trim();
            long snsNumber = Integer.parseInt(elements[6].trim());
            String citizenCard = elements[7].trim();
            snsUserDTO = new SnsUserDTO(nome, email, gender, date, snsNumber, addressDTO, phoneNumber, citizenCard);
            addSnsUser(snsUserDTO);
        } catch (ParseException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.out.println("The file contains errors and could not be loaded");
            LoadCsvFileUI.closeApplication();
        }
    }

    /**
     * simple method to read the address and split by a given dilimiter of the file to mapp the class Address
     * @param address - in a single string
     * @return a vector with splited string in the "/"
     */

    public static String[] readAddress(String address) {
        return address.split("\\|");
    }

    /**
     * method to verify if the file is of csv type. Throws an exception that is treated and the app closes for further
     * reinput of the file
     * @param filePath
     * @return boolean if the file is in correct format
     */

    public static boolean isCsvType(String filePath) {
        boolean isCsvFormat = Pattern.compile(".+\\.csv$").matcher(filePath).find();

        if (!isCsvFormat) {
            throw new InvalidTypeFileException("The type of format is not csv. Please enter the correct format");
        }
        return isCsvFormat;
    }

    /**
     * simple method to write an snsUser. The method only adds since the verification if it is duplicated is only needed
     * in the SnsUserStore class
     * @param snsUserDTO
     * @return boolean if the user is added
     */

    public boolean addSnsUser(SnsUserDTO snsUserDTO) {
        return snsUserDTOList.add(snsUserDTO);
    }

    /**
     * method to get the list of snsUserDTO
     * @return list of objects of type SnsUserDTO
     */

    public List<SnsUserDTO> getSnsUserDTOList() {
        return new ArrayList<>(snsUserDTOList);
    }


}
