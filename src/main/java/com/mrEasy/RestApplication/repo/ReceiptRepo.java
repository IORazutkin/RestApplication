package com.mrEasy.RestApplication.repo;

import com.mrEasy.RestApplication.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepo extends JpaRepository<Receipt, Long> {
}
