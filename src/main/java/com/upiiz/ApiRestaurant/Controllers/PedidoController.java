package com.upiiz.ApiRestaurant.Controllers;


import com.upiiz.ApiRestaurant.Entities.Pedido;
import com.upiiz.ApiRestaurant.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/garufa/public/v1/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @Autowired
    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Operación: Listar pedidos (GET /garufa/public/v1/pedidos)
    @GetMapping
    public ResponseEntity<List<Pedido>> listarPedidos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    // Operación: Obtener pedido por ID (GET /garufa/public/v1/pedidos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPedidoPorId(@PathVariable Long id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Operación: Crear pedido (POST /garufa/public/v1/pedidos)
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido pedido) {
        try {
            Pedido nuevoPedido = pedidoService.save(pedido);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPedido);
        } catch (RuntimeException e) {
            // Manejo de errores si el cliente o platillo no existe
            // 400 BAD REQUEST
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // Operación: Eliminar pedido (DELETE /garufa/public/v1/pedidos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Long id) {
        if (pedidoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // --- Endpoints de Relación Pedidos ↔ Platillos ---

    // Operación: Agregar platillo a pedido (POST /garufa/public/v1/pedidos/{idPedido}/platillos/{idPlatillo})
    @PostMapping("/{idPedido}/platillos/{idPlatillo}")
    public ResponseEntity<Pedido> agregarPlatilloAPedido(
            @PathVariable Long idPedido,
            @PathVariable Long idPlatillo) {

        Optional<Pedido> updatedPedido = pedidoService.addPlatilloToPedido(idPedido, idPlatillo);

        if (updatedPedido.isPresent()) {
            return ResponseEntity.ok(updatedPedido.get());
        } else {
            // 404 NOT FOUND si el pedido o el platillo no existen
            return ResponseEntity.notFound().build();
        }
    }

    // Operación: Quitar platillo de pedido (DELETE /garufa/public/v1/pedidos/{idPedido}/platillos/{idPlatillo})
    @DeleteMapping("/{idPedido}/platillos/{idPlatillo}")
    public ResponseEntity<Pedido> quitarPlatilloDePedido(
            @PathVariable Long idPedido,
            @PathVariable Long idPlatillo) {

        Optional<Pedido> updatedPedido = pedidoService.removePlatilloFromPedido(idPedido, idPlatillo);

        if (updatedPedido.isPresent()) {
            return ResponseEntity.ok(updatedPedido.get());
        } else {
            // 404 NOT FOUND si el pedido no existe
            return ResponseEntity.notFound().build();
        }
    }

    // Operación: Listar platillos de pedido (GET /garufa/public/v1/pedidos/{idPedido}/platillos)
    @GetMapping("/{idPedido}/platillos")
    public ResponseEntity<?> listarPlatillosDePedido(@PathVariable Long idPedido) {
        return pedidoService.findById(idPedido)
                // Usamos el cuerpo para devolver la lista de detalles (platillos)
                .map(pedido -> ResponseEntity.ok(pedido.getDetalles()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}