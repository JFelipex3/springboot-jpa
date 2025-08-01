package com.jmachuca.curso.springboot.jpa.springboot_jpa.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "persons") // Se puede omitir si la tabla se llama igual que la clase
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY para Mysql o SQL Server y SEQUENCE para Oracle o PostgreSQL
    private Long id;

    private String name;
    private String lastname;

    @Column(name = "programming_language") // Se puede omitir si el nombre de la columna se llama igual que el atributo
    private String programmingLanguage;

    @Embedded
    private Audit audit = new Audit();

    // Si se define un constructor con parametros es obligatorio crear un constructor sin parametros para Hibernate
    public Person() {
        this.audit = new Audit();
    }

    public Person(Long id, String name, String lastname, String programmingLanguage) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.programmingLanguage = programmingLanguage;
        this.audit = new Audit();
    }

    public Person(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
        this.audit = new Audit();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public String toString() {
        return "Person [id=" + id + ", name=" + name + ", lastname=" + lastname + ", programmingLanguage="
                + programmingLanguage + ", creatAt=" + (audit != null ? audit.getCreatAt() : null)
                + ", updatedAt=" + (audit != null ? audit.getUpdatedAt() : null) + "]";
    }


}
