package com.jmachuca.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jmachuca.curso.springboot.jpa.springboot_jpa.entities.Person;
import com.jmachuca.curso.springboot.jpa.springboot_jpa.repositories.PersonRepository;

import jakarta.transaction.Transactional;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner{

	@Autowired
	private PersonRepository personRepository; 
	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		update();
		
	}

	@Transactional
	public void create() {

		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();

		System.out.print("Ingrese el apellido: ");
		String lastName = scanner.next();

		System.out.print("Ingrese Lenguaje de Programación: ");
		String programmingLanguage = scanner.next();

		Person person = new Person(null, name, lastName, programmingLanguage);

		Person personNew = personRepository.save(person);
		System.out.println(personNew);

		scanner.close();
	}

	@Transactional
	public void update() {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Ingrese el id de la persona: ");
		Long id = scanner.nextLong();
		
		Optional<Person> optionalPerson = personRepository.findById(id);

		optionalPerson.ifPresent(person -> {
			System.out.println(person);
			System.out.print("Ingrese el lenguaje de programación: ");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage);
			Person personDb = personRepository.save(person);
			System.out.println(personDb);
		});

		scanner.close();

	}

	public void findOne() {
		/*
		Person person = null;
		Optional<Person> optionalPerson = personRepository.findById(1L);

		if (optionalPerson.isPresent()) {
			person = optionalPerson.get();
		}

		System.out.println(person);
		*/

		//personRepository.findById(1L).ifPresent(person -> System.out.println(person));

		//personRepository.findById(1L).ifPresent(System.out::println); // Lo mismo que el anterior pero usando metodo de referencia

		personRepository.findOne(1L).ifPresent(System.out::println);
		personRepository.findOneLikeName("ria").ifPresent(System.out::println);
		personRepository.findByNameContaining("an").ifPresent(System.out::println);
	}

	public void list() {

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
