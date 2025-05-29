package ru.home.training.java.cetrification.spring.exception;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(Long id) {
        super("Пользователь с id не найден: " + id);
    }
}
