package by.it_academy.user.service;

import by.it_academy.user.service.exceptions.CustomMailSenderException;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class MailSenderExceptionHandler implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        throw new CustomMailSenderException(ex.getMessage());
    }
}
