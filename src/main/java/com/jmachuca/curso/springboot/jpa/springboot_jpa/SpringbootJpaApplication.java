package com.jmachuca.curso.springboot.jpa.springboot_jpa;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.jmachuca.curso.springboot.jpa.springboot_jpa.dto.PersonDto;
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
					findCoincidence(scanner);
					break;
				case 7:
					//personalizedQueries(scanner);
					personalizedQueries2();
					break;
				case 8:
					personalizedQueriesDistinct();
					break;
				case 9:
					personalizedQueriesConcatUpperAndLowerCase();
					break;
				case 10:
					personalizedQueriesBetweenAndOrderBy();
					break;
				case 11:
					personalizedQueriesCountMaxMinLengthSumAvg();
					break;
				case 12:
					personalizedQueriesSubquery();
					break;
				case 13:
					personalizedQueriesWhereIN();
					break;
				case 0:
					System.out.println("Saliendo de la aplicación. ¡Hasta luego!");
					break;
				default:
					System.out.println("Opción no válida. Intente de nuevo.");
					break;
			}

			if (option != 0) {
				System.out.println("\nPresione Enter para continuar...");
				scanner.nextLine();
			}
		} while (option != 0);

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
		System.out.println("6. Buscar Persona por Coincidencia");
		System.out.println("7. Consultas Personalizadas");
		System.out.println("8. Consultas Personalizadas Distinct");
		System.out.println("9. Consultas Personalizadas Concat Upper and LowerCase");
		System.out.println("10. Consultas Personalizadas Between And Order By");
		System.out.println("11. Consultas Personalizadas Count - Max - Min - Length -  Sum - Avg");
		System.out.println("12. Consultas con Subquery o Subconsulta en JPQL");
		System.out.println("13. Consulta por Where IN");
		System.out.println("0. Salir");
		System.out.println("");
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesWhereIN() {
		System.out.println("===================== Consulta por Where IN (1, 2, 5) =====================");
		List<Long> ids = List.of(1L, 2L, 5L);
		List<Person> persons = personRepository.getPersonsByIds(ids);
		persons.stream().forEach(person -> {
			System.out.println("Persona: " + person.getName() + " " + person.getLastname() + ", ID: " + person.getId());
		});	
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesSubquery() {
		System.out.println("===================== Consulta con Subquery: Nombres más cortos =====================");
		List<Object[]> shortersNames = personRepository.getShorterName();
		shortersNames.stream().forEach(person -> {
			System.out.println("Nombre: " + person[0] + ", Longitud: " + person[1]);
		});

		System.out.println("");
		System.out.println("===================== Consulta con Subquery: Nombres más largos =====================");
		List<Object[]> largesNames = personRepository.getLargeName();
		largesNames.stream().forEach(person -> {
			System.out.println("Nombre: " + person[0] + ", Longitud: " + person[1]);
		});

		System.out.println("");
		System.out.println("===================== Consulta con Subquery: Persona con ID mayor =====================");
		Optional<Person> lastRegistration = personRepository.getLastRegistration();
		lastRegistration.ifPresentOrElse(person -> {
			System.out.println("Último registro: " + person);
		}, () -> {
			System.out.println("No se encontró ningún registro.");
		});
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesCountMaxMinLengthSumAvg() {
		System.out.println("===================== Consulta Count =====================");
		Long count = personRepository.countPersons();
		System.out.println("Cantidad de personas: " + count);
	
		System.out.println("");
		System.out.println("===================== Consulta Max ID =====================");
		Long maxId = personRepository.getMaxPersonId();
		System.out.println("ID máximo de persona: " + maxId);

		System.out.println("");
		System.out.println("===================== Consulta Min ID =====================");
		Long minIdPerson = personRepository.getMinPersonId();
		System.out.println("ID mínimo de persona: " + minIdPerson);

		System.out.println("");
		System.out.println("===================== Consulta Length Nombre Persona =====================");
		List<Object[]> regs = personRepository.getPersonNameLength();
		regs.stream().forEach(person -> {
			System.out.println("Nombre: " + person[0] + ", Longitud: " + person[1]);
		});

		System.out.println("");
		System.out.println("===================== Consulta Length Nombre Persona Más Corto =====================");
		int minName = personRepository.getMinLengthName();
		System.out.println("Longitud mínima del nombre de persona: " + minName);

		System.out.println("");
		System.out.println("===================== Consulta Length Nombre Persona Más Largo =====================");
		int maxName = personRepository.getMaxLengthName();
		System.out.println("Longitud máxima del nombre de persona: " + maxName);

		System.out.println("");
		System.out.println("===================== Consulta Agregación Resumen (Min - Max - Sum - Avg - Length - Count) =====================");
		Object regsResume = personRepository.getResumenAggregationFunction();
		Object[] resume = (Object[]) regsResume;
		System.out.println("Resumen de Agregación:");
		System.out.println("ID Mínimo: " + resume[0]);
		System.out.println("ID Máximo: " + resume[1]);
		System.out.println("Suma de IDs: " + resume[2]);
		System.out.println("Promedio de Longitud de Nombres: " + resume[3]);
		System.out.println("Cantidad de Personas: " + resume[4]);

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesBetweenAndOrderBy() {
		System.out.println("===================== Consulta personas between ID 2 y 5 + Order By Name =====================");
		List<Person> persons = personRepository.findAllBetweenId(2, 5);
		persons.stream().forEach(person -> {
			System.out.println("Persona: " + person.getName() + " " + person.getLastname() + ", ID: " + person.getId());
		});

		System.out.println("");
		System.out.println("===================== Consulta personas between name J y P + Order By Name =====================");
		List<Person> personsName = personRepository.findAllBetweenName("J", "P");
		personsName.stream().forEach(person -> {
			System.out.println("Persona: " + person.getName() + " " + person.getLastname() + ", ID: " + person.getId());
		});

		System.out.println("===================== Consulta personas between ID 5 y 8 + Order By Name (Query Method) =====================");
		List<Person> personsMethod = personRepository.findAllBetweenId(1, 4);
		personsMethod.stream().forEach(person -> {
			System.out.println("Persona: " + person.getName() + " " + person.getLastname() + ", ID: " + person.getId());
		});

		System.out.println("");
		System.out.println("===================== Consulta personas between name A y N + Order By Name (Query Method) =====================");
		List<Person> personsNameMethod = personRepository.findAllBetweenName("A", "N");
		personsNameMethod.stream().forEach(person -> {
			System.out.println("Persona: " + person.getName() + " " + person.getLastname() + ", ID: " + person.getId());
		});
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesConcatUpperAndLowerCase() {
		System.out.println("===================== Consulta nombres y apellidos de personas (Concat) =====================");
		List<String> names = personRepository.getFullNameConcat();
		names.stream().forEach(name -> {
			System.out.println("Nombre completo: " + name);
		});

		System.out.println("");
		System.out.println("===================== Consulta nombres y apellidos de personas (Pipe) =====================");
		List<String> namesPipe = personRepository.getFullNameConcatenadoPipe();
		namesPipe.stream().forEach(name -> {
			System.out.println("Nombre completo: " + name);
		});

		System.out.println("");
		System.out.println("===================== Consulta nombres y apellidos de personas (Upper) =====================");
		List<String> namesUpper = personRepository.getFullNameConcatUpper();
		namesUpper.stream().forEach(name -> {
			System.out.println("Nombre completo: " + name);
		});

		System.out.println("");
		System.out.println("===================== Consulta nombres y apellidos de personas (Lower) =====================");
		List<String> namesLower = personRepository.getFullNameConcatLower();
		namesLower.stream().forEach(name -> {
			System.out.println("Nombre completo: " + name);
		});
	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct() {
		System.out.println("Consultas con nombres de personas");
		List<String> names = personRepository.findAllNames();

		names.stream().forEach(name -> {
			System.out.println("Nombre: " + name);
		});

		System.out.println("");
		System.out.println("Consultas con nombres de personas distinct");
		List<String> namesDistinct = personRepository.findAllNamesDistinct();

		namesDistinct.stream().forEach(name -> {
			System.out.println("Nombre: " + name);
		});

		System.out.println("");
		System.out.println("Consultas con lenguajes de programación distinct");
		List<String> languaguesDistinct = personRepository.findAllProgrammingLanguageDistinct();

		languaguesDistinct.stream().forEach(language -> {
			System.out.println("Lenguaje: " + language);
		});

		System.out.println("");
		System.out.println("Consultas cuenta lenguajes de programación distinct");
		Long countLangDistinct = personRepository.countProgrammingLanguageDistinct();

		System.out.println("Cantidad de lenguajes de programación distintos: " + countLangDistinct);
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
			System.out.println(person.getName() + " " + person.getLastname() + " con id " + id + " eliminada.");
			System.out.println("");
			System.out.println("Lista de personas actualizada:");
			list();
		}, () -> {
			System.out.println("Persona con id " + id + " no encontrada.");
		});
	}

	@Transactional(readOnly = true)
	public void personalizedQueries(Scanner scanner) {

		System.out.println("===================== Consultas personalizadas por el ID =====================");
		System.out.print("Ingrese el id de la persona a buscar: ");
		Long id = scanner.nextLong();
		scanner.nextLine();

		String namePerson = personRepository.getNameById(id);
		Long idPerson = personRepository.getIdById(id);
		String fullName = personRepository.getFullNameById(id);
		Object[] personDataFull = (Object[]) personRepository.obtenerPersonDataFull(id);
		
		if (namePerson.isEmpty()){
			System.out.println("Persona con id " + id + " no encontrada.");
		} else {
			System.out.println("Nombre de la persona con id " + id + ": " + namePerson);
			System.out.println("Id de la persona con id " + id + ": " + idPerson);
			System.out.println("Nombre completo de la persona con id " + id + ": " + fullName);
			System.out.println("ID: " + personDataFull[0] + ", Nombre: " + personDataFull[1] + ", Apellido: " + personDataFull[2] + ", Lenguaje de Programación: " + personDataFull[3]);
		}
	}

	@Transactional(readOnly = true)
	public void personalizedQueries2() {
		System.out.println("===================== Consulta por Objeto persona y lenguaje de programación ====================");
		List<Object[]> personsRegs = personRepository.findAllMixPerson();

		personsRegs.forEach(reg -> {
			System.out.println("ProgrammingLanguague: " + reg[1] + ", person: " + reg[0]);
		});

		System.out.println("");
		System.out.println("===================== Consulta que puebla y devuelve objeto entity de una instancia personalizada ====================");
		List<Person> persons = personRepository.findAllObjectPersonPersonalized();

		persons.forEach(person -> {
			System.out.println("Person: " + person);
		});

		System.out.println("");
		System.out.println("===================== Consulta que puebla y devuelve objeto dto de una clase personalizada ====================");

		List<PersonDto> personsDtos = personRepository.findAllPersonDto();
		personsDtos.forEach(personDto -> {
			System.out.println("PersonDto: " + personDto.getName() + " " + personDto.getLastname());
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

	public void findCoincidence(Scanner scanner) {

		System.out.print("Ingrese texto a buscar: ");
		String text = scanner.nextLine();

		List<Person> personsList = personRepository.findLikeName(text);

		if (personsList.isEmpty()) {
			System.out.println("No se encontraron personas que contengan el texto ingresado en el nombre.");
		} else {
			System.out.println("Personas encontradas:");
			personsList.forEach(System.out::println);
		}
	}

	public void list() {
		List<Person> persons = (List<Person>) personRepository.findAll();
		System.out.println("Listado de Personas");
		persons.stream().forEach(person -> {
			System.out.println(person);
		});
	}

}
