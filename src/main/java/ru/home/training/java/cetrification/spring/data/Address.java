package ru.home.training.java.cetrification.spring.data;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Сущность, представляющая адрес в БД.
 * Содержит информацию о городе и улице, а также список людей, проживающих по этому адресу.
 * 
 * @see Person
 * @Entity Указывает, что класс является JPA сущностью.
 * @Table(name = "addresses") Задает имя таблицы в БД.
 */
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    
    private String city;
    
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    @JsonIgnore // Исключает поле persons из JSON 
    private Set<Person> persons = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public void setPersons(Set<Person> persons) {
        this.persons = persons;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
