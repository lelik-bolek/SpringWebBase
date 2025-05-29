package ru.home.training.java.cetrification.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.home.training.java.cetrification.spring.data.Person;
import ru.home.training.java.cetrification.spring.data.PersonRepository;
import ru.home.training.java.cetrification.spring.exception.PersonNotFoundException;

import java.util.List;

//@ResponseStatus(HttpStatus.NOT_FOUND)
//class PersonNotFoundException extends RuntimeException {}

/**
 * REST-контроллер для работы с сущностью Person.
 * Предоставляет CRUD-операции (Create, Read, Update, Delete) через HTTP-методы.
 * Все эндпоинты начинаются с базового пути "/person".
 *
 * <p><strong>Используемые технологии:</strong>
 * <ul>
 *   <li>Spring Boot (Web, Data JPA)</li>
 *   <li>Hibernate (ORM)</li>
 * </ul>
 *
 * <p><strong>Примеры запросов:</strong>
 * <ul>
 *   <li>POST /persons - {"name":"Иван","surname":"Петров","address":1}</li>
 *   <li>GET /persons/1 - получение пользователя с ID=1</li>
 *   <li>PATCH /persons/1 - {"name":"НовоеИмя"} - частичное обновление</li>
 * </ul>
 *
 * @see Person
 * @see PersonRepository
 * @RestController Аннотация, объединяющая @Controller и @ResponseBody
 * @RequestMapping("/person") Базовый путь для всех эндпоинтов контроллера
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController (PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    // Метод для получения всех пользователей
    @GetMapping()
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return ResponseEntity.ok(persons);
    }
    
    // Метод для получения пользователя по id
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Person person = personRepository.findById(id)
            .orElseThrow(() -> new PersonNotFoundException(id));
        return ResponseEntity.ok(person);
    }

    // Метод для создания нового пользователя
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person){
        Person savedPerson = personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    // получение всех по адресов
    @GetMapping("/addresses")
    public ResponseEntity<List<Person>> getAllPersonWithAddresses() {
        List<Person> persons = personRepository.findAllWithAddresses();
        return ResponseEntity.ok(persons);
    }
    
    // Метод для обновления данных пользователя
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, 
         @RequestBody Person person) {
        person.setId(id);   // Устанавливаем ID из URL в объект Person
        Person updatedPerson = personRepository.save(person);
        return ResponseEntity.ok(updatedPerson);
    }

    // Метод для частичного обновления данных пользователя
    @PatchMapping("/{id}")
    public ResponseEntity<Person> patchPerson(@PathVariable Long id, 
           @RequestBody Person updatedPerson) {
        Person existingPerson = personRepository.findById(id).orElse(null);
        if (existingPerson == null) {
            return ResponseEntity.notFound().build(); // Возвращает HTTP 404 с пустым телом.
        }
        if (updatedPerson.getName() != null) {
            existingPerson.setName(updatedPerson.getName());
        }
        if (updatedPerson.getAddress() != null) {
            existingPerson.setAddress(updatedPerson.getAddress());
        }
        Person savedPerson = personRepository.save(existingPerson);
        return ResponseEntity.ok(savedPerson);
    }

    // Метод для удаления пользователя
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        // проверка есть ли такой пользователь
        Person existingPerson = personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
        personRepository.delete(existingPerson);
        //personRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Возвращает HTTP 204 (No Content) с пустым телом.
    }
}
