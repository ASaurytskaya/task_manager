package by.it_academy.user.service;

import by.it_academy.user.core.dto.CustomUserDetails;
import by.it_academy.user.dao.entity.UserEntity;
import by.it_academy.user.service.api.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserService userService;

    public CustomUserDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userService.findByMail(username);
        CustomUserDetails.CustomUserDetailsBuilder builder = new CustomUserDetails.CustomUserDetailsBuilder();
        UserDetails userDetails = builder.setId(entity.getUserId()).
                setMail(entity.getMail()).
                setFio(entity.getFio()).
                setUserRole(entity.getUserRole()).
                setUserStatus(entity.getUserStatus()).
                setPassword(entity.getPassword()).build();

        return userDetails;
    }
}
