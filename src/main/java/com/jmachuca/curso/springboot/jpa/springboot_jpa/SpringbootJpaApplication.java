package com.jmachuca.curso.springboot.jpa.springboot_jpa;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jmachuca.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.jmachuca.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository personRepository; 
	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		List<Person> persons = (List<Person>) personRepository.findAll();
		System.out.println("Listado de Personas");
		persons.stream().forEach(person -> {
			System.out.println(person);
		});
		System.out.println("*****************************************");

		List<Person> persons2 = (List<Person>) personRepository.buscarPorProgrammingLanguage("Java");
		System.out.println("Listado de Personas por Lenguaje de Programacion Java");
		persons2.stream().forEach(person -> {
			System.out.println(person);
		});
		System.out.println("*****************************************");

		List<Person> persons3 = (List<Person>) personRepository.findByProgrammingLanguageAndName("Java", "Andres");
		System.out.println("Listado de Personas por Lenguaje de Programacion Java y nombre especifico");
		persons3.stream().forEach(person -> {
			System.out.println(person);
		});
		System.out.println("*****************************************");

		List<Object[]> personsValues = personRepository.obtenerPersonData();
		System.out.println("Listado de Personas con Nombre y lenguaje de programacion");
		personsValues.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});
		System.out.println("*****************************************");

		List<Object[]> personsValues2 = personRepository.obtenerPersonData("John");
		System.out.println("Listado de Personas con Nombre y lenguaje de programacion");
		personsValues2.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});
		System.out.println("*****************************************");
	}

}
