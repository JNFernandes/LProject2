package app.domain.store.exception;

public class SnsUserArrivalForClosedVaccinationCenterWithAttributeExcepetion extends Throwable {

    public SnsUserArrivalForClosedVaccinationCenterWithAttributeExcepetion(String attribute, String attributeValue, String attribute2, String attribute2Value)
    {
        super(String.format("This %s %s arrived outside Vaccination Center %s %s working hours", attribute, attributeValue, attribute2, attribute2Value));
    }
}
