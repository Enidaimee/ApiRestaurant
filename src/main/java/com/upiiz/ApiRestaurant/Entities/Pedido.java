package com.upiiz.ApiRestaurant.Entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha y hora del pedido
    @Column(nullable = false)
    private LocalDateTime fecha;

    // Relación Muchos a Uno con Cliente (FK)
    @ManyToOne(fetch = FetchType.EAGER) // Trae el cliente junto con el pedido
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    // Total del pedido
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // Relación Uno a Muchos con DetallePedido (los platillos del pedido)
    // El 'mappedBy' indica el campo en la clase DetallePedido que es el dueño de la relación
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;
}