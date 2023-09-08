package validator;

public interface Validator {
    public <T> boolean isValid(T object);
}
