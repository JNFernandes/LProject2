package app.externalmodule;

import app.domain.shared.Gender;
import app.mappers.dto.AddressDTO;
import app.mappers.dto.SnsUserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFileReaderAPISemiColonAdapterTest {

    private CsvFileReaderAPISemiColonAdapter csvFileReaderAPISemiColonAdapter;

    public CsvFileReaderAPISemiColonAdapterTest() {
        this.csvFileReaderAPISemiColonAdapter = new CsvFileReaderAPISemiColonAdapter();
    }

    List<SnsUserDTO> expectedList = new ArrayList<>();
    List<SnsUserDTO> actualList;

    @BeforeEach
    void setUP() {

        String filePath = "testAdapterSemiColon.csv";


        try {
            actualList = csvFileReaderAPISemiColonAdapter.readFileFromExternalSource(filePath);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            AddressDTO a1 = new AddressDTO("Rua das Flores", 111, "1234-123", "Porto", "Portugal");
            SnsUserDTO s1 = new SnsUserDTO("Joao",
                    "joao@joao.pt",
                    Gender.Male,
                    df.parse("14-12-1999"),
                    275341394L,
                    a1,
                    968885510L,
                    "13999985");

            expectedList.add(s1);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void readFileFromExternalSource() {
        Assertions.assertEquals(expectedList, actualList);
    }
}