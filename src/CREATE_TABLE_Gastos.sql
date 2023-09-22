CREATE TABLE Gasto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    monto DECIMAL(10, 2) NOT NULL,
    fecha DATE NOT NULL,
    categoria_id INT,
    FOREIGN KEY (categoria_id) REFERENCES CategoriaGasto(id)
);

DROP TABLE TablaEjemplo;
DELETE FROM TablaEjemplo WHERE id = 1;
