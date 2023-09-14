package by.it_academy.user.controller;

import by.it_academy.user.config.converter.DateConverter;
import by.it_academy.user.core.dto.UserView;
import by.it_academy.user.core.dto.UserSimpleViewWithPass;
import by.it_academy.user.dao.entity.UserEntity;
import by.it_academy.user.service.api.IUserService;
import by.it_academy.user.util.TPage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final IUserService userService;
    private final DateConverter dateConverter;

    public UserController(IUserService userService, DateConverter dateConverter) {
        this.userService = userService;
        this.dateConverter = dateConverter;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getPage(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
       TPage<UserView> page1 = userService.getPage(page, size);


        return new ResponseEntity<>(page1, HttpStatus.valueOf(200));
    }

    @GetMapping(value = "/{uuid}", produces = "application/json")
    public ResponseEntity<?> getUser(@PathVariable UUID uuid) {
        UserView user = userService.entityToDto(userService.get(uuid));

        return new ResponseEntity<>(user, HttpStatus.valueOf(200));
    }

    @PostMapping
    public ResponseEntity<?> addUser(@Valid @RequestBody UserSimpleViewWithPass user) {
        UserEntity entity = userService.save(user);
        if(entity != null) {
            return new ResponseEntity<>(HttpStatus.valueOf( 201));
        }
        return new ResponseEntity<>(HttpStatus.valueOf( 500));
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserSimpleViewWithPass user, @PathVariable UUID uuid, @PathVariable(name = "dt_update") LocalDateTime dtUpdate) {
        userService.update(user, uuid, dtUpdate);
        return new ResponseEntity<>(HttpStatus.valueOf( 200));
    }
}
