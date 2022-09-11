package app.externalmodule;

import app.domain.shared.Gender;
import app.mappers.dto.AddressDTO;
import app.mappers.dto.SnsUserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileReaderSemiColonAdapteeTest {

    private List<SnsUserDTO> snsUserDTOList;

    private static final String [] HEADER = {"Name",
            "Sex",
            "Birth Date",
            "Address",
            "Phone Number",
            "E-mail",
            "SNS User Number",
            "Citizen Card Number"};


    public String [] headerTestPass = {"Name",
            "Sex",
            "Birth Date",
            "Address",
            "Phone Number",
            "E-mail",
            "SNS User Number",
            "Citizen Card Number"};

    public String [] headerTestFail = {"Sex",
            "Sex",
            "Sex",
            "Address",
            "Phone Number",
            "E-mail",
            "SNS User Number",
            "Citizen Card Number"};

    public CsvFileReaderSemiColonAdaptee csvFileReaderSemiColonAdaptee;

    boolean headeMatches;

    String address;
    String [] actualAddress;
    String [] expectedAddress;

    String fileName;
    String fileNameFails;
    boolean fileNameResult;

    private List<SnsUserDTO> actualDTOList;

    private String fileNameWithErrors;
    private String fileWithInvalidDelimiter;
    private String [] StringWithInvalidFields;

    public CsvFileReaderSemiColonAdapteeTest() {
        this.csvFileReaderSemiColonAdaptee = new CsvFileReaderSemiColonAdaptee();
        snsUserDTOList = new ArrayList<>();
    }

    @BeforeEach
    void setUP() {
        headeMatches = CsvFileReaderSemiColonAdaptee.checkHeader(HEADER, headerTestPass);

        address = "Rua das Flores|111|1234-234|Porto|Portugal";
        expectedAddress = new String[]{"Rua das Flores",
                "111",
                "1234-234",
                "Porto",
                "Portugal"};

        actualAddress = CsvFileReaderSemiColonAdaptee.readAddress(address);

        fileName = "asdasd.csv";
        fileNameFails = "asdas.txt";
        fileNameResult = CsvFileReaderSemiColonAdaptee.isCsvType(fileName);

        actualDTOList = csvFileReaderSemiColonAdaptee.getSnsUserDTOList();

        fileNameWithErrors = "test_withErrorsMoreLessFields.csv";
        fileWithInvalidDelimiter = "test_InvalidDelimiter.csv";
        StringWithInvalidFields = new String[]{
               "João Fernandes",
               "Male",
               "14",
                "Rua do Porto/89/3700-265/Porto/Portugal",
                "967569747",
                "joaofernandes@gmail.com",
                "275341394",
                "139999850ZY3"};
    }

    @Test
    void checkHeader() {
        assertTrue(headeMatches);
        assertThrows(HeaderNotMatchException.class,
                ()-> CsvFileReaderSemiColonAdaptee.checkHeader(HEADER, headerTestFail));

    }

    @Test
    void readAddress() {
        Assertions.assertArrayEquals(expectedAddress, actualAddress);
    }

    @Test
    void isCsvType() {
        Assertions.assertTrue(fileNameResult);
        Assertions.assertThrows(InvalidTypeFileException.class, ()->
                CsvFileReaderSemiColonAdaptee.isCsvType(fileNameFails));
    }

    @Test
    void addSnsUser()  {
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            AddressDTO a1 = new AddressDTO("Rua das Flores", 111, "1234-234", "Porto", "Portugal");
            SnsUserDTO s1 = new SnsUserDTO("Joao",
                    "joao@joao.pt",
                    Gender.Male,
                    df.parse("14-12-1999"),
                    275341394L,
                    a1,
                    968885510L,
                    "13999985");
            Assertions.assertTrue(snsUserDTOList.add(s1));
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void getSnsUserDTOList() {
        assertEquals(snsUserDTOList, actualDTOList);
    }

//    @Test
//    void readFile() {
//        Assertions.assertThrows(ListLinesErrorsException.class,
//                ()->csvFileReaderSemiColonAdaptee.readFile(fileNameWithErrors));
//        Assertions.assertThrows(ListLinesErrorsException.class,
//                ()->csvFileReaderSemiColonAdaptee.readFile(fileWithInvalidDelimiter));
//    }
//
//    @Test
//    void readFileLine() {
//        Assertions.assertThrows(ParseException.class,
//                ()-> csvFileReaderSemiColonAdaptee.readFileLine(StringWithInvalidFields));
//
//    }
}