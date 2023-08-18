package by.it_academy.user.config.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;


@Component
public class DateConverter implements Converter<Long, LocalDateTime> {

    @Override
    public LocalDateTime convert(Long value) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
    }

}
