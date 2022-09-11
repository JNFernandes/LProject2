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

class CsvFileReaderCommaAdapteeTest {

    public CsvFileReaderCommaAdaptee csvFileReaderCommaAdaptee;

    private List<SnsUserDTO> snsUserDTOList;

    String address;
    String [] actualAddress;
    String [] expectedAddress;

    String fileName;
    String fileNameFails;
    boolean fileNameResult;

    private List<SnsUserDTO> actualDTOList;



    public CsvFileReaderCommaAdapteeTest() {
        this.csvFileReaderCommaAdaptee = new CsvFileReaderCommaAdaptee();
        snsUserDTOList = new ArrayList<>();
    }
    @BeforeEach
    void setUP() {
        address = "Rua das Flores|111|1234-234|Porto|Portugal";
        expectedAddress = new String[]{"Rua das Flores",
                "111",
                "1234-234",
                "Porto",
                "Portugal"};

        actualAddress = CsvFileReaderCommaAdaptee.readAddress(address);

        fileName = "asdasd.csv";
        fileNameFails = "asdas.txt";
        fileNameResult = CsvFileReaderSemiColonAdaptee.isCsvType(fileName);

        actualDTOList = csvFileReaderCommaAdaptee.getSnsUserDTOList();
    }

    @Test
    void readAddress() {
        Assertions.assertArrayEquals(expectedAddress, actualAddress);
    }

    @Test
    void isCsvType() {
        Assertions.assertTrue(fileNameResult);
        Assertions.assertThrows(InvalidTypeFileException.class, ()->
                CsvFileReaderCommaAdaptee.isCsvType(fileNameFails));
    }

    @Test
    void addSnsUser() {
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

}