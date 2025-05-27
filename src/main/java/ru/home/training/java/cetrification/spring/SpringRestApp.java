package ru.home.training.java.cetrification.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import ru.home.training.java.cetrification.spring.data.Address;
import ru.home.training.java.cetrification.spring.data.AddressRepository;
import ru.home.training.java.cetrification.spring.data.Person;
import ru.home.training.java.cetrification.spring.data.PersonRepository;

import java.util.List;

@SpringBootApplication
public class SpringRestApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringRestApp.class, args);

        PersonRepository personRepository = context.getBean(PersonRepository.class);
        AddressRepository addressRepository = context.getBean(AddressRepository.class);
        Person person1 = new Person();
        person1.setName("Вася");
        person1.setSurname("Иванов");

        Person person2 = new Person();
        person2.setName("Дима");
        person2.setSurname("Петров");

        Address address = new Address();
        Address address2 = new Address();

        address.setCity("Москва");
        address.setStreet("Плющиха");

        address2.setCity("Москва");
        address2.setStreet("Довженко");

        address.getPersons().add(person1);
        address2.getPersons().add(person2);
        
        person1.setAddress(address);
        person2.setAddress(address2);

        addressRepository.save(address);
        addressRepository.save(address2);
        // каскадное сохранение адреса и проживающих

        // Здесь запрос по первичному ключу, загружаются
        // сразу и Person, и его Address
        Person p1 = personRepository.findById(1L).get();
        System.out.println(p1.getName()+p1.getAddress());

        System.out.println("Список всех людей:");
        List<Person> all = personRepository.findAllWithAddresses();
        for (Person p: all) {
            System.out.println(p);
        }

    }

}



