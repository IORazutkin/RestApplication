package com.mrEasy.RestApplication.repo;

import com.mrEasy.RestApplication.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<Service, Long> {
}
