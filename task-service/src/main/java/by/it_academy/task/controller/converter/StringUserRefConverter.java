package by.it_academy.task.controller.converter;

import by.it_academy.task.core.UserRef;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class StringUserRefConverter implements Converter<String, UserRef> {
    @Override
    public UserRef convert(String source) {
        return new UserRef(UUID.fromString(source));
    }
}
