package by.it_academy.task.dao.converter;

import by.it_academy.task.core.ProjectRef;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.UUID;

@Converter
public class ProjectRefConverter implements AttributeConverter<ProjectRef, String> {

    @Override
    public String convertToDatabaseColumn(ProjectRef attribute) {
        return attribute.getUuid().toString();
    }

    @Override
    public ProjectRef convertToEntityAttribute(String dbData) {
        return new ProjectRef(UUID.fromString(dbData));
    }
}
