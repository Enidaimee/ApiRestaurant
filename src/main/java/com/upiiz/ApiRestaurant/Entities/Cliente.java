package com.upiiz.ApiRestaurant.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// Anotación para indicar que es una entidad JPA
@Entity
// Anotación para el nombre de la tabla
@Table(name = "cliente")
// Lombok: Genera getters, setters, toString, equals y hashCode
@Data
// Lombok: Genera constructor sin argumentos
@NoArgsConstructor
// Lombok: Genera constructor con todos los argumentos
@AllArgsConstructor
public class Cliente {

    // Clave primaria e identidad
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del cliente
    @Column(nullable = false, length = 100)
    private String nombre;

    // Email, único
    @Column(unique = true, nullable = false, length = 100)
    private String email;

    // Teléfono
    @Column(length = 20)
    private String telefono;
}