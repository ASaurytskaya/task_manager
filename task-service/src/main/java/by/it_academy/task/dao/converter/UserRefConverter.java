package by.it_academy.task.dao.converter;

import by.it_academy.task.core.UserRef;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter
public class UserRefConverter implements AttributeConverter<UserRef, String> {

    @Override
    public String convertToDatabaseColumn(UserRef attribute) {
        return attribute.getUuid().toString();
    }

    @Override
    public UserRef convertToEntityAttribute(String dbData) {
        return new UserRef(UUID.fromString(dbData));
    }
}
