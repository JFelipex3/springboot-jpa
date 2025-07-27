package com.jmachuca.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jmachuca.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findByProgrammingLanguage(String programmingLanguage);

}
