package app.externalmodule;

import app.domain.interfaces.LoadDataFromExternalSource;
import app.mappers.dto.SnsUserDTO;


import java.io.FileNotFoundException;
import java.util.*;

public class CsvFileReaderAPICommaAdapter implements LoadDataFromExternalSource {

    /**
     * csvFileReaderCommaAdaptee atribute
     */

    private CsvFileReaderCommaAdaptee csvFileReaderCommaAdaptee;

    /**
     * CsvFileReaderAPICommaAdapter constructor
     */

    public CsvFileReaderAPICommaAdapter() {
        this.csvFileReaderCommaAdaptee = new CsvFileReaderCommaAdaptee();
    }

    /**
     * method that is overriden by the method in the interface
     * @param pathName - file path given in the UI
     * @return dtoList
     * @throws FileNotFoundException - to be treated in the UI
     */

    @Override
    public List<SnsUserDTO> readFileFromExternalSource(String pathName) throws FileNotFoundException {
        List<SnsUserDTO> dtoList;
        csvFileReaderCommaAdaptee.readFile(pathName);
        dtoList = csvFileReaderCommaAdaptee.getSnsUserDTOList();
        return dtoList;
    }
}
