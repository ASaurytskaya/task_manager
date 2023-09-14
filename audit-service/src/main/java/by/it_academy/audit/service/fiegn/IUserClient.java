package by.it_academy.audit.service.fiegn;

import by.it_academy.audit.core.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "user-client", url= "localhost:80/api/v1/users/me")
public interface IUserClient {
    @GetMapping
    ResponseEntity<User> getMe(@RequestHeader("Authorization") String token);

}
