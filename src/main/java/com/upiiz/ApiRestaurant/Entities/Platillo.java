package com.upiiz.ApiRestaurant.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "platillo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Platillo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del platillo
    @Column(nullable = false, length = 150)
    private String nombre;

    // Descripción
    @Column(length = 500)
    private String descripcion;

    // Precio con precisión de 2 decimales
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;
}