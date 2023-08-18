package by.it_academy.user.controller;

import by.it_academy.user.core.dto.*;
import by.it_academy.user.service.api.IAuthorizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class AuthorizationController {

    private final IAuthorizationService loginService;

    public AuthorizationController(IAuthorizationService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody UserRegistration userRegistration) {
        String token = loginService.register(userRegistration);

        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> validate(@RequestParam String mail, @RequestParam UUID code) {
        loginService.verify(new MailVerification(mail, code));
        return new ResponseEntity<>(HttpStatus.valueOf(200));
    }

    @GetMapping("/login")
    public ResponseEntity<?> logIn(@Valid @RequestBody UserLogin userLogin) {
       String token = loginService.logIn(userLogin);
        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAbout() {
        UserView user = loginService.about();
        return new ResponseEntity<>(user, HttpStatus.valueOf(200));
    }
}
