package by.it_academy.user.service;

import by.it_academy.user.core.dto.UserSimleViewWithPass;
import by.it_academy.user.dao.entity.UserEntity;
import by.it_academy.user.service.api.IUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CommonUserDetailsService implements UserDetailsService {

    private final IUserService userService;

    public CommonUserDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userService.findByMail(username);
        UserSimleViewWithPass.UserSimpleViewWithPassBuilder builder = new UserSimleViewWithPass.UserSimpleViewWithPassBuilder();
        UserDetails userDetails = builder.setMail(entity.getMail()).
                setUserRole(entity.getUserRole()).
                setUserStatus(entity.getUserStatus()).
                setPassword(entity.getPassword()).build();

        return userDetails;
    }
}
