package com.upiiz.ApiRestaurant.Repositories;

import com.upiiz.ApiRestaurant.Entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Proporciona automáticamente métodos CRUD para la entidad Cliente
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}