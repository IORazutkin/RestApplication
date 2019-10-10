package com.mrEasy.RestApplication.repo;

import com.mrEasy.RestApplication.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepo extends JpaRepository<House, Long> {
    House findByAddress(String address);
}
