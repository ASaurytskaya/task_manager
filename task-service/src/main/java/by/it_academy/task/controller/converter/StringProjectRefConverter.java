package by.it_academy.task.controller.converter;

import by.it_academy.task.core.ProjectRef;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringProjectRefConverter implements Converter<String, ProjectRef> {
    @Override
    public ProjectRef convert(String source) {
        return new ProjectRef(UUID.fromString(source));
    }
}
