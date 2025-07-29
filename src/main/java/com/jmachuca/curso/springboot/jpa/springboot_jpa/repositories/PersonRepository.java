package com.jmachuca.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jmachuca.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
    
    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Optional<Person> findOne(Long id);

    @Query("SELECT p FROM Person p WHERE p.name like %?1%")
    Optional<Person> findOneLikeName(String name);

    Optional<Person> findByNameContaining(String name);
    
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1")
    List<Person> buscarPorProgrammingLanguage(String programmingLanguage);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> obtenerPersonData();

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.name = ?1")
    List<Object[]> obtenerPersonData(String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage = ?1")
    List<Object[]> obtenerPersonDataPorLenguage(String programmingLanguage);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage = ?1 and p.name = ?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);

}
