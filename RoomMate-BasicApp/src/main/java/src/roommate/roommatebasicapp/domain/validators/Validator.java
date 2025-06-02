package src.roommate.roommatebasicapp.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}