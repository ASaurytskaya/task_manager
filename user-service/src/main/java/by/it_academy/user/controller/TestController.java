package by.it_academy.user.controller;


import by.it_academy.user.service.UserHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class TestController {

    private UserHolder holder;

    public TestController(UserHolder holder) {
        this.holder = holder;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "user";
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET)
    public UserDetails details(){
        return holder.getUser();
    }
}
