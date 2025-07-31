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
		Scanner scanner = new Scanner(System.in);
		int option;

		do {
			menu();
			System.out.print("Ingresa Opción deseada: ");
			while (!scanner.hasNextInt()) {
				System.out.println("Entrada inválida. Por favor, ingrese un número.");
				scanner.next(); // descartar entrada incorrecta
				System.out.print("Ingresa Opción deseada: ");
			}
			option = scanner.nextInt();
			scanner.nextLine(); // consumir el salto de línea

			switch (option) {
				case 1:
					create(scanner);
					break;
				case 2:
					update(scanner);
					break;
				case 3:
					delete(scanner);
					break;
				case 4:
					list();
					break;
				case 5:
					findOne(scanner);
					break;
				case 6:
					System.out.println("Saliendo de la aplicación. ¡Hasta luego!");
					break;
				default:
					System.out.println("Opción no válida. Intente de nuevo.");
					break;
			}

			if (option != 6) {
				System.out.println("\nPresione Enter para continuar...");
				scanner.nextLine();
			}
		} while (option != 6);

		scanner.close();
	}

	public void menu() {
		System.out.println("");
		System.out.println("*****************************************");
		System.out.println("Opciones Programa");
		System.out.println("-----------------");
		System.out.println("");
		System.out.println("1. Crear Persona");
		System.out.println("2. Actualizar Persona");
		System.out.println("3. Eliminar Persona");
		System.out.println("4. Listar Personas");
		System.out.println("5. Buscar Persona por ID");
		System.out.println("6. Salir");
		System.out.println("");
	}

	@Transactional
	public void create(Scanner scanner) {
		System.out.print("Ingrese el nombre: ");
		String name = scanner.nextLine();

		System.out.print("Ingrese el apellido: ");
		String lastName = scanner.nextLine();

		System.out.print("Ingrese Lenguaje de Programación: ");
		String programmingLanguage = scanner.nextLine();

		Person person = new Person(null, name, lastName, programmingLanguage);

		Person personNew = personRepository.save(person);
		System.out.println("Persona creada: " + personNew);
	}

	@Transactional
	public void update(Scanner scanner) {
		list();
		System.out.print("Ingrese el id de la persona a actualizar: ");
		Long id = scanner.nextLong();
		scanner.nextLine(); // consume newline
		
		Optional<Person> optionalPerson = personRepository.findById(id);

		optionalPerson.ifPresentOrElse(person -> {
			System.out.println("Persona encontrada: " + person);
			System.out.print("Ingrese el nuevo lenguaje de programación: ");
			String programmingLanguage = scanner.nextLine();
			person.setProgrammingLanguage(programmingLanguage);
			Person personDb = personRepository.save(person);
			System.out.println("Persona actualizada: " + personDb);
		}, () -> System.out.println("Persona con id " + id + " no encontrada."));
	}

	@Transactional
	public void delete(Scanner scanner) {
		list();
		System.out.print("Ingrese el id de la persona a eliminar: ");
		long id = scanner.nextLong();
		scanner.nextLine(); // consume newline

		Optional<Person> personOptional = personRepository.findById(id);
		personOptional.ifPresentOrElse(person -> {
			personRepository.deleteById(id);
			System.out.println("Persona con id " + id + " eliminada.");
			System.out.println("Lista de personas actualizada:");
			list();
		}, () -> {
			System.out.println("Persona con id " + id + " no encontrada.");
		});
	}

	public void findOne(Scanner scanner) {
		System.out.print("Ingrese el id de la persona a buscar: ");
		long id = scanner.nextLong();
		scanner.nextLine(); // consume newline
		
		personRepository.findById(id)
			.ifPresentOrElse(
				person -> System.out.println("Persona encontrada: " + person), 
				() -> System.out.println("Persona con id " + id + " no encontrada.")
			);
	}

	public void list() {
		List<Person> persons = (List<Person>) personRepository.findAll();
		System.out.println("Listado de Personas");
		persons.stream().forEach(person -> {
			System.out.println(person);
		});
	}

}
