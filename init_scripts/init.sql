CREATE TABLE IF NOT EXISTS persons (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    lastname VARCHAR(255),
    programming_language VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME
);

INSERT INTO persons (name, lastname, programming_language) VALUES ('Andres', 'Guzman', 'Java');
INSERT INTO persons (name, lastname, programming_language) VALUES ('Pepe', 'Doe', 'Python');
INSERT INTO persons (name, lastname, programming_language) VALUES ('John', 'Dow', 'JavaScript');
INSERT INTO persons (name, lastname, programming_language) VALUES ('Maria', 'Roe', 'Java');
INSERT INTO persons (name, lastname, programming_language) VALUES ('Josefa', 'Rae', 'Java');