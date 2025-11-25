package com.upiiz.ApiRestaurant.Repositories;

import com.upiiz.ApiRestaurant.Entities.Platillo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatilloRepository extends JpaRepository<Platillo, Long> {
}