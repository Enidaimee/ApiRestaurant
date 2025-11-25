package com.upiiz.ApiRestaurant.Controllers;

import com.upiiz.ApiRestaurant.Entities.Cliente;
import com.upiiz.ApiRestaurant.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Define la URL base para todos los métodos en este controlador
@RestController
@RequestMapping("/garufa/public/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Operación: Listar clientes (GET /garufa/public/v1/clientes)
    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.findAll();
        // 200 OK
        return ResponseEntity.ok(clientes);
    }

    // Operación: Obtener cliente por ID (GET /garufa/public/v1/clientes/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Long id) {
        return clienteService.findById(id)
                // 200 OK si se encuentra
                .map(ResponseEntity::ok)
                // 404 NOT FOUND si no se encuentra
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Operación: Crear cliente (POST /garufa/public/v1/clientes)
    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.save(cliente);
        // 201 CREATED
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    // Operación: Actualizar cliente (PUT /garufa/public/v1/clientes/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Cliente updatedCliente = clienteService.update(id, clienteDetails);
        if (updatedCliente != null) {
            // 200 OK
            return ResponseEntity.ok(updatedCliente);
        } else {
            // 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }

    // Operación: Eliminar cliente (DELETE /garufa/public/v1/clientes/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        if (clienteService.deleteById(id)) {
            // 204 NO CONTENT (Éxito sin contenido de respuesta)
            return ResponseEntity.noContent().build();
        } else {
            // 404 NOT FOUND
            return ResponseEntity.notFound().build();
        }
    }
}