package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.validation.Validation;
import javax.validation.Validator;

@Component
public class ExecutableValidatorImpl {

    @Bean
    public Validator getValidator(){
        return Validation.buildDefaultValidatorFactory()
                .getValidator();
    }
}
