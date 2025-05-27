package ru.home.training.java.cetrification.spring.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT p FROM Person p JOIN FETCH p.address")
    List<Person> findAllWithAddresses();
    @NonNull
    List<Person> findAll();
}

