package com.mrEasy.RestApplication.controller.rest;

import com.mrEasy.RestApplication.entity.House;
import com.mrEasy.RestApplication.repo.HouseRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/houses")
public class HouseRestController {
    private final HouseRepo houseRepo;

    @Autowired
    public HouseRestController(HouseRepo houseRepo) {
        this.houseRepo = houseRepo;
    }

    @GetMapping
    public List<House> getList() {
        return houseRepo.findAll(Sort.by("address"));
    }
}