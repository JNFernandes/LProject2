package app.domain.store.exception;

public class SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion extends RuntimeException {

    public SnsUserArrivalForSameDateAlreadyExistsInSoreWithAttributeExcepetion(String attribute, String attributeValue, String attribute2, String attribute2Value)
    {
        super(String.format("This %s %s already came to the vaccination center today, %s %s ", attribute, attributeValue, attribute2, attribute2Value));
    }

}

