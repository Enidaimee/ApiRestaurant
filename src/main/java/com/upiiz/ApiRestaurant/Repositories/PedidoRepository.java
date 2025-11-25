package com.upiiz.ApiRestaurant.Repositories;

import com.upiiz.ApiRestaurant.Entities.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}