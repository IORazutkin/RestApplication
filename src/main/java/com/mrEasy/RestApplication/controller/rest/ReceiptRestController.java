package com.mrEasy.RestApplication.controller.rest;

import com.mrEasy.RestApplication.entity.Receipt;
import com.mrEasy.RestApplication.repo.ReceiptRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/receipts")
public class ReceiptRestController {
    private final ReceiptRepo receiptRepo;

    @Autowired
    public ReceiptRestController(ReceiptRepo receiptRepo) {
        this.receiptRepo = receiptRepo;
    }

    @GetMapping
    public List<Receipt> getList(@RequestParam(required = false, defaultValue = "") String apartmentId) {
        return receiptRepo.findAll(Sort.by(Sort.Direction.DESC, "receiptId")).stream()
                .filter(x -> apartmentId.equals("") || x.getApartment().getApartmentId().toString().equals(apartmentId))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Receipt add(@RequestBody Receipt receipt) {
        return receiptRepo.save(receipt);
    }

    @PutMapping("{id}")
    public Receipt update(@PathVariable("id") Receipt receiptFromDb,
                          @RequestBody Receipt receipt) {
        BeanUtils.copyProperties(receipt, receiptFromDb, "receiptId");
        return receiptRepo.save(receiptFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Receipt receipt) {
        receiptRepo.delete(receipt);
    }
}