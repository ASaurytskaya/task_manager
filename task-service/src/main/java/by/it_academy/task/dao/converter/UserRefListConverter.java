package by.it_academy.task.dao.converter;

import by.it_academy.task.core.UserRef;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Converter
public class UserRefListConverter implements AttributeConverter<List<UserRef>, List<String>> {

    @Override
    public List<String> convertToDatabaseColumn(List<UserRef> attribute) {
        List<String> lS = new ArrayList<>();
        for (UserRef userRef : attribute) {
            lS.add(userRef.getUserID().toString());
        }
        return lS;
    }

    @Override
    public List<UserRef> convertToEntityAttribute(List<String> dbData) {
        List<UserRef> lU = new ArrayList<>();
        for (String s : dbData) {
            lU.add(new UserRef(UUID.fromString(s)));
        }
        return lU;
    }
}
