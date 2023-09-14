package by.it_academy.task.service.feign;

import by.it_academy.task.core.dto.UserSimpleViewWithId;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(value = "user-client", url= "localhost:80/api/v1/users")
public interface IUserClient {
    @GetMapping(path = "/{uuid}")
    UserSimpleViewWithId getUser(@PathVariable UUID uuid , @RequestHeader(name = "Authorization") String token);

}
