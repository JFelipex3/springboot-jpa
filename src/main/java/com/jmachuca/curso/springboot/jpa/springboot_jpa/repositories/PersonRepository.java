package com.jmachuca.curso.springboot.jpa.springboot_jpa.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.jmachuca.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
import com.jmachuca.curso.springboot.jpa.springboot_jpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("SELECT MIN(p.id), MAX(p.id), SUM(p.id), AVG(length(p.name)), COUNT(p.id) FROM Person p")
    Object getResumenAggregationFunction();

    @Query("SELECT MIN(LENGTH(p.name)) FROM Person p")
    Integer getMinLengthName();

    @Query("SELECT MAX(LENGTH(p.name)) FROM Person p")
    Integer getMaxLengthName();

    @Query("SELECT p.name, LENGTH(p.name) FROM Person p")
    List<Object[]> getPersonNameLength();

    @Query("SELECT MIN(p.id) FROM Person p")
    Long getMinPersonId();

    @Query("SELECT MAX(p.id) FROM Person p")
    Long getMaxPersonId();

    @Query("SELECT COUNT(p) FROM Person p")
    Long countPersons();

    List<Person> findByIdBetweenOrderByName(int id1, int id2);

    List<Person> findByNameBetweenOrderByName(String c1, String c2);

    @Query("SELECT p FROM Person p WHERE p.name BETWEEN ?1 AND ?2 ORDER BY p.name")
    List<Person> findAllBetweenName(String c1, String c2);

    @Query("SELECT p FROM Person p WHERE p.id BETWEEN ?1 AND ?2 ORDER BY p.name")
    List<Person> findAllBetweenId(int id1, int id2);

    @Query("SELECT UPPER(CONCAT(p.name, ' ', p.lastname)) as fullName FROM Person p")
    List<String> getFullNameConcatUpper();

    @Query("SELECT LOWER(CONCAT(p.name, ' ', p.lastname)) as fullName FROM Person p")
    List<String> getFullNameConcatLower();

    @Query("SELECT p.name || ' ' || p.lastname as fullName FROM Person p")
    List<String> getFullNameConcatenadoPipe();

    @Query("SELECT CONCAT(p.name, ' ', p.lastname) as fullName FROM Person p")
    List<String> getFullNameConcat();

    @Query("SELECT p.name FROM Person p")
    List<String> findAllNames();

    @Query("SELECT DISTINCT(p.name) FROM Person p")
    List<String> findAllNamesDistinct();

    @Query("SELECT DISTINCT(p.programmingLanguage) FROM Person p")
    List<String> findAllProgrammingLanguageDistinct();

    @Query("SELECT COUNT(DISTINCT(p.programmingLanguage)) FROM Person p")
    Long countProgrammingLanguageDistinct();
    
    @Query("SELECT new com.jmachuca.curso.springboot.jpa.springboot_jpa.dto.PersonDto(p.name, p.lastname) FROM Person p")
    List<PersonDto> findAllPersonDto();

    @Query("SELECT new Person(p.name, p.lastname) FROM Person p")
    List<Person> findAllObjectPersonPersonalized();

    @Query("SELECT p.name FROM Person p WHERE p.id = ?1")
    String getNameById(Long id);

    @Query("SELECT p.id FROM Person p WHERE p.id = ?1")
    Long getIdById(Long id);

    @Query("SELECT CONCAT(p.name, ' ', p.lastname) as fullName FROM Person p WHERE p.id = ?1")
    String getFullNameById(Long id);

    @Query("SELECT p FROM Person p WHERE p.id = ?1")
    Optional<Person> findOne(Long id);

    @Query("SELECT p FROM Person p WHERE p.name like %?1%")
    List<Person> findLikeName(String name);

    Optional<Person> findByNameContaining(String name);
    
    List<Person> findByProgrammingLanguage(String programmingLanguage);

    @Query("SELECT p FROM Person p WHERE p.programmingLanguage = ?1")
    List<Person> buscarPorProgrammingLanguage(String programmingLanguage);

    List<Person> findByProgrammingLanguageAndName(String programmingLanguage, String name);

    @Query("SELECT p, p.programmingLanguage FROM Person p")
    List<Object[]> findAllMixPerson();

    @Query("SELECT p.id, p.name, p.lastname, p.programmingLanguage FROM Person p WHERE p.id = ?1")
    Object obtenerPersonDataFull(Long id);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p")
    List<Object[]> obtenerPersonData();

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.name = ?1")
    List<Object[]> obtenerPersonData(String name);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage = ?1")
    List<Object[]> obtenerPersonDataPorLenguage(String programmingLanguage);

    @Query("SELECT p.name, p.programmingLanguage FROM Person p WHERE p.programmingLanguage = ?1 and p.name = ?2")
    List<Object[]> obtenerPersonData(String programmingLanguage, String name);

}
