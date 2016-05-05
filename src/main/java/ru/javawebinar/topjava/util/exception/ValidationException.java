package ru.javawebinar.topjava.util.exception;

import org.springframework.validation.BindingResult;

/**
 * Created by Mikhail on 05.05.2016.
 */
public class ValidationException extends RuntimeException{

    public ValidationException(BindingResult bindingResult) {
        super(getDesc(bindingResult));
    }

    private static String getDesc(BindingResult bindingResult) {
        StringBuilder sb = new StringBuilder();
        bindingResult.getFieldErrors().forEach(e -> sb.append(e.getField()).append(" ").append(e.getDefaultMessage()));
        return sb.toString();
    }
}
