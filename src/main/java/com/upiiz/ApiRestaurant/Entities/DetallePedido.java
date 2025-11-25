package com.upiiz.ApiRestaurant.Entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

// Entidad de unión para la relación N:M entre Pedido y Platillo.
// Almacena el precio que tenía el platillo en el momento del pedido.
@Entity
@Table(name = "detalle_pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación Muchos a Uno con Pedido
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;

    // Relación Muchos a Uno con Platillo
    // No es 'nullable = false' si queremos permitir eliminar platillos (aunque es mejor no hacerlo en producción)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_platillo", nullable = false)
    private Platillo platillo;

    // Precio que tenía el platillo al momento de la orden (para historial)
    @Column(name = "precio_actual", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioActual;
}