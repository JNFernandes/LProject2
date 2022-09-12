package app.domain.store.exception;

public class SnsUserArrivalForIncorretVaccinationCenterWithAttributeExcepetion  extends RuntimeException {

    public SnsUserArrivalForIncorretVaccinationCenterWithAttributeExcepetion(String attribute, String attributeValue, String attribute2, String attribute2Value)
    {
        super(String.format("This %s %s is at the incorrect Vaccination Center %s %s ", attribute, attributeValue, attribute2, attribute2Value));
    }
}
