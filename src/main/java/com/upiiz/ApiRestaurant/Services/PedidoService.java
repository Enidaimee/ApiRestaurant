package com.upiiz.ApiRestaurant.Services;

import com.upiiz.ApiRestaurant.Entities.DetallePedido;
import com.upiiz.ApiRestaurant.Entities.Pedido;
import com.upiiz.ApiRestaurant.Entities.Platillo;
import com.upiiz.ApiRestaurant.Repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PlatilloService platilloService; // Necesario para obtener precios

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository, PlatilloService platilloService) {
        this.pedidoRepository = pedidoRepository;
        this.platilloService = platilloService;
    }

    // Listar todos los pedidos
    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    // Obtener pedido por ID
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id);
    }

    // Guardar/Crear pedido (incluye lógica de cálculo de total y precio actual)
    @Transactional
    public Pedido save(Pedido pedido) {
        // Asegúrate de que el pedido y sus detalles tienen el precioActual y la referencia al pedido
        pedido.setTotal(pedido.getDetalles().stream()
                .map(detalle -> {
                    // Buscar el platillo para obtener el precio actual
                    Optional<Platillo> platilloOpt = platilloService.findById(detalle.getPlatillo().getId());
                    if (platilloOpt.isEmpty()) {
                        // Podrías lanzar una excepción de platillo no encontrado
                        throw new RuntimeException("Platillo con ID " + detalle.getPlatillo().getId() + " no encontrado.");
                    }
                    Platillo platillo = platilloOpt.get();
                    detalle.setPrecioActual(platillo.getPrecio());
                    detalle.setPedido(pedido); // Establecer la referencia bidireccional
                    return detalle.getPrecioActual();
                })
                .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));

        return pedidoRepository.save(pedido);
    }

    // Eliminar pedido
    public boolean deleteById(Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Lógica para agregar un platillo a un pedido existente
    @Transactional
    public Optional<Pedido> addPlatilloToPedido(Long idPedido, Long idPlatillo) {
        return pedidoRepository.findById(idPedido).flatMap(pedido -> {
            Optional<Platillo> platilloOpt = platilloService.findById(idPlatillo);
            if (platilloOpt.isEmpty()) {
                return Optional.empty(); // Platillo no encontrado
            }
            Platillo platillo = platilloOpt.get();

            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setPlatillo(platillo);
            detalle.setPrecioActual(platillo.getPrecio()); // Usar el precio actual

            // Añadir el detalle a la lista del pedido
            pedido.getDetalles().add(detalle);
            // Recalcular el total
            pedido.setTotal(pedido.getTotal().add(detalle.getPrecioActual()));

            return Optional.of(pedidoRepository.save(pedido));
        });
    }

    // Lógica para quitar un platillo de un pedido existente (busca y elimina por platillo ID)
    @Transactional
    public Optional<Pedido> removePlatilloFromPedido(Long idPedido, Long idPlatillo) {
        return pedidoRepository.findById(idPedido).flatMap(pedido -> {
            boolean removed = pedido.getDetalles().removeIf(detalle -> {
                if (detalle.getPlatillo().getId().equals(idPlatillo)) {
                    // Recalcular el total al remover
                    pedido.setTotal(pedido.getTotal().subtract(detalle.getPrecioActual()));
                    return true;
                }
                return false;
            });

            if (removed) {
                return Optional.of(pedidoRepository.save(pedido));
            } else {
                return Optional.of(pedido); // No se eliminó, pero el pedido existe
            }
        });
    }
}