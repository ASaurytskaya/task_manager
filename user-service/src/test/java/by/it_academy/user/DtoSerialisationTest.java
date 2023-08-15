package by.it_academy.user;

import by.it_academy.user.util.TBaseEssence;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@SpringBootTest
public class DtoSerialisationTest {
    @Test
    public void whenSerializingDateToISO8601_thenSerializedToText()
            throws JsonProcessingException, ParseException {

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");



        LocalDateTime date = LocalDateTime.parse("1970-01-01T02:30:00");
        TBaseEssence event = new TBaseEssence(UUID.randomUUID(), date, date);

        ObjectMapper mapper = new ObjectMapper();
        //mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // StdDateFormat is ISO8601 since jackson 2.9
        mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

        mapper.registerModule(new JavaTimeModule());

        String result = mapper.writeValueAsString(event.getDt_create());

        assertThat(result, containsString("1970-01-01T02:30:00"));
    }
}
