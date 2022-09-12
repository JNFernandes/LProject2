package app.domain.store.exception;

public class SnsUserArrivalForNotHavingaScheduleWithAttributeException extends Throwable {

    public SnsUserArrivalForNotHavingaScheduleWithAttributeException(String attribute, String attributeValue)
    {
        super(String.format("This %s %s doesn't have a Vaccine Schedule or the SNS User number is incorrect.", attribute, attributeValue));
    }
}
