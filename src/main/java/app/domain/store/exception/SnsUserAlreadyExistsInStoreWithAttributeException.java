package app.domain.store.exception;

public class SnsUserAlreadyExistsInStoreWithAttributeException extends RuntimeException {

    public SnsUserAlreadyExistsInStoreWithAttributeException(String attribute, String attributeValue)
    {
        super(String.format("A SNS user already exists with attribute %s equals to %s", attribute, attributeValue));
    }

}
