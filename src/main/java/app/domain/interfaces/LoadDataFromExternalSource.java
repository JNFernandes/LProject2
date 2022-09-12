package app.domain.interfaces;

import java.io.FileNotFoundException;
import java.util.List;

public interface LoadDataFromExternalSource
{
    <E> List<E> readFileFromExternalSource(String filePath) throws FileNotFoundException;
}
