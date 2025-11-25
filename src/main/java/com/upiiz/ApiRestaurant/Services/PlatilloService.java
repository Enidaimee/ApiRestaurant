package com.upiiz.ApiRestaurant.Services;

import com.upiiz.ApiRestaurant.Entities.Platillo;
import com.upiiz.ApiRestaurant.Repositories.PlatilloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatilloService {

    private final PlatilloRepository platilloRepository;

    @Autowired
    public PlatilloService(PlatilloRepository platilloRepository) {
        this.platilloRepository = platilloRepository;
    }

    // Listar todos los platillos
    public List<Platillo> findAll() {
        return platilloRepository.findAll();
    }

    // Obtener platillo por ID
    public Optional<Platillo> findById(Long id) {
        return platilloRepository.findById(id);
    }

    // Guardar/Crear platillo
    public Platillo save(Platillo platillo) {
        return platilloRepository.save(platillo);
    }

    // Actualizar platillo
    public Platillo update(Long id, Platillo updatedPlatillo) {
        return platilloRepository.findById(id)
                .map(platillo -> {
                    platillo.setNombre(updatedPlatillo.getNombre());
                    platillo.setDescripcion(updatedPlatillo.getDescripcion());
                    platillo.setPrecio(updatedPlatillo.getPrecio());
                    return platilloRepository.save(platillo);
                }).orElse(null); // Podría lanzar una excepción NotFound
    }

    // Eliminar platillo
    public boolean deleteById(Long id) {
        if (platilloRepository.existsById(id)) {
            platilloRepository.deleteById(id);
            return true;
        }
        return false;
    }
}