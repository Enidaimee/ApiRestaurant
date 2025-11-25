package com.upiiz.ApiRestaurant.Services;

import com.upiiz.ApiRestaurant.Entities.Cliente;
import com.upiiz.ApiRestaurant.Repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    // Listar todos los clientes
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    // Obtener cliente por ID
    public Optional<Cliente> findById(Long id) {
        return clienteRepository.findById(id);
    }

    // Guardar/Crear cliente
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Actualizar cliente
    public Cliente update(Long id, Cliente updatedCliente) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(updatedCliente.getNombre());
                    cliente.setEmail(updatedCliente.getEmail());
                    cliente.setTelefono(updatedCliente.getTelefono());
                    return clienteRepository.save(cliente);
                }).orElse(null); // Podría lanzar una excepción NotFound
    }

    // Eliminar cliente
    public boolean deleteById(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return true;
        }
        return false;
    }
}