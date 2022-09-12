package app.domain.interfaces;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CenterPerformanceCalculation {

    void calculatePerformance(int timeResolution, LocalDate day, int workingPeriod,
                              List<LocalDateTime> listArrivals, List<LocalDateTime> listLeavings);

}
