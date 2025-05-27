package ru.home.training.java.cetrification.spring.data;

import lombok.Data;

import jakarta.persistence.*;

/**
 * Сущность, представляющая человека в БД.
 * Содержит персональные данные (имя, фамилию) и связанный адрес проживания.
 * 
 * @see Address
 * @Entity Указывает, что класс является JPA сущностью.
 * @Table(name = "persons") Задает имя таблицы в БД.
 * @Data Lombok-аннотация, генерирующая геттеры, сеттеры, toString(), equals() и hashCode().
 */
@Entity
@Table(name = "persons")
// @Data - это комбинированная аннотация Lombok, которая автоматически генерирует:
//   Геттеры для всех полей.
//   Сеттеры для всех не-final полей.
//   toString(), equals() и hashCode() на основе всех полей класса.
@Data 
public class Person {

    private Long id;

    private String name;

    private String surname;

    private Address address;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    public Address getAddress() {
        return address;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
