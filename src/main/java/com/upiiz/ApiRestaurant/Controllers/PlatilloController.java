package com.upiiz.ApiRestaurant.Controllers;

import com.upiiz.ApiRestaurant.Entities.Platillo;
import com.upiiz.ApiRestaurant.Services.PlatilloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garufa/public/v1/platillos")
public class PlatilloController {

    private final PlatilloService platilloService;

    @Autowired
    public PlatilloController(PlatilloService platilloService) {
        this.platilloService = platilloService;
    }

    // Operación: Listar platillos (GET /garufa/public/v1/platillos)
    @GetMapping
    public ResponseEntity<List<Platillo>> listarPlatillos() {
        return ResponseEntity.ok(platilloService.findAll());
    }

    // Operación: Obtener platillo por ID (GET /garufa/public/v1/platillos/{id})
    @GetMapping("/{id}")
    public ResponseEntity<Platillo> obtenerPlatilloPorId(@PathVariable Long id) {
        return platilloService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Operación: Crear platillo (POST /garufa/public/v1/platillos)
    @PostMapping
    public ResponseEntity<Platillo> crearPlatillo(@RequestBody Platillo platillo) {
        Platillo nuevoPlatillo = platilloService.save(platillo);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPlatillo);
    }

    // Operación: Actualizar platillo (PUT /garufa/public/v1/platillos/{id})
    @PutMapping("/{id}")
    public ResponseEntity<Platillo> actualizarPlatillo(@PathVariable Long id, @RequestBody Platillo platilloDetails) {
        Platillo updatedPlatillo = platilloService.update(id, platilloDetails);
        if (updatedPlatillo != null) {
            return ResponseEntity.ok(updatedPlatillo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Operación: Eliminar platillo (DELETE /garufa/public/v1/platillos/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPlatillo(@PathVariable Long id) {
        if (platilloService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}