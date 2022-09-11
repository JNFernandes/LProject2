package app.externalmodule;

import app.domain.interfaces.LoadDataFromExternalSource;
import app.mappers.dto.SnsUserDTO;
import java.io.FileNotFoundException;
import java.util.List;


public class CsvFileReaderAPISemiColonAdapter implements LoadDataFromExternalSource {

    /**
     * csvFileReaderSemiColonAdaptee atribute
     */

    private CsvFileReaderSemiColonAdaptee csvFileReaderSemiColonAdaptee;

    /**
     * CsvFileReaderAPISemiColonAdapter constructor
     */

    public CsvFileReaderAPISemiColonAdapter() {
        this.csvFileReaderSemiColonAdaptee = new CsvFileReaderSemiColonAdaptee();
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
        csvFileReaderSemiColonAdaptee.readFile(pathName);
        dtoList = csvFileReaderSemiColonAdaptee.getSnsUserDTOList();
        return dtoList;
    }
}
