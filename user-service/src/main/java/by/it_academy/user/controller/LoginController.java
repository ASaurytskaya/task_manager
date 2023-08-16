package by.it_academy.user.controller;

import by.it_academy.user.controller.utils.JwtTokenHandler;
import by.it_academy.user.core.dto.*;
import by.it_academy.user.service.api.ILoginService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class LoginController {


    private final ILoginService loginService;
    private final JwtTokenHandler jwtHandler;

    public LoginController(ILoginService loginService, JwtTokenHandler jwtHandler) {
        this.loginService = loginService;
        this.jwtHandler = jwtHandler;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid @RequestBody UserRegistration userRegistration) {
        String token = loginService.register(userRegistration);

        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @GetMapping("/verification")
    public ResponseEntity<?> validate(@RequestParam String mail, @RequestParam String code) {
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
