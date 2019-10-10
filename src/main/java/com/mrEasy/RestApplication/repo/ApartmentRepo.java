package com.mrEasy.RestApplication.repo;

import com.mrEasy.RestApplication.entity.Apartment;
import com.mrEasy.RestApplication.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepo extends JpaRepository<Apartment, Long> {
    Apartment findByHouseAndApartmentNumber(House house, Integer apartmentNumber);
}