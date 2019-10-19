package com.mrEasy.RestApplication.controller.rest;

import com.mrEasy.RestApplication.entity.Apartment;
import com.mrEasy.RestApplication.repo.ApartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apartments")
public class ApartmentRestController {
    private final ApartmentRepo apartmentRepo;

    @Autowired
    public ApartmentRestController(ApartmentRepo apartmentRepo) {
        this.apartmentRepo = apartmentRepo;
    }

    @GetMapping
    public List<Apartment> getList(@RequestParam(defaultValue = "") String address) {
        return apartmentRepo.findAll(Sort.by("apartmentNumber")).stream()
                .filter(x -> x.getHouse().getAddress().equals(address))
                .collect(Collectors.toList());
    }
}
