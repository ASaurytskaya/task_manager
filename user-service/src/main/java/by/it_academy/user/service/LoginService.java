package by.it_academy.user.service;

import by.it_academy.user.controller.utils.JwtTokenHandler;
import by.it_academy.user.core.UserRole;
import by.it_academy.user.core.UserStatus;
import by.it_academy.user.core.dto.*;
import by.it_academy.user.dao.entity.ConfirmationEntity;
import by.it_academy.user.dao.entity.UserEntity;
import by.it_academy.user.service.api.ILoginService;
import by.it_academy.user.service.api.IUserService;
import by.it_academy.user.service.exceptions.UserPasswordIncorrectException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LoginService implements ILoginService {
    private static final String PASSWORD_INCORRECT = "Неверный пароль.";

    private final IUserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserHolder userHolder;
    private final UserDetailsService userDetailsService;
    private final JwtTokenHandler tokenHandler;
    private final ConfirmationService confirmationService;

    private final MailSenderService mailSenderService;

    public LoginService(IUserService userService, PasswordEncoder passwordEncoder, UserHolder userHolder,
                        UserDetailsService userDetailsService, JwtTokenHandler tokenHandler,
                        ConfirmationService confirmationService, MailSenderService mailSenderService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userHolder = userHolder;
        this.userDetailsService = userDetailsService;
        this.tokenHandler = tokenHandler;
        this.confirmationService = confirmationService;
        this.mailSenderService = mailSenderService;
    }

    @Override
    public String register(UserRegistration userRegistration) {
        UserSimleViewWithPass.UserSimpleViewWithPassBuilder builder = new UserSimleViewWithPass.UserSimpleViewWithPassBuilder();
        UserSimleViewWithPass user = builder.
                setMail(userRegistration.getMail()).
                setFio(userRegistration.getFio()).
                setUserRole(UserRole.USER).
                setUserStatus(UserStatus.WAITING_ACTIVATION).
                setPassword(passwordEncoder.encode(userRegistration.getPassword())).build();
        userService.save(user);

        activateMail(userRegistration.getMail());

        return tokenHandler.generateAccessToken(user);
    }

    @Override
    public void verify(MailVerification mailVerification) {
        confirmationService.confirmMail(mailVerification.getCode(), mailVerification.getMail());

        UserEntity entity = userService.findByMail(mailVerification.getMail());

        UserSimleViewWithPass.UserSimpleViewWithPassBuilder builder = new UserSimleViewWithPass.UserSimpleViewWithPassBuilder();
        UserSimleViewWithPass user = builder.setMail(entity.getMail()).
                setFio(entity.getFio()).
                setUserRole(entity.getUserRole()).
                setPassword(entity.getPassword()).
                setUserStatus(UserStatus.ACTIVATED).build();

        userService.update(user, entity.getUserId(), entity.getDtUpdate());

    }

    @Override
    public String logIn(UserLogin userLogin) {
        String mail = userLogin.getMail();
        UserDetails userDetails = userDetailsService.loadUserByUsername(mail);
        if(!passwordEncoder.matches(userLogin.getPassword(), userDetails.getPassword())) {
            throw new UserPasswordIncorrectException(PASSWORD_INCORRECT, "password");
        }
        return tokenHandler.generateAccessToken(userDetails);
    }

    @Override
    public UserView about() {
        UserDetails userDetails = userHolder.getUser();
        String mail = userDetails.getUsername();
        UserEntity entity = userService.findByMail(mail);
        UserView.UserBuilder builder = new UserView.UserBuilder();
        UserView user = builder.
                setUuid(entity.getUserId()).
                setDtCreate(entity.getDtCreate()).
                setDtUpdate(entity.getDtUpdate()).
                setMail(mail).
                setFio(entity.getFio()).
                setUserRole(entity.getUserRole()).
                setUserStatus(entity.getUserStatus()).build();
        return user;
    }

    private void activateMail(String mail) {
        String code = UUID.randomUUID().toString();
        ConfirmationEntity entity = new ConfirmationEntity(UUID.randomUUID(), mail, LocalDateTime.now(), code);
        confirmationService.save(entity);

        mailSenderService.sendMessage(mail, code);
    }
}
